<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_0003.jsp
*@FileTitle : Practice 3
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02
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
<%@ page import="com.clt.apps.opus.esm.clv.practiceframework.event.Practice0003Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	Practice0003Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	String partner 			= "";
	try {
		event = (Practice0003Event) request.getAttribute("Event");
		serverException = (Exception) request
				.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);
		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException)
					.loadPopupMessage();
		}
		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request
				.getAttribute("EventResponse");
		partner = eventResponse.getETCData("jo_crr_cd");
	} catch (Exception e) {
		out.println(e.toString());
	}
%>

<script language="javascript">
	var partner = "All|<%=partner%>";
	
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
		
	}
</script>

<form name="form">
<input type="hidden" name="f_cmd">
<input type="hidden" name="pagerows">
<!-- 개발자 작업	-->
<div class="page_title_area clear">
		<h2 class="page_title"><button type="button"><span id="title"></span></button></h2>
		<div class="opus_design_btn">
		   <button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button>
		   <!-- -->
		   <button type="button" class="btn_normal" name="btn_New" id="btn_New">New</button>
		   <!-- -->
		   <button type="button" class="btn_normal" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button>
		   <!-- -->
		   <button type="button" class="btn_normal" name="btn_DownExcel2" id="btn_DownExcel2">Down Excel 2</button>
		</div>
	    <div class="location">
	     	<span id="navigation"></span>
	    </div>
	</div>
	
	<div class="wrap_search">
		<div class="opus_design_inquiry">
		    <table>
		    <colgroup>
					<col width="100" />				
					<col width="200" />						
					<col width="70" />	
					<col width="100" />				
					<col width="70" />					
					<col width="100" />	
					<col width="70" />			
			   </colgroup> 
		        <tbody>
					<tr>
						<th width="">Year month</th>					
							<td><input type="text" style="width:80px;" class="input1" maxlength="8" name="fr_acct_yrmon" value="" id="fr_acct_yrmon"/> 
							   <button type="button" class="btn_left" name="btn_from_back" id="btn_from_back"></button><!--  
							   --><button type="button" class="btn_right" name="btn_from_next" id="btn_from_next"></button>~  
							   <input type="text" style="width:80px;" class="input1" maxlength="8" name="to_acct_yrmon" value="" id="to_acct_yrmon"/>
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
		<div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
			<script language="javascript">ComSheetObject('sheet1');</script>
		</div>
		
		<div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
			<script language="javascript">ComSheetObject('sheet2');</script>
		</div>
		
	</div>

<!-- 개발자 작업  끝 -->
</form>