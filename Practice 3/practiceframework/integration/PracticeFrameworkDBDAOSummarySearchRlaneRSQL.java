/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkDBDAOSummarySearchRlaneRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.06 
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

public class PracticeFrameworkDBDAOSummarySearchRlaneRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * Rlane
	  * </pre>
	  */
	public PracticeFrameworkDBDAOSummarySearchRlaneRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.practiceframework.integration").append("\n"); 
		query.append("FileName : PracticeFrameworkDBDAOSummarySearchRlaneRSQL").append("\n"); 
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
		query.append("select " ).append("\n"); 
		query.append("	distinct(rlane_cd)" ).append("\n"); 
		query.append("from joo_carrier" ).append("\n"); 
		query.append("where jo_crr_cd IN (" ).append("\n"); 
		query.append("	#foreach($key IN ${obj_list_no}) #if($velocityCount < $obj_list_no.size()) '$key', #else '$key' #end #end)" ).append("\n"); 

	}
}