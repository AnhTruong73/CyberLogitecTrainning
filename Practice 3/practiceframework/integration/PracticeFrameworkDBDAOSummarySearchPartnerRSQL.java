/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkDBDAOSummarySearchPartnerRSQL.java
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

public class PracticeFrameworkDBDAOSummarySearchPartnerRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * SearchPartner()
	  * </pre>
	  */
	public PracticeFrameworkDBDAOSummarySearchPartnerRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.practiceframework.integration").append("\n"); 
		query.append("FileName : PracticeFrameworkDBDAOSummarySearchPartnerRSQL").append("\n"); 
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
		query.append("	distinct(jo_crr_cd) " ).append("\n"); 
		query.append("FROM joo_carrier" ).append("\n"); 

	}
}