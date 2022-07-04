/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.js
*@FileTitle : ESM_DOU_0108
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.02 JayTruong
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
     * @class ESM_DOU_0108 : ESM_DOU_0108 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */


// Common global variable 
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";
var sheetObjects=new Array();
var sheetCnt=0;
var tabObjects=new Array();
var tabCnt=0;
var currentTab=1;
var checkAgree= 0;

var signalToConfirm ="";
var searchOption="";
var searchOptionSumary="";
var searchOptionDetails="";

var searchOptionForDbl = "";
document.onclick=processButtonClick;

/**
 * {loadPage} functions that calls a common function that sets the default settings of the sheet
 * It is the first called area when fire jsp onload event
*/
function loadPage(){	
	// initializr combobox
	for ( var k = 0; k < comboObjects.length; k++) {
		initCombo(comboObjects[k], k + 1);
	}
	// initializr calendar with default value and disable input
	initCalendar();
	s_fr_acct_yrmon.disabled = true;
	s_to_acct_yrmon.disabled = true;
	s_jo_crr_cd.SetSelectIndex(0);
	
	// initializr tab
	for(k = 0;k < tabObjects.length; k++){
		initTab(tabObjects[k], k + 1);
		tabObjects[k].SetSelectedIndex(0);
	}
	// initializr sheet
	for(i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	searchOption=setSearchOption();
	//Search Sumary tab in first - time 
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	signalToConfirm = "FISRTTIME";
}

function setSearchOption(){
	let formObj = document.form;
	let nameSearchOption = "";
	nameSearchOption+=formObj.s_fr_acct_yrmon.value;
	nameSearchOption+=formObj.s_to_acct_yrmon.value;
	nameSearchOption+=formObj.s_jo_crr_cd.value;
	nameSearchOption+=formObj.s_rlane_cd.value;
	nameSearchOption+=formObj.s_trd_cd.value;
	return nameSearchOption;
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
	        SetDropHeight(250);
	        ValidChar(2,1);
		}
		// partner from ETCDATA and split "|"
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
			// insert one by one item into comboObj
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
	// when check "ALL"
	if (Code === 0){
		var bChk = s_jo_crr_cd.GetItemCheck(Code);
		// selected all will uncheck all and disable s_rlane_cd,s_trd_cd
        if(bChk){
            for(var i=1 ; i < count ; i++) {
            	s_jo_crr_cd.SetItemCheck(i,false,0);
            }
            s_rlane_cd.RemoveAll();
         	s_trd_cd.RemoveAll();
        	s_rlane_cd.SetEnable(false);
        	s_trd_cd.SetEnable(false);
        }
	}
	// else 
	else {
        var bChk=s_jo_crr_cd.GetItemCheck(Code);
        // selected item
        if (bChk) {
        	// uncheck "All" and enable rlane
        	s_jo_crr_cd.SetItemCheck(0,false,0);
        }
    }
	//when check more
	for (var i = 0; i < count; i++){
		if (s_jo_crr_cd.GetItemCheck(i)){
			checkSelectCount += 1;
		}	
	}
	// when no item was checked
	if(checkSelectCount === 0) {
		s_jo_crr_cd.SetItemCheck(0,true,0);
		s_rlane_cd.RemoveAll();
		s_trd_cd.RemoveAll();
     	s_rlane_cd.SetEnable(false);
     	s_trd_cd.SetEnable(false);
	 }
}

function s_jo_crr_cd_OnBlur(){
	if (document.form.s_jo_crr_cd.value !== 'All'){
			ComOpenWait(true);
			getLaneComboData();
			s_trd_cd.SetEnable(false);
			ComOpenWait(false);
	}	
}


/**
 * {getLaneComboData} functions that get Lane from database and then initializr combobox Lane
 */
function getLaneComboData(){
	s_rlane_cd.RemoveAll();
 	s_trd_cd.RemoveAll();
 	//get item of combobox "Lane" from server 
	document.form.f_cmd.value = SEARCH01;
	var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
	rlaneCd = ComGetEtcData(xml,"rlane_cd");
	//generate Data combobox "Lane"
	generDataCombo(comboObjects[1], rlaneCd);
	// when ETCdata return > 0 will selected first item and enable combobox "Lane"
	if(s_rlane_cd.GetItemCount() > 0){
//		s_rlane_cd.SetSelectIndex(0,1);
		s_rlane_cd.SetEnable(true);
	}
	else
		s_rlane_cd.SetEnable(false);
}

/**
 * {s_rlane_cd_OnChange} functions that handling event on change of combobox Lane
 */
function s_rlane_cd_OnChange(){
	// Get Data and set enable combobox "Trade code" 
	s_trd_cd.SetEnable(true);
	getTradeComboData();
}

/**
 * {s_rlane_cd_OnFocus} functions that handling event on focus of combobox Lane
 */
function s_rlane_cd_OnFocus(){
	s_rlane_cd.SetOutLineColor("#B8D6F6");
}

/**
 * {s_trd_cd_OnFocus} functions that handling event on focus of combobox Trade
 */
function s_trd_cd_OnFocus(){
	s_trd_cd.SetOutLineColor("#B8D6F6");
}

/**
 * {getTradeComboData} functions that get Trade Code from database and then initializr combobox Trade Code
 */ 
function getTradeComboData(){
	s_trd_cd.RemoveAll();
	//get item of combobox "Lane" from server 
	document.form.f_cmd.value = SEARCH02;
	var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
	trdCd = ComGetEtcData(xml,"trd_cd");
	//generate Data combobox "Trade code"
	generDataCombo(comboObjects[2], trdCd);
	// when ETCdata return > 0 will selected first item and enable combobox "Trade code"
	if(s_trd_cd.GetItemCount() > 0){
//		s_trd_cd.SetSelectIndex(0,1);
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
//		switch tab with zIndex
		if(i != nItem){
			objs[i].style.display="none";
			objs[currentTab].style.zIndex=objs[nItem].style.zIndex - 1 ;
		}
	}
	signalToConfirm=handleSignal();
	switch (signalToConfirm) {
		case "FISRTTIME":
			doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
			signalToConfirm="";
			break;
		case "SEARCHOPTIONSUMARYWASCHANGE":
			if (sheetObjects[0].RowCount()>0){
				if (confirm("Search Option was changed! Do you want retrieve Summary sheet?")){
					doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
					signalToConfirm="";
				}
			}
			else{
				doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
			}
			break;
		case "SEARCHOPTIONDETAILSWASCHANGE":
			if (sheetObjects[1].RowCount()>0){
				if (confirm("Search Option was changed! Do you want retrieve Details sheet?")){
					doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
					signalToConfirm="";
				}
			}
			else {
				doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
			}
			break;
		case "DOUBLECLICK":
			signalToConfirm="";
			break;
				
	}
	currentTab=nItem;
    resizeSheet();
    
    
}
function handleSignal(){
	if (signalToConfirm==="FISRTTIME")
		return signalToConfirm;
	else if (signalToConfirm==="DOUBLECLICK")
		return signalToConfirm;
	else {
		if (searchOptionSumary!==searchOption && currentTab===1){
			return "SEARCHOPTIONSUMARYWASCHANGE";
		}
		else if (searchOptionDetails!==searchOption && currentTab===0){
			return "SEARCHOPTIONDETAILSWASCHANGE";
		}
	}
}


/**
 * {initCalendar} functions that define the basic properties of the Yearmonth on the screen
 */
function initCalendar(){
	var formObj = document.form;
	var ymTo = ComGetNowInfo("ym","-");
	formObj.s_to_acct_yrmon.value = ymTo;
	var ymFrom = ComGetDateAdd(formObj.s_to_acct_yrmon.value + "-01","M",-1);
	formObj.s_fr_acct_yrmon.value = GetDateFormat(ymFrom,"ym");	
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
	
	            var info    = { Sort:0, ColMove:0, HeaderCheck:0, ColResize:1 };
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
				
	            var info    = { Sort:0, ColMove:0, HeaderCheck:0, ColResize:1 };
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
 * {addMonth} add month when click button next
 * @param obj
 */
function addMonth(obj){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	var ymFrom = ComGetDateAdd(obj.value + "-01","M",1);
	obj.value = GetDateFormat(ymFrom,"ym");
}

function checkSearchOption(formObj){
	var error  ="";
	if (formObj.s_rlane_cd.value === "" && formObj.s_jo_crr_cd.value !== "All"){
		s_rlane_cd.SetOutLineColor("#FF0000");
		error = "Rlane";
	}
	if (formObj.s_trd_cd.value === "" && formObj.s_rlane_cd.value !== ""){
		s_trd_cd.SetOutLineColor("#FF0000");
		error = "Trade";
	}
	if (error!== ""){
		ComShowCodeMessage('COM130403',error);
		return false;
	}
	return true;
	
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
//		with 4 case "Search | Add | Save | Down Excel | Down Excel 2"
		switch (srcName){
		case "btn_from_back":
			subMonth(formObj.s_fr_acct_yrmon);
			break;
		case "btn_from_next":
			if (!checkDate(formObj)) return;
			addMonth(formObj.s_fr_acct_yrmon);
			break;
		case "btn_to_back":
			if (!checkDate(formObj)) return;
			subMonth(formObj.s_to_acct_yrmon);
			break;
		case "btn_to_next":
			addMonth(formObj.s_to_acct_yrmon);
			break;
		case "btn_Retrieve":
			if(!checkOver3Month(formObj)) return;
			if(!checkSearchOption(formObj)) return;
			if (currentTab===0){
				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
				sheetObjects[1].RemoveAll();
			}
			else {
				doActionIBSheet(sheetObject2, formObj, IBSEARCH);
				sheetObjects[1].RemoveAll();
			}
				
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
		}
		searchOption=setSearchOption();
		
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
		var opt = { Sync : 1 };
		ComOpenWait(true);
		//storage processing
		if(sheetObj.id=="sheet1"){
			formObj.f_cmd.value = SEARCH;
			sheetObj.DoSearch("ESM_DOU_0108GS.do", FormQueryString(formObj),opt);
		}
		else if (sheetObj.id=="sheet2"){
			formObj.f_cmd.value = SEARCH03;
			sheetObj.DoSearch("ESM_DOU_0108GS.do", FormQueryString(formObj),opt);
		}
		
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
			sheetObjects[0].Down2ExcelBuffer(true);
			sheetObjects[0].Down2Excel({FileName:"Excel.xls",DownCols: makeHiddenSkipCol(sheetObjects[0]),Merge:1, SheetDesign:1, KeyFieldMark:0,SheetName:'Summary'});
			sheetObjects[1].Down2Excel({FileName:"Excel.xls",DownCols: makeHiddenSkipCol(sheetObjects[1]),Merge:1, SheetDesign:1, KeyFieldMark:0,SheetName:'Details'});
			sheetObjects[0].Down2ExcelBuffer(false);
			ComOpenWait(false);
		}
		else {
//			sheetObj.Down2ExcelBuffer(true);
			formObj.f_cmd.value = COMMAND01;
			let param ={
					URL:"/opuscntr/ESM_DOU_0108DL.do",
					ExtendParam:FormQueryString(formObj),
					FileName:"Details.xls",
					DownCols: makeHiddenSkipCol(sheetObjects[1]),
					Merge:1,
					SheetDesign:1,
					KeyFieldMark:0,
					SheetName:'Details'
			};
			sheetObjects[1].DirectDown2Excel(param);
			
//			formObj.f_cmd.value = COMMAND01;
//			let param2 ={
//					URL:"/opuscntr/PRACTICE_0003DL.do",
//					ExtendParam:FormQueryString(formObj),
//					FileName:"Details.xls",
//					DownCols: makeHiddenSkipCol(sheetObj),
//					Merge:1,
//					SheetDesign:1,
//					KeyFieldMark:0,
//					SheetName:'Summary'
//			};
//			sheetObjects[1].DirectDown2Excel(param2);
//			sheetObj.Down2ExcelBuffer(false);
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
	initCalendar();
	s_jo_crr_cd.SetSelectIndex(0);
	ComOpenWait(false);
}

/**
 * {checkDate} validate Yearmonth from - to
 * @param formObj
 * @returns {Boolean}
 */
function checkDate(formObj){
	var fromDate = formObj.s_fr_acct_yrmon.value.replaceStr("-","") + "01";
	var toDate   = formObj.s_to_acct_yrmon.value.replaceStr("-","") + "01";
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
	var fromDate = formObj.s_fr_acct_yrmon.value.replaceStr("-","") + "01";
	var toDate   = formObj.s_to_acct_yrmon.value.replaceStr("-","") + "01";
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
	document.form.f_cmd=SEARCH03;
	searchOptionForDbl= FormQueryString(document.form)
	ComOpenWait(false);
	searchOptionSumary=setSearchOption();
	if (searchOptionSumary!==""&& searchOptionSumary===searchOptionDetails){
		searchOption=searchOptionSumary;
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
	searchOptionDetails =setSearchOption();
	if (searchOptionSumary!==""&& searchOptionSumary===searchOptionDetails){
		searchOption=searchOptionSumary;
	}
}


/**
 * {sheet1_OnDblClick} functions that handling event when double click
 * @param SheetObj
 * @param Row
 * @param Col
 */
function sheet1_OnDblClick(SheetObj, Row, Col){
	if (SheetObj.GetCellValue(Row,"jo_crr_cd")==""){
		return;
	}
	if (sheetObjects[1].RowCount() <=0 ){ 
//		doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
		ComOpenWait(true);
		document.form.f_cmd.value = SEARCH03;
		var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", searchOptionForDbl);
		sheetObjects[1].LoadSearchData(xml,{Sync:1});
	}
	signalToConfirm = "DOUBLECLICK"
	let indexInv=SheetObj.GetCellValue(Row,"csr_no");
	let rowcount = sheetObjects[1].RowCount();
	for (let i=Row; i<= rowcount+1;i++) {
		if (sheetObjects[1].GetCellValue(i,"jo_crr_cd")!=""){
			let indexInvCompare = sheetObjects[1].GetCellValue(i,"csr_no");
			if (indexInvCompare === indexInv){
				tab1.SetSelectedIndex(1);
				let colName =SheetObj.ColSaveName(Col);
				sheetObjects[1].SelectCell(i,colName);
				break;
				
			}
		}
	}
}



