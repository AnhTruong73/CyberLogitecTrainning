/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtDBDAOMasterDSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.27
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.27 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.integration ;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author anhtruong
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CodeMgmtDBDAOMasterDSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * Delete Master
	  * </pre>
	  */
	public CodeMgmtDBDAOMasterDSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_id",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.dou.doutraining.codemgmt.integration ").append("\n"); 
		query.append("FileName : CodeMgmtDBDAOMasterDSQL").append("\n"); 
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
		query.append("DELETE FROM COM_INTG_CD" ).append("\n"); 
		query.append("WHERE	INTG_CD_ID = @[intg_cd_id] AND NOT EXISTS (SELECT INTG_CD_ID FROM COM_INTG_CD_DTL WHERE COM_INTG_CD_DTL.INTG_CD_ID = @[intg_cd_id]) " ).append("\n"); 

	}
}