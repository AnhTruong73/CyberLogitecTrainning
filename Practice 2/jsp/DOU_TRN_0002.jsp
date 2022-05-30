<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.dou.doutraining.codemgmt.event.DouTrn0002Event"%>
<%
	DouTrn0002Event event = null; //PDTO(Data Transfer Object including Parameters)
	Exception serverException = null; //서버에서 발생한 에러
	String strErrMsg = ""; //에러메세지
	String successFlag = "";
	String codeList = "";
	String pageRows = "100";

	try {
		event = (DouTrn0002Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
	} catch (Exception e) {
		out.println(e.toString());
	}
%>

<script language="javascript">
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			showErrMessage(errMessage);
		} // end if
		loadPage();
	}
</script>

<form name="form1">
<input type="hidden" name="f_cmd">
<input type="hidden" name="iPage">
<input type="hidden" name="codeid">
<input type="hidden" name="selectedcodes">

<!-- page_title_area(S) -->
<div class="page_title_area clear">
	
	<!-- page_title(S) -->
	<h2 class="page_title"><button type="button"><span id="title"></span></button></h2>
	<!-- page_title(E) -->

	<!-- opus_design_btn(S) -->
	<div class="opus_design_btn">
		 <button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button>
		 <button type="button" class="btn_normal" name="btn_Save" id="btn_Save">Save</button>
	 </div>
	<!-- opus_design_btn(E) -->

   	<!-- page_location(S) -->
   	<div class="location">
		<!-- location 내용 동적생성 (별도 코딩 불필요) -->
        <span id="navigation"></span>
   	</div>
   	<!-- page_location(E) -->
</div>
<!-- page_title_area(E) -->

<div class="wrap_search">
	<div class="opus_design_inquiry wFit"> 
		<table class="search" style="width: 100%;">
			<tr class="h23">
				<th width="70">Subsystem</th>
				<td width="150"><input type="text" name="s_ownr_sub_sys_cd" style="width: 130" onKeyPress="javascript:ComKeyOnlyAlphabet('uppernum');" value="" id="s_ownr_sub_sys_cd"></td>
				<th width="45">Cd ID</th>
				<td><input type="text" name="s_intg_cd_id" style="width: 150" value="" id="s_intg_cd_id"></td>
			</tr>
		</table>
	</div>
</div>

<div class="wrap_result">
		
	<!-- opus_grid_design_btn(S) -->
	<div class="opus_design_grid">
		<h3 class="title_design">Master</h3>
		<!-- opus_grid_btn(S) -->
		<div class="opus_design_btn">
			<button type="button" class="btn_normal" name="btn_rowadd_mst" id="btn_rowadd_mst">Row Add</button>
		</div>
		<!-- opus_grid_btn(E) -->
	</div>
	
	<script language="javascript">ComSheetObject('sheet1');</script>
	<!-- opus_grid_design_btn(E) -->
	
	<div class="opus_design_inquiry"><table class="line_bluedot"><tr><td></td></tr></table></div>
	
	<!-- opus_grid_design_btn(S) -->
	<div class="opus_design_grid">
		<h3 class="title_design">Detail</h3>
		<!-- opus_grid_btn(S) -->
		<div class="opus_design_btn">
			<button type="button" class="btn_normal" name="btn_rowadd_dtl" id="btn_rowadd_dtl" disabled="true">Row Add</button>
		</div>
		<!-- opus_grid_btn(E) -->
	</div>
	
	<script language="javascript">ComSheetObject('sheet2');</script>
	<!-- opus_grid_design_btn(E) -->
</div>

</form>