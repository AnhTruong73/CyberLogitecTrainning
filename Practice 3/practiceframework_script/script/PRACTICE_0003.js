/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_0003.js
*@FileTitle : Pactice 3
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
     * @extends 
     * @class PRACTICE_0003 : PRACTICE_0003 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */


// Common global variable 
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";
var sheetObjects=new Array();
var sheetCnt=0;
var tabObjects=new Array();
var tabCnt=0;
var beforetab=1;
var checkAgree= 0;

document.onclick=processButtonClick;

/**
 * {loadPage} functions that calls a common function that sets the default settings of the sheet
 * It is the first called area when fire jsp onload event
*/
function loadPage(){
	
	for ( var k = 0; k < comboObjects.length; k++) {
		initCombo(comboObjects[k], k + 1);
	}

	initCalender();
	fr_acct_yrmon.disabled = true;
	to_acct_yrmon.disabled = true;
	
	s_jo_crr_cd.SetSelectIndex(0);
	s_rlane_cd.SetEnable(false);
	s_trd_cd.SetEnable(false);
	
	for(k = 0;k < tabObjects.length; k++){
		initTab(tabObjects[k], k + 1);
		tabObjects[k].SetSelectedIndex(0);
	}
	
	
	
	for(i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	ComOpenWait(true);
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
}

/**
 * {initCombo} functions that define the basic properties of the combobox
 * @param comboObj
 * @param comboNo
 */
function initCombo(comboObj, comboNo) {
	var formObj = document.form;
	switch (comboNo) {
	case 1:
		with (comboObj) {
			SetMultiSelect(1);
	        SetDropHeight(200);
	        ValidChar(2,1);
		}
		var comboItems = partner.split("|");
		addComboItem(comboObj, comboItems);
		break;
	}
}

/**
 * {addComboItem} functions that add data to the combobox
 * @param comboObj
 * @param comboItems
 */
function addComboItem(comboObj, comboItems) {
	for (var i=0 ; i < comboItems.length ; i++) {
		var comboItem=comboItems[i].split(",");
		if(comboItem.length == 1){
			comboObj.InsertItem(i, comboItem[0], comboItem[0]);
		}else{
			comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1], comboItem[1]);
		}
		
	}   		
}

/**
 * {s_jo_crr_cd_OnCheckClick} functions that handling event check click in combobox "Partner"
 * @param Index
 * @param Code
 * @param Checked
 */
function s_jo_crr_cd_OnCheckClick(Index, Code, Checked) {
	var count = s_jo_crr_cd.GetItemCount();
	var checkSelectCount = 0;
	
	if (Code === 0){
		var bChk = s_jo_crr_cd.GetItemCheck(Code);
        if(bChk){
            for(var i=1 ; i < count ; i++) {
            	s_jo_crr_cd.SetItemCheck(i,false);
            }
            s_rlane_cd.RemoveAll();
         	s_trd_cd.RemoveAll();
        	s_rlane_cd.SetEnable(false);
        	s_trd_cd.SetEnable(false);
        }
	}
	else {
        var bChk=s_jo_crr_cd.GetItemCheck(Code);
        if (bChk) {
        	s_jo_crr_cd.SetItemCheck(0,false);
        	s_rlane_cd.SetEnable(true);
        	getLaneComboData();
        }
    }

	for (var i = 0; i < count; i++){
		if (s_jo_crr_cd.GetItemCheck(i)){
			checkSelectCount += 1;
			getLaneComboData();
		}	
	}
	 if(checkSelectCount === 0) {
		s_jo_crr_cd.SetItemCheck(0,true,false);
		s_rlane_cd.RemoveAll();
		s_trd_cd.RemoveAll();
     	s_rlane_cd.SetEnable(false);
     	s_trd_cd.SetEnable(false);
	 }
}

/**
 * {getLaneComboData} functions that get Lane from database and then initializr combobox Lane
 */
function getLaneComboData(){
	s_rlane_cd.RemoveAll();
 	s_trd_cd.RemoveAll();
	document.form.f_cmd.value = SEARCH01;
	var xml = sheetObjects[0].GetSearchData("PRACTICE_0003GS.do", FormQueryString(document.form));
	rlaneCd = ComGetEtcData(xml,"rlane_cd");
	generDataCombo(comboObjects[1], rlaneCd);
	if(s_rlane_cd.GetItemCount() > 0){
		s_rlane_cd.SetSelectIndex(0,1);
		s_rlane_cd.SetEnable(true);
	}
	else
		s_rlane_cd.SetEnable(false);
}

/**
 * {s_rlane_cd_OnChange} functions that handling event on change of combobox Lane
 */
function s_rlane_cd_OnChange(){
	s_trd_cd.SetEnable(true);
	getTradeComboData();

}

/**
 * {getTradeComboData} functions that get Trade Code from database and then initializr combobox Trade Code
 */
function getTradeComboData(){
	s_trd_cd.RemoveAll();
	document.form.f_cmd.value = SEARCH02;
	var xml = sheetObjects[0].GetSearchData("PRACTICE_0003GS.do", FormQueryString(document.form));
	trdCd = ComGetEtcData(xml,"trd_cd");
	generDataCombo(comboObjects[2], trdCd);
	if(s_trd_cd.GetItemCount() > 0){
		s_trd_cd.SetSelectIndex(0,1);
		s_trd_cd.SetEnable(true);
	}
	else
		s_trd_cd.SetEnable(false);
}

/**
 * {generDataCombo} functions that generating combobox
 * @param comboObj
 * @param dataStr
 */
function generDataCombo(comboObj, dataStr){
	if (typeof dataStr !== 'undefined'){
		if (dataStr.indexOf("|") != -1)
		{
			var data = dataStr.split("|");
			for (var i = 0; i < data.length; i++){
				comboObj.InsertItem(-1, data[i], data[i]);
			}
		}
		if (dataStr.length > 0 && dataStr.indexOf("|") == -1){
			comboObj.InsertItem(-1, dataStr, dataStr);
		}
	}
}

/**
 * {setComboObject} to put combo objects in global variable "ComboObjects"
 * @param combo_obj
 */
function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}



/**
 * {setTabObject} to put combo objects in global variable "tabObjects"
 */
function setTabObject(tab_obj){
	tabObjects[tabCnt++]=tab_obj;
}

/**
 * {initTab} functions that define the basic properties of the tab on the screen
 */
function initTab(tabObj , tabNo) {
	switch(tabNo) {
	case 1:
		with (tabObj) {
			var cnt=0 ;
				InsertItem( "Summary" , "");
				InsertItem( "Detail" , "");
		}
		break;
	}
}

/**
 * {tab1_OnChange} handling event when tab1 have change
 * @param tabObj
 * @param nItem
 */
function tab1_OnChange(tabObj, nItem) {
	var objs=document.all.item("tabLayer");
	objs[nItem].style.display="Inline";		
	for(var i = 0; i<objs.length; i++){
		  if(i != nItem){
		   objs[i].style.display="none";
		   objs[beforetab].style.zIndex=objs[nItem].style.zIndex - 1 ;
		  }
		}
	beforetab=nItem;
    resizeSheet();
}


/**
 * {initCalender} functions that define the basic properties of the Yearmonth on the screen
 */
function initCalender(){
	var formObj = document.form;
	var ymTo = ComGetNowInfo("ym","-");
	formObj.to_acct_yrmon.value = ymTo;
	var ymFrom = ComGetDateAdd(formObj.to_acct_yrmon.value + "-01","M",-1);
	formObj.fr_acct_yrmon.value = GetDateFormat(ymFrom,"ym");	
}

/**
 * {GetDateFormat} functions that format date by "ym"
 * @param obj
 * @param sFormat
 * @returns {String}
 */
function GetDateFormat(obj, sFormat){
	var sVal = String(getArgValue(obj));
	sVal = sVal.replace(/\/|\-|\.|\:|\ /g,"");
	if (ComIsEmpty(sVal)) return "";
	
	var retValue = "";
	switch(sFormat){
		case "ym":
			retValue = sVal.substring(0,6);
			break;
	}
	retValue = ComGetMaskedValue(retValue,sFormat);
	return retValue;
}

/**
 * {setSheetObject} to put sheet objects in global variable "sheetObjects"
 */
function setSheetObject(sheet_obj){
	//Add sheet object to list 
	sheetObjects[sheetCnt++]=sheet_obj;
}

/**
 * {initSheet} functions that define the basic properties of the sheet on the screen
 * @param sheetObj
 * @param sheetNo
 */
function initSheet(sheetObj,sheetNo) {
	var cnt = 0;
	switch (sheetNo) {
		case 1:
			with(sheetObj){    
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name";
	
	            SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
	
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
	       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center",   ColMerge: 0, SaveName: "ibflag" },
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center",   ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center",   ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
	       	             ];
	            InitColumns(cols);
				SetEditable(1);
				SetAutoSumPosition(1);
				SetWaitImageVisible(0);
				resizeSheet(); 
			}
			break;
		case 2:
			with(sheetObj){
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
				
				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
				
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
		       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Combo",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rev_exp",         KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0, ComboText: "Rev|Exp", ComboCode: "R|E"},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "item",        	 KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
		       	             ];
		            InitColumns(cols);
					SetEditable(1);
					SetAutoSumPosition(1);
					SetWaitImageVisible(0);
					SetSheetHeight(500);
					resizeSheet();
			}
			break;
	}
}



/**
 * {processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
*/
function processButtonClick(){
	// get Sheet 
	var sheetObject1 = sheetObjects[0];
	var sheetObject2 = sheetObjects[1];
	// Get data from form
	var formObj = document.form;
	try {
		//Get button's name
		var srcName = ComGetEvent("name");
		if (srcName == null){
			return;
		}
//		with 4 case "Search | Add | Save | Down Excel"
		switch (srcName){
		case "btn_from_back":
			if(!checkOver3Month(formObj)) return;
			subMonth(formObj.fr_acct_yrmon);
			break;
		case "btn_from_next":
			if (!checkDate(formObj)) return;
			addMonth(formObj.fr_acct_yrmon);
			break;
			
		case "btn_to_back":
			if (!checkDate(formObj)) return;
			subMonth(formObj.to_acct_yrmon);
			break;
		case "btn_to_next":
			if(!checkOver3Month(formObj)) return;
			addMonth(formObj.to_acct_yrmon);
			break;
		case "btn_Retrieve":
			doActionIBSheet(sheetObject1, formObj, IBSEARCH);
			doActionIBSheet(sheetObject2, formObj, IBSEARCH);
			break;
		case "btn_New":
			doActionIBSheet(sheetObject1,formObj,IBRESET);
			break;
		case "btn_DownExcel":
			doActionIBSheet(sheetObject1,formObj,IBDOWNEXCEL);
			break;
		case "btn_DownExcel2":
			doActionIBSheet(sheetObject2,formObj,IBDOWNEXCEL);
			break;
			default:
				break;
		}
		
	}
	catch (e){
//		With Error show message error
		if (e== "[object Error]"){
			ComShowCodeMessage(OBJECT_ERROR);
		}
		else {
			ComShowMessage(e.message);
		}
	}
}
/** 
 * {addMonth} add month when click button next
 * @param obj
 */
function addMonth(obj){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	var ymFrom = ComGetDateAdd(obj.value + "-01","M",1);
	obj.value = GetDateFormat(ymFrom,"ym");
}


/**
 * {subMonth} sub month when click button back
 * @param obj
 */
function subMonth(obj){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	var ymFrom = ComGetDateAdd(obj.value + "-01","M",-1);
	obj.value = GetDateFormat(ymFrom,"ym");
}
/**
 * {doActionIBSheet} functions that define transaction logic between UI and server
 * 
 * @param sheetObj
 * @param formObj
 * @param sAction
 */
function doActionIBSheet(sheetObj,formObj,sAction) {
//	with 4 case ("IBSEARCH | IBSAVE | IBINSERT | IBDOWNEXCEL")
	switch (sAction){
	case IBSEARCH:
		ComOpenWait(true);
		//storage processing
		if(sheetObj.id=="sheet1"){
			formObj.f_cmd.value = SEARCH;
		}
		if (sheetObj.id=="sheet2"){
			formObj.f_cmd.value = SEARCH03;
		}
		sheetObj.DoSearch("PRACTICE_0003GS.do", FormQueryString(formObj));
		break;
	case IBRESET:
		ComOpenWait(true);
		returnDefault(formObj);
		break;
	case IBDOWNEXCEL:
		ComOpenWait(true);
		if (sheetObj.id=="sheet1"){
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
				ComOpenWait(false);
				break;
			}
			sheetObj.Down2Excel({FileName:"Summary.xls",DownCols: makeHiddenSkipCol(sheetObj),Merge:1, SheetDesign:1, KeyFieldMark:0});
			ComOpenWait(false);
		}
		else {								
			let param = {
					 URL:"/opuscntr/PRACTICE_0003_DETAILS.jsp" //Business logic page
					 ,ExtendParam: FormQueryString(formObj)
					 ,FileName:"Details.xls"
					 ,DownCols: makeHiddenSkipCol(sheetObj)
					 ,Merge:1
					 ,SheetDesign:1
					 ,KeyFieldMark:0
					};
			sheetObj.DirectDown2Excel(param);
			ComOpenWait(false);
		}	
		break;
	}
}



/**
 * {returnDefault} function that return Default whent click button "New" 
 * @param formObj
 */
function returnDefault(formObj){
	formObj.reset();
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	initCalender();
	s_jo_crr_cd.SetSelectIndex(0);
	ComOpenWait(false);
}

/**
 * {checkDate} validate Yearmonth from - to
 * @param formObj
 * @returns {Boolean}
 */
function checkDate(formObj){
	var fromDate = formObj.fr_acct_yrmon.value.replaceStr("-","") + "01";
	var toDate   = formObj.to_acct_yrmon.value.replaceStr("-","") + "01";
	if (ComGetDaysBetween(fromDate, toDate) <= 0){
		ComShowMessage("ToDate must be greater than FromDate");
		return false;
	}
		
		
	return true;
	
}
/**
 * {checkOver3Month} check over 3 month will show message
 * @param formObj
 * @returns {Boolean}
 */
function checkOver3Month(formObj){
	var fromDate = formObj.fr_acct_yrmon.value.replaceStr("-","") + "01";
	var toDate   = formObj.to_acct_yrmon.value.replaceStr("-","") + "01";
	if (ComGetDaysBetween(fromDate, toDate) >= 88 && checkAgree==0){
		if (confirm("Year Month over 3 months, do you realy want to get data?")){
			checkAgree=1;
			return true;
		}
		return false;
	}
	return true;
}

/**
 * {resizeSheet} will change size Sheet according to window
 */
function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
	ComResizeSheet(sheetObjects[1]);
}

/**
 * {sheet1_OnSearchEnd} functions that handling event after search 
 * @param sheetObj
 * @param Code
 * @param Msg
 * @param StCode
 * @param StMsg
 */
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg){
	var rowcount = sheetObj.RowCount();
	for (var i=0; i<=rowcount+1; i++) {
		if (sheetObj.GetCellValue(i,"jo_crr_cd") == ""){
			if (sheetObj.GetCellValue(i,"inv_no") != "") {
				sheetObj.SetCellValue(i,"inv_no","");
				sheetObj.SetRowFontColor(i,"#C65911");
				sheetObj.SetRangeFontBold(i,6,i,9,1);
			}
			else if(sheetObj.GetCellValue(i,"inv_no") == ""){
				sheetObj.SetRowBackColor(i,"#F8CBAD");
				sheetObj.SetRangeFontBold(i,6,i,9,1);
			}
		}
	}
}
/**
 * {sheet2_OnSearchEnd} functions that handling event after search 
 * @param sheetObj
 * @param Code
 * @param Msg
 * @param StCode
 * @param StMsg
 */
function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg){
	var rowcount = sheetObj.RowCount();
	for (var i=0; i<=rowcount+1; i++) {
		if (sheetObj.GetCellValue(i,"jo_crr_cd") == ""){
			if (sheetObj.GetCellValue(i,"inv_no") != "") {
				sheetObj.SetCellValue(i,"inv_no","");
				sheetObj.SetRowFontColor(i,"#C65911");
				sheetObj.SetRangeFontBold(i,8,i,11,1);
			}
			else if(sheetObj.GetCellValue(i,"inv_no") == ""){
				sheetObj.SetRowBackColor(i,"#F8CBAD");
				sheetObj.SetRangeFontBold(i,8,i,11,1);
			}
		}
	}
	ComOpenWait(false);
}


/**
 * {sheet1_OnDblClick} functions that handling event when double click
 * @param SheetObj
 * @param Row
 * @param Col
 */
function sheet1_OnDblClick(SheetObj, Row, Col){
	const t0 = performance.now();
	if (SheetObj.GetCellValue(Row,"jo_crr_cd")==""){
		return;
	}
	
	let indexInv=SheetObj.GetCellValue(Row,"inv_no");
	let rowcount = sheetObjects[1].RowCount();
	for (let i=Row; i<= rowcount+1;i++) {
		if (sheetObjects[1].GetCellValue(i,"jo_crr_cd")!=""){
			let indexInvCompare = sheetObjects[1].GetCellValue(i,"inv_no");
			if (indexInvCompare === indexInv){
				tab1.SetSelectedIndex(1);
				let colName =SheetObj.ColSaveName(Col);
				sheetObjects[1].SelectCell(i,colName);
				const t1 = performance.now();
				console.log(t1 - t0);
				break;
				
			}
		}
	}
	
}



