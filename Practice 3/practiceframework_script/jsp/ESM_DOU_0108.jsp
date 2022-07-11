<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.jsp
*@FileTitle : ESM_DOU_0108
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.02 JayTruong
* 1.0 Creation
=========================================================*/
%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.esm.clv.practiceframework.event.EsmDou0108Event"%>
<%@ page import="org.apache.log4j.Logger" %>
<%
	EsmDou0108Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;		
	String strErrMsg = "";			
	int rowCount	 = 0;					

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	String partner 			= "";
	try {
		event = (EsmDou0108Event) request.getAttribute("Event");
		serverException = (Exception) request
				.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);
		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException)
					.loadPopupMessage();
		}
		GeneralEventResponse eventResponse = (GeneralEventResponse) request
					.getAttribute("EventResponse");
		partner = eventResponse.getETCData("jo_crr_cd");
		
		
		
	} catch (Exception e) {
		out.println(e.toString());
	}
%>

<script language="javascript">
	var partner = "All|<%=partner%>";
	var signalToConfirm="";
	
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
		
	}
</script>

<form name="form">
    <input type="hidden" name="f_cmd"> <input type="hidden"
                                              name="pagerows"> <input type="hidden" name="value_partner">
    <!-- 개발자 작업	-->
    <div class="page_title_area clear">
        <h2 class="page_title">
            <button type="button">
                <span id="title">Money Management</span>
            </button>
        </h2>
        <div class="opus_design_btn">
            <button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
			-->
            <button type="button" class="btn_normal" name="btn_New" id="btn_New">New</button><!--
			-->
            <button type="button" class="btn_normal" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button><!--
			-->
            <button type="button" class="btn_normal" name="btn_DownExcel2" id="btn_DownExcel2">Down Excel2</button>
        </div>
        <div class="location">
            <span id="navigation"></span>
        </div>
    </div>
    <div class="wrap_search tab">
        <div class="opus_design_inquiry wFit">
            <table>
                <tbody>
					<tr>
						<th width="">Year month</th>					
							<td><input type="text" style="width:80px;" class="input1" maxlength="8" name="s_fr_acct_yrmon" id="s_fr_acct_yrmon"/> 
							   <button type="button" class="btn_left" name="btn_from_back" id="btn_from_back"></button><!--  
							   --><button type="button" class="btn_right" name="btn_from_next" id="btn_from_next"></button>~  
							   <input type="text" style="width:80px;" class="input1" maxlength="8" name="s_to_acct_yrmon" id="s_to_acct_yrmon"/>
							   <button type="button" class="btn_left" name="btn_to_back" id="btn_to_back"></button><!--  
							   --><button type="button" class="btn_right" name="btn_to_next" id="btn_to_next"></button></td>
						<th width="">Partner</th>
							<td> <script type="text/javascript">ComComboObject('s_jo_crr_cd',1,130, 1, 0, 0);</script></td>						
						<th width="">Lane</th>
							<td> <script type="text/javascript">ComComboObject('s_rlane_cd',1,130, 1, 0, 0);</script></td>
						<th width="">Trade</th>
							<td> <script type="text/javascript">ComComboObject('s_trd_cd',1,130, 1, 0, 0);</script></td>
					</tr> 	
				</tbody>
            </table>
        </div>
    </div>

	<div class="wrap_result">
		<div class="opus_design_tab">
			<script type="text/javascript">ComTabObject('tab1')</script>
		</div>
		<div class="opus_design_grid" name="tabLayer" id="tabLayer">
			<script language="javascript">ComSheetObject('sheet1');</script>
		</div>
		<div class="opus_design_grid" name="tabLayer" id="tabLayer">
			<script language="javascript">ComSheetObject('sheet2');</script>
		</div>
		
	</div>
	<!-- End of developer work -->
</form>