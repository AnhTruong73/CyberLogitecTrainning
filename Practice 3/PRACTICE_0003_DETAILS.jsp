
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<% 
String fr_acct_yrmon = request.getParameter("fr_acct_yrmon");
System.out.println(fr_acct_yrmon);

String to_acct_yrmon = request.getParameter("to_acct_yrmon");
System.out.println(to_acct_yrmon);

String s_jo_crr_cd = request.getParameter("s_jo_crr_cd");
System.out.println(s_jo_crr_cd);

String s_rlane_cd = request.getParameter("s_rlane_cd");
System.out.println(String.format(".....%s...",s_rlane_cd));

String s_trd_cd = request.getParameter("s_trd_cd");
System.out.println(String.format(".....%s...",s_trd_cd));

//String query ="SELECT INV.JO_CRR_CD ,INV.RLANE_CD ,INV.LOCL_CURR_CD ,INV.INV_NO , INV.CSR_NO, INV.APRO_FLG, INV.CUST_VNDR_CNT_CD ,INV.CUST_VNDR_SEQ ,INV.PRNR_REF_NO ,INV.CUST_VNDR_ENG_NM ,SUM(INV.REV_ACT_AMT) AS INV_REV_ACT_AMT ,SUM(INV.EXP_ACT_AMT) AS INV_EXP_ACT_AMT ,INV.RE_DIVR_CD AS REV_EXP ,INV.JO_STL_ITM_CD AS ITEM FROM (SELECT INV.JO_CRR_CD ,STL.RLANE_CD ,NVL(INV.INV_CURR_CD, INV.LOCL_CURR_CD) AS LOCL_CURR_CD ,DTL.ACT_AMT ,INV.INV_NO ,NVL(INV.SLP_TP_CD||INV.SLP_FUNC_CD||INV.SLP_OFC_CD||INV.SLP_ISS_DT||INV.SLP_SER_NO,'N/A') AS CSR_NO ,NVL(CSR.APRO_FLG, 'N') AS APRO_FLG ,DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,1,2), NULL) AS CUST_VNDR_CNT_CD ,DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,3), INV.PRNR_REF_NO) AS CUST_VNDR_SEQ ,INV.PRNR_REF_NO ,DECODE('R',STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS REV_ACT_AMT ,DECODE('E',STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS EXP_ACT_AMT ,INV.RE_DIVR_CD ,STL.JO_STL_ITM_CD ,CASE WHEN INV.RE_DIVR_CD = 'R' THEN (SELECT M.CUST_LGL_ENG_NM FROM MDM_CUSTOMER M WHERE M.DELT_FLG='N' AND M.CUST_CNT_CD =SUBSTR(INV.PRNR_REF_NO,1,2) AND M.CUST_SEQ =SUBSTR(INV.PRNR_REF_NO,3)) ELSE (SELECT M.VNDR_LGL_ENG_NM FROM MDM_VENDOR M WHERE M.DELT_FLG='N' AND M.VNDR_SEQ =INV.PRNR_REF_NO) END AS CUST_VNDR_ENG_NM FROM JOO_INVOICE INV ,JOO_INV_DTL DTL ,JOO_STL_TGT STL ,JOO_CSR CSR WHERE 1=1 AND NVL(STL.THEA_STL_FLG, 'N') = 'N' AND INV.ACCT_YRMON BETWEEN REPLACE(@[acct_yrmon_fr],'-','') AND REPLACE(@[acct_yrmon_to],'-','') #if (${jo_crr_cd}!='' && ${jo_crr_cd} != 'All') AND INV.JO_CRR_CD IN ( #foreach($key IN ${obj_list_no}) #if($velocityCount < $obj_list_no.size()) '$key' , #else '$key' #end #end) #end #if (${rlane_cd}!='' ) AND STL.RLANE_CD=@[rlane_cd] #end #if (${trd_cd}!='' ) AND EXISTS ( SELECT 'X' FROM JOO_CARRIER CRR WHERE 1=1 AND CRR.DELT_FLG='N' AND CRR.JO_CRR_CD=STL.JO_CRR_CD AND  CRR.RLANE_CD=STL.RLANE_CD AND CRR.TRD_CD=@[trd_cd] ) #end AND INV.RJCT_CMB_FLG='N' AND DTL.ACCT_YRMON=INV.ACCT_YRMON AND DTL.JO_CRR_CD=INV.JO_CRR_CD AND DTL.INV_NO=INV.INV_NO AND DTL.RE_DIVR_CD=INV.RE_DIVR_CD AND STL.THEA_STL_FLG=INV.THEA_STL_FLG AND STL.VSL_CD=DTL.VSL_CD AND STL.SKD_VOY_NO=DTL.SKD_VOY_NO AND STL.SKD_DIR_CD=DTL.SKD_DIR_CD AND STL.REV_DIR_CD=DTL.REV_DIR_CD AND STL.REV_YRMON=DTL.REV_YRMON AND STL.STL_VVD_SEQ=DTL.STL_VVD_SEQ AND INV.SLP_TP_CD=CSR.SLP_TP_CD(+) AND INV.SLP_FUNC_CD=CSR.SLP_FUNC_CD(+) AND INV.SLP_OFC_CD=CSR.SLP_OFC_CD(+) AND INV.SLP_ISS_DT=CSR.SLP_ISS_DT(+) AND INV.SLP_SER_NO=CSR.SLP_SER_NO(+) )INV WHERE 1=1 GROUP BY GROUPING SETS ((JO_CRR_CD, RLANE_CD, CSR_NO, APRO_FLG, CUST_VNDR_CNT_CD, CUST_VNDR_SEQ, PRNR_REF_NO, CUST_VNDR_ENG_NM, LOCL_CURR_CD, INV_NO, RE_DIVR_CD, JO_STL_ITM_CD), (LOCL_CURR_CD, INV_NO), (LOCL_CURR_CD)) ORDER BY INV_NO, JO_CRR_CD";
StringBuffer query = new StringBuffer();
		query.append("SELECT INV.JO_CRR_CD" ).append("\n"); 
		query.append("	,INV.RLANE_CD" ).append("\n"); 
		query.append("	,INV.LOCL_CURR_CD" ).append("\n"); 
		query.append("	,INV.INV_NO" ).append("\n"); 
		query.append("	,INV.CSR_NO" ).append("\n"); 
		query.append("	,INV.APRO_FLG " ).append("\n"); 
		query.append("	,INV.CUST_VNDR_CNT_CD" ).append("\n"); 
		query.append("	,INV.CUST_VNDR_SEQ" ).append("\n"); 
		query.append("	,INV.PRNR_REF_NO" ).append("\n"); 
		query.append("	,INV.CUST_VNDR_ENG_NM" ).append("\n"); 
		query.append("	,SUM(INV.REV_ACT_AMT) AS INV_REV_ACT_AMT" ).append("\n"); 
		query.append("	,SUM(INV.EXP_ACT_AMT) AS INV_EXP_ACT_AMT" ).append("\n"); 
		query.append("    ,INV.RE_DIVR_CD AS REV_EXP" ).append("\n"); 
		query.append("    ,INV.JO_STL_ITM_CD AS ITEM" ).append("\n"); 
		query.append("FROM" ).append("\n"); 
		query.append("(" ).append("\n"); 
		query.append("SELECT INV.JO_CRR_CD" ).append("\n"); 
		query.append("	,STL.RLANE_CD" ).append("\n"); 
		query.append("	,NVL(INV.INV_CURR_CD, INV.LOCL_CURR_CD) AS LOCL_CURR_CD" ).append("\n"); 
		query.append("	,DTL.ACT_AMT" ).append("\n"); 
		query.append("	,INV.INV_NO" ).append("\n"); 
		query.append("	,NVL(INV.SLP_TP_CD||INV.SLP_FUNC_CD||INV.SLP_OFC_CD||INV.SLP_ISS_DT||INV.SLP_SER_NO,'N/A') AS CSR_NO" ).append("\n"); 
		query.append("	,NVL(CSR.APRO_FLG, 'N') AS APRO_FLG" ).append("\n"); 
		query.append("	,DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,1,2), NULL) AS CUST_VNDR_CNT_CD" ).append("\n"); 
		query.append("	,DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,3), INV.PRNR_REF_NO) AS CUST_VNDR_SEQ" ).append("\n"); 
		query.append("	,INV.PRNR_REF_NO" ).append("\n"); 
		query.append("	,DECODE('R',STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS REV_ACT_AMT" ).append("\n"); 
		query.append("	,DECODE('E',STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS EXP_ACT_AMT" ).append("\n"); 
		query.append("	,INV.RE_DIVR_CD" ).append("\n"); 
		query.append("    ,STL.JO_STL_ITM_CD" ).append("\n"); 
		query.append("	,CASE WHEN INV.RE_DIVR_CD = 'R' THEN" ).append("\n"); 
		query.append("	(SELECT M.CUST_LGL_ENG_NM" ).append("\n"); 
		query.append("		FROM MDM_CUSTOMER M" ).append("\n"); 
		query.append("		WHERE M.DELT_FLG='N'" ).append("\n"); 
		query.append("		 AND M.CUST_CNT_CD =SUBSTR(INV.PRNR_REF_NO,1,2)" ).append("\n"); 
		query.append("		 AND M.CUST_SEQ =SUBSTR(INV.PRNR_REF_NO,3))" ).append("\n"); 
		query.append("	ELSE" ).append("\n"); 
		query.append("	(SELECT M.VNDR_LGL_ENG_NM" ).append("\n"); 
		query.append("	 FROM MDM_VENDOR M" ).append("\n"); 
		query.append("	 WHERE M.DELT_FLG='N'" ).append("\n"); 
		query.append("		AND M.VNDR_SEQ =INV.PRNR_REF_NO)" ).append("\n"); 
		query.append("	END AS CUST_VNDR_ENG_NM" ).append("\n"); 
		query.append("	FROM JOO_INVOICE INV" ).append("\n"); 
		query.append("		,JOO_INV_DTL DTL" ).append("\n"); 
		query.append("		,JOO_STL_TGT STL" ).append("\n"); 
		query.append("		,JOO_CSR CSR" ).append("\n"); 
		query.append("	WHERE 1=1" ).append("\n"); 
		query.append("    AND NVL(STL.THEA_STL_FLG, 'N') = 'N'" ).append("\n"); 
		query.append(String.format("	AND INV.ACCT_YRMON   BETWEEN REPLACE('%s','-','') AND REPLACE('%s','-','')",fr_acct_yrmon,to_acct_yrmon )).append("\n");
		if (!"".equals(s_jo_crr_cd) &&  !"All".equals(s_jo_crr_cd)){
			String[] words = s_jo_crr_cd.split(",");
			StringBuffer jo_crr_cd = new StringBuffer();
			for(String word:words){
				jo_crr_cd.append("'"+word+"'");
				jo_crr_cd.append(",");
			}

			query.append(String.format("        AND INV.JO_CRR_CD  IN (%s)", jo_crr_cd)).append("\n"); 
		}
		if (!"".equals(s_rlane_cd)){
			query.append(String.format("		AND STL.RLANE_CD   = '%s'" ,s_rlane_cd)).append("\n");
		}

		if(!"".equals(s_trd_cd)){
			query.append("	   AND EXISTS   (   SELECT 'X'" ).append("\n"); 
			query.append("						  FROM JOO_CARRIER CRR" ).append("\n"); 
			query.append("						 WHERE 1=1" ).append("\n"); 
			query.append("						   AND CRR.DELT_FLG         = 'N'" ).append("\n"); 
			query.append("						   AND CRR.JO_CRR_CD        = STL.JO_CRR_CD" ).append("\n"); 
			query.append("						   AND CRR.RLANE_CD         = STL.RLANE_CD" ).append("\n"); 
			query.append(String.format("						   AND CRR.TRD_CD = '%s'",s_trd_cd) ).append("\n"); 
			query.append("					 )" ).append("\n"); 
		} 
		query.append("    AND INV.RJCT_CMB_FLG  = 'N'" ).append("\n"); 
		query.append("    AND DTL.ACCT_YRMON    = INV.ACCT_YRMON" ).append("\n"); 
		query.append("    AND DTL.JO_CRR_CD     = INV.JO_CRR_CD" ).append("\n"); 
		query.append("    AND DTL.INV_NO        = INV.INV_NO" ).append("\n"); 
		query.append("    AND DTL.RE_DIVR_CD    = INV.RE_DIVR_CD" ).append("\n"); 
		query.append("    AND STL.THEA_STL_FLG  = INV.THEA_STL_FLG" ).append("\n"); 
		query.append("    AND STL.VSL_CD        = DTL.VSL_CD" ).append("\n"); 
		query.append("    AND STL.SKD_VOY_NO    = DTL.SKD_VOY_NO" ).append("\n"); 
		query.append("    AND STL.SKD_DIR_CD    = DTL.SKD_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_DIR_CD    = DTL.REV_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_YRMON     = DTL.REV_YRMON" ).append("\n"); 
		query.append("    AND STL.STL_VVD_SEQ   = DTL.STL_VVD_SEQ" ).append("\n"); 
		query.append("    AND INV.SLP_TP_CD     = CSR.SLP_TP_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_FUNC_CD   = CSR.SLP_FUNC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_OFC_CD    = CSR.SLP_OFC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_ISS_DT    = CSR.SLP_ISS_DT(+)" ).append("\n"); 
		query.append("    AND INV.SLP_SER_NO    = CSR.SLP_SER_NO(+)" ).append("\n"); 
		query.append(")INV" ).append("\n"); 
		query.append("WHERE 1=1" ).append("\n"); 
		query.append("GROUP BY GROUPING SETS ((JO_CRR_CD, RLANE_CD, CSR_NO, APRO_FLG, CUST_VNDR_CNT_CD, CUST_VNDR_SEQ, PRNR_REF_NO, CUST_VNDR_ENG_NM, LOCL_CURR_CD, INV_NO, RE_DIVR_CD, JO_STL_ITM_CD)," ).append("\n"); 
		query.append("                         (LOCL_CURR_CD, INV_NO)," ).append("\n"); 
		query.append("                         (LOCL_CURR_CD))" ).append("\n"); 
		query.append(" ORDER BY INV_NO, JO_CRR_CD" ).append("\n");
System.out.println(query);
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.14:1521:ORCL","C##ONE","C##ONE");
PreparedStatement  pstmt=conn.prepareStatement(query.toString());
ResultSet rs=pstmt.executeQuery();
List li=new ArrayList<>();
Map mp=null;
while(rs.next()){
	mp=new HashMap<>(); 
	mp.put("csr_no",rs.getString("CSR_NO"));
    mp.put("inv_rev_act_amt",rs.getString("INV_REV_ACT_AMT")); 
    mp.put("locl_curr_cd",rs.getString("LOCL_CURR_CD"));
    mp.put("cust_vndr_seq",rs.getString("CUST_VNDR_SEQ")); 
    mp.put("jo_crr_cd",rs.getString("JO_CRR_CD"));
    mp.put("rlane_cd",rs.getString("RLANE_CD")); 
    mp.put("rev_exp",rs.getString("REV_EXP"));
    mp.put("cust_vndr_cnt_cd",rs.getString("CUST_VNDR_CNT_CD")); 
    mp.put("inv_no",rs.getString("INV_NO"));
    mp.put("cust_vndr_eng_nm",rs.getString("CUST_VNDR_ENG_NM"));
    mp.put("inv_exp_act_amt",rs.getString("INV_EXP_ACT_AMT")); 
    mp.put("item",rs.getString("ITEM"));
    mp.put("prnr_ref_no",rs.getString("PRNR_REF_NO")); 
    mp.put("apro_flg",rs.getString("APRO_FLG")); 
    li.add(mp); 
    }
    request.setAttribute("SHEETDATA", li); 
    System.out.println("All counts:"+li.size()); 
    String forwardPath="./js/ibsheet/jsp/DirectDown2Excel.jsp" ; 
    if(!"".equals(forwardPath)) {
    	RequestDispatcher rd=request.getRequestDispatcher(forwardPath); 
    	rd.forward(request,response); 
    }
 %>
