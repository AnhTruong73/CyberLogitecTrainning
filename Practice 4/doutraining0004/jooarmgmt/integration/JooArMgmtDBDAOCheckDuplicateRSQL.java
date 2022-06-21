/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooArMgmtDBDAOCheckDuplicateRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author anhtruong
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class JooArMgmtDBDAOCheckDuplicateRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * using check duplicate
	  * </pre>
	  */
	public JooArMgmtDBDAOCheckDuplicateRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("jo_crr_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("rlane_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.integration").append("\n"); 
		query.append("FileName : JooArMgmtDBDAOCheckDuplicateRSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("SELECT COUNT(JO_CRR_CD) as cnt" ).append("\n"); 
		query.append("FROM " ).append("\n"); 
		query.append("	(SELECT JO_CRR_CD " ).append("\n"); 
		query.append("	 FROM JOO_CARRIER " ).append("\n"); 
		query.append("	 WHERE JO_CRR_CD=@[jo_crr_cd] " ).append("\n"); 
		query.append("	 AND RLANE_CD=@[rlane_cd]" ).append("\n"); 
		query.append("	 AND ROWNUM =1)" ).append("\n"); 

	}
}