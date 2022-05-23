/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_PRAC_0001.js
*@FileTitle : Pactice 1
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
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
     * @class DOU_PRAC_0001 : DOU_PRAC_0001 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */


//List sheet
var sheetObjects=new Array();
//Quantity Sheet
var sheetCnt=0;
//Quatity row
var rowcount=0;
//Catch event click button
document.onclick=processButtonClick;


//Load UI for sheet and data 
function loadPage(){
	//generate Grid Layout
	for (i = 0; i < sheetObjects.length; i++) {
		//Config UI for sheet
		ComConfigSheet(sheetObjects[i]);
		//initial Sheet with 2 field sheet Object and serial
		initSheet(sheetObjects[i], i + 1);
		//add a preference function
		ComEndConfigSheet(sheetObjects[i]);
		//auto search data after loading finish.
		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	}
	
	
}

//click button will load
function processButtonClick(){
	// get Sheet 
	var sheetObject1 = sheetObjects[0];
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
//			with Search
			case "btn_Retrieve":
//				call function doActionIBSheet with 3 field ( Sheet| Data | Action Search)
				doActionIBSheet(sheetObject1,formObj,IBSEARCH);
				break;
//			with Add
			case "btn_Add":
//				call function doActionIBSheet with 3 field ( Sheet| Data | Action Add)
				doActionIBSheet(sheetObject1,formObj,IBINSERT);
				break;
//			with Save
			case "btn_Save":
//				call function doActionIBSheet with 3 field ( Sheet| Data | Action Save)
				doActionIBSheet(sheetObject1,formObj,IBSAVE);
				break;
//			with DownExcel
			case "btn_DownExcel":
//				call function doActionIBSheet with 3 field ( Sheet| Data | Action DownExcel)
				doActionIBSheet(sheetObject1,formObj,IBDOWNEXCEL);
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

//Set number of sheet
function setSheetObject(sheet_obj){
	//Add sheet object to list 
	sheetObjects[sheetCnt++]=sheet_obj;
}

//initial Sheet
function initSheet(sheetObj,sheetNo) {
	var cnt = 0;
//	Config each sheet
	switch (sheetNo) {
	case 1: 
		with (sheetObj) {
//			String title of column
			var HeadTitle = "STS|Del|Msg Cd|Msg Type|Msg Level|Message|Description";
//			Count the number of head title
			var headCount = ComCountHeadTitle(HeadTitle);
//			Config how to fetch inintialized sheet
//			  SearchMode use as to config search mode by select  one from General, paging, lazyload or realtime server processing modes
//			  with 2 is LazyLoad. Search all data and display search result data on the screen by page as set in Page property value according to the scroll location
			
//			  MergeSheer use as to check and config the overall merge types permitted
//			  with 5 is allow merge in the header rows only
			
//			  Page use as to config paging unit size for paging mode
//			  with 20 is a number of row will display in one page 
			  
//			  DataRowMerge use as to config to allow horizontal merge of the entire row 
//			  with 0 is 
//				- General: select a range of cells or rows 
//				- Use Ctrl key: Drag rows 
			SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});
			
//			Sort is whether to allow sorting by clicking on the header 0,1
//			ColMove is can move column when drag the header
//			HeaderCheck use as to checkAll checkbox 
//			ColResize is whether to allow resize the column
			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			
//			Text is name header will display
//			Align use as to align header text
			var headers = [ { Text : HeadTitle, Align : "Center" }];
			
//			Initialize Header
			InitHeaders(headers, info);
			
//			Type is a column data type
//			Hidden is allow show column or not
//			Width is width of column
//			Align is data alignment
//			ColMerge is allow merge column or not
//			SaveName is A parameter name used to save or search data
//			KeyField is require fill or not
//			Format is format data
//			CalcLogic is calculation equation by column
//			PointCount is a number of decimal places when column tyoe is float
//			UpdateEdit is allow edit when update or not
//			InsertEdit is alow edit when insert or not
//			EditLen is editable data length 
			var cols = [ 
	             { Type : "Status",   Hidden : 1, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
	             { Type : "DelCheck", Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "del_chk",    KeyField : 0 , Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 1, InsertEdit:1}, 
	             { Type : "Text",     Hidden : 0, Width : 100, Align : "Center", ColMerge : 1, SaveName : "err_msg_cd", KeyField : 1,  Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 0, InsertEdit : 1, EditLen: 8 }, 
	             { Type : "Combo",    Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_tp_cd",  KeyField : 1,  Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 1, InsertEdit : 1 , ComboCode:"B|U|S",  ComboText: "BOTH|UI|SERVER"  }, 
	             { Type : "Combo",    Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_lvl_cd", KeyField : 1,  Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 1, InsertEdit : 1 , ComboCode:"W|E|I", ComboText:"WARNING|ERROR|INFO" }, 
	             { Type : "Text",     Hidden : 0, Width : 600, Align : "Left",   ColMerge : 0, SaveName : "err_msg",    KeyField : 1,  Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 1, InsertEdit : 1  }, 
	             { Type : "Text",     Hidden : 0, Width : 100, Align : "Left",   ColMerge : 0, SaveName : "err_desc",   KeyField : 0,  Format : "", CalcLogic : "", PointCount : 0, UpdateEdit : 1, InsertEdit : 1 } 
	             ];
			
//			Inititalize Column
			InitColumns(cols);
			
//			Config overrall editability before intital load
			SetEditable(1);
//			Turn of Wait Image
			SetWaitImageVisible(0);
//			resize sheet by height
			resizeSheet();
		}
		break;
	}
}

// do Action after click button
function doActionIBSheet(sheetObj,formObj,sAction) {
	sheetObj.ShowDebugMsg(false);
//	with 4 case ("IBSEARCH | IBSAVE | IBINSERT | IBDOWNEXCEL")
	switch (sAction){
	case IBSEARCH:
		//storage processing
		formObj.f_cmd.value = SEARCH;
//		Open image wait
		ComOpenWait(true);
//		Execute search function with 2 field 
//		Mapping search page to read search XML and then load XML data internally in IBSheet
//		Condition Search 
		sheetObj.DoSearch("DOU_PRAC_0001GS.do", FormQueryString(formObj));
		break;
	case IBSAVE:
//		check validate
		if (!validateForm(sheetObj,formObj,sAction)) return;
//		request save new row or update row into database 
		formObj.f_cmd.value =MULTI;
		sheetObj.DoSave("DOU_PRAC_0001GS.do", FormQueryString(formObj))
		break;
	case IBINSERT:
//		Add row in sheet
		sheetObj.DataInsert(-1);
		break;
	case IBDOWNEXCEL:
//		check sheet null
		if(sheetObj.RowCount() < 1){
			ComShowCodeMessage("COM132501");
		}else{
//			Down2Excel methol 
//			Filename is file's name excel
//			DownCols is a string connecting all downloading columns using "|"
//			makeHiddenSkipCol is no download columns with hidden= 1(true)
//			SheetDesign is using style of sheet
			sheetObj.Down2Excel({FileName:"Practice1.xls",DownCols: makeHiddenSkipCol(sheetObj), SheetDesign:1});
			
		}
		break;
	}
}
function validateForm(sheetObj, formObj, sAction){
//	regex with 3 char is a uppercase letters and 5 char is number Interger  
	var regrex = new RegExp("^[A-Z]{3}[0-9]{5}$");
//	Get list row Insert
	var sRow = sheetObj.FindStatusRow("I")
	if (sRow!=""){
		var lRow = sheetObj.FindStatusRow("I").split(";");
//		with each row in list
		for (var i =0; i<sRow.length; i++) {
//					check validate Error message code
			if (!regrex.test(sheetObj.GetCellValue(sRow[i],"err_msg_cd"))){
//						Show row and message of row invalid
				ComShowCodeMessage("COM132201","Message code row " +sRow[i])
				return false;
			}
		}
	}
//	else return true
	return true;
}

// resize Sheet
function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
}

//Close image Wait
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
 	ComOpenWait(false);
}

//Constuctor
//function DOU_PRAC_0001() {
//	this.processButtonClick		= tprocessButtonClick;
//	this.setSheetObject 		= setSheetObject;
//	this.loadPage 				= loadPage;
//	this.initSheet 				= initSheet;
//	this.initControl            = initControl;
//	this.doActionIBSheet 		= doActionIBSheet;
//	this.setTabObject 			= setTabObject;
//	this.validateForm 			= validateForm;
//}
