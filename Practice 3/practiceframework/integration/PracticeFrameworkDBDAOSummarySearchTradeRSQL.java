/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkDBDAOSummarySearchTradeRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.06 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practiceframework.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author anhtruong
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class PracticeFrameworkDBDAOSummarySearchTradeRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * SearchTrade
	  * </pre>
	  */
	public PracticeFrameworkDBDAOSummarySearchTradeRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",Y";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("rlane_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.practiceframework.integration").append("\n"); 
		query.append("FileName : PracticeFrameworkDBDAOSummarySearchTradeRSQL").append("\n"); 
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
		query.append("SELECT " ).append("\n"); 
		query.append("	distinct(trd_cd)," ).append("\n"); 
		query.append("	jo_crr_cd," ).append("\n"); 
		query.append("	rlane_cd" ).append("\n"); 
		query.append("FROM joo_carrier" ).append("\n"); 
		query.append("WHERE rlane_cd = @[rlane_cd]" ).append("\n"); 
		query.append("AND jo_crr_cd IN (" ).append("\n"); 
		query.append("	#foreach($key IN ${obj_list_no}) #if($velocityCount < $obj_list_no.size()) '$key', #else '$key' #end #end)" ).append("\n"); 

	}
}