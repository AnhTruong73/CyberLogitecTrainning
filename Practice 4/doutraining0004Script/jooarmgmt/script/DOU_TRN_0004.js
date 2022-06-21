/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004.js
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15 JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview It is a JavaScript file commonly used in work, and calendar-related functions are defined.
     * @author JayTruong
     */

    /**
     * @extends 
     * @class DOU_TRN_0004 : Defines the task script used in the screen for DOU_TRN_0004 creation.
     */

	// common global variable
	var sheetObjects = new Array();
	var sheetCnt = 0;
	var comboObjects = new Array();
	var comboCnt = 0;
	document.onclick=processButtonClick;
	
	
	/**
	 * {loadPage} functions that calls a common function that sets the default settings of the sheet
	 * It is the first called area when fire jsp onload event
	 */
    function loadPage() {
		//generate Grid Layout
		for (i = 0; i < sheetObjects.length; i++) {
			ComConfigSheet(sheetObjects[i]);
			initSheet(sheetObjects[i], i + 1);
			ComEndConfigSheet(sheetObjects[i]);
		}
		//generate comboboxlist data
		for ( var k = 0; k < comboObjects.length; k++) {
			initCombo(comboObjects[k], k + 1);
		}
		//disable calendar
		s_cre_dt_fm.disabled = true;
		s_cre_dt_to.disabled = true;
		s_carrier.SetSelectIndex(0);
		
		//Load data for sheet
		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	}
    
    
    /**
     * {setSheetObject} to put sheet objects in global variable "sheetObjects"
     */
    function setSheetObject(sheet_obj) {
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    
    
    /**
     * {initSheet} functions that define the basic properties of the sheet on the screen
     * @param sheetObj
     * @param sheetNo
     */
    function initSheet(sheetObj, sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    	case 1: // sheet1 init
    		with (sheetObj) {

    			var HeadTitle = "STS|Chk|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
    			var headCount = ComCountHeadTitle(HeadTitle);

    			SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});

    			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
    			var headers = [ { Text : HeadTitle, Align : "Center" }];
    			InitHeaders(headers, info);

    			var cols = [
    	             { Type : "Status",     Hidden : 1, Width : 50,		Align : "Center", 	SaveName : "ibflag" }, 
    	             { Type : "CheckBox",   Hidden : 0, Width : 50,		Align : "Center", 	SaveName : "del_chk" }, 
    	             { Type : "Popup",		Hidden : 0, Width : 70,		Align : "Center", 	SaveName : "jo_crr_cd",		KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1 , EditLen: 3 , AcceptKeys : "E", InputCaseSensitive : 1}, 
    	             { Type : "Combo",		Hidden : 0, Width : 100, 	Align : "Center", 	SaveName : "rlane_cd", 		KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1 , EditLen: 5 , ComboCode:rlaneCombobox, ComboText: rlaneCombobox }, 
    	             { Type : "PopupEdit", 	Hidden : 0, Width : 100, 	Align : "Center", 	SaveName : "vndr_seq", 		KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 6 , AcceptKeys : "N"}, 
    	             { Type : "Popup", 		Hidden : 0, Width : 50, 	Align : "Center", 	SaveName : "cust_cnt_cd", 	KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 2 , AcceptKeys : "E", InputCaseSensitive : 1}, 
    	             { Type : "Popup", 		Hidden : 0, Width : 100, 	Align : "Center", 	SaveName : "cust_seq", 		KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 6 , AcceptKeys : "N"}, 
    	             { Type : "PopupEdit", 	Hidden : 0, Width : 70, 	Align : "Center", 	SaveName : "trd_cd", 		KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 3 , AcceptKeys : "E", InputCaseSensitive : 1}, 
    	             { Type : "Combo", 		Hidden : 0, Width : 70, 	Align : "Center", 	SaveName : "delt_flg", 		KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 , ComboCode:"N|Y", ComboText: "N|Y"}, 
    	             { Type : "Text", 		Hidden : 0, Width : 150, 	Align : "Center", 	SaveName : "cre_dt", 		KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
    	             { Type : "Text", 		Hidden : 0, Width : 180, 	Align : "Left",		SaveName : "cre_usr_id", 	KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
    	             { Type : "Text", 		Hidden : 0, Width : 150, 	Align : "Center", 	SaveName : "upd_dt", 		KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
    	             { Type : "Text", 		Hidden : 0, Width : 180, 	Align : "Left", 	SaveName : "upd_usr_id", 	KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0}
    	             ];

    			InitColumns(cols);
    			SetEditable(1);
    			SetWaitImageVisible(0);
    			resizeSheet();
    		}
    		break;
    	}
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
    		var comboItems = carrierCombobox.split("|");
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
     * {setComboObject} to put combo objects in global variable "ComboObjects"
     * @param combo_obj
     */
    function setComboObject(combo_obj) {
    	comboObjects[comboCnt++] = combo_obj;
    }
    
    
    /**
     * {resizeSheet} will change size Sheet according to window
     */
    function resizeSheet(){
        ComResizeSheet(sheetObjects[0]);
    }
    
    /**
     * {checkDate} validate Yearmonth from - to
     * @param formObj
     * @returns {Boolean}
     */
    function checkDate(formObj){
    	var fromDate = formObj.s_cre_dt_fm.value.replaceStr("-","");
    	var toDate   = formObj.s_cre_dt_to.value.replaceStr("-","");	
    	if (ComGetDaysBetween(fromDate, toDate) < 0 && (fromDate!="" && toDate!="")  ){
    		ComShowMessage("ToDate must be greater than FromDate");
    		return false;
    	}	
    	return true;
    	
    }
    
    /**
     * {processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
    */
    function processButtonClick() {
    	/** *** setting sheet object **** */
    	var sheetObj = sheetObjects[0];
    	/** **************************************************** */
    	var formObj = document.form;
    	try {
    		var srcName = ComGetEvent("name");
    		// return in case class is btn1_1
    		switch (srcName) {
    			//button open calendar from
    			case "btn_calendar_dt_fr":
    				var calendar = new ComCalendar();
					calendar.select(formObj.s_cre_dt_fm, "yyyy-MM-dd");
					break;
				//button open calendar from
				case "btn_calendar_dt_to":
					var calendar = new ComCalendar();
					calendar.select(formObj.s_cre_dt_to, "yyyy-MM-dd");
					break;
//				Button Retrieve 
				case "btn_Retrieve":
					if (!checkDate(formObj)){
						break;
					}
					doActionIBSheet(sheetObj, formObj, IBSEARCH);
					break;
//				button reset all
				case "btn_New":
					doActionIBSheet(sheetObj,formObj,IBRESET);
					break;
//				Button downExcel
				case "btn_DownExcel":
					doActionIBSheet(sheetObj,formObj,IBDOWNEXCEL);
					break;
//				button add 1 row
				case "btn_Add":
					doActionIBSheet(sheetObj,formObj,IBINSERT);
					break;
//				Button save all change on sheet
				case "btn_Save":
					doActionIBSheet(sheetObj,formObj,IBSAVE);
					break;
//				button delete row was selected
				case "btn_Delete":
					doActionIBSheet(sheetObj,formObj,IBDELETE);
					break;
    			default :
    				break;
    		}
    	} catch (e) {
    		if (e == "[object Error]") {
    			ComShowCodeMessage('JOO00001');
    		} else {
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
    function doActionIBSheet(sheetObj, formObj, sAction) {
    	switch (sAction) {
    	case IBSEARCH: // retrieve
//    		ComOpenWait(true);
    		formObj.f_cmd.value=SEARCH;
			sheetObj.DoSearch("DOU_TRN_0004GS.do",FormQueryString(formObj));
    		break;
    	// Reset sheet
    	case IBRESET:
    		setDefault(formObj);
    		break;
    	// down excel
    	case IBDOWNEXCEL:
    		if(sheetObj.RowCount() < 1){
    			ComShowCodeMessage("COM132501");
    		}else{
//    			Down2Excel methol 
//    			Filename is file's name excel
//    			DownCols is a string connecting all downloading columns using "|"
//    			makeHiddenSkipCol is no download columns with hidden= 1(true)
//    			SheetDesign is using style of sheet
    			sheetObj.Down2Excel({FileName:"Practice4.xls",DownCols: makeHiddenSkipCol(sheetObj), Merge:1,SheetDesign:1});
    			
    		}
    		break;
    	// add 1 row to insert
    	case IBINSERT:
    		sheetObj.DataInsert(-1);
    		break;
    	// Save all change on sheet 	
    	case IBSAVE:
    		formObj.f_cmd.value=MULTI;
    		sheetObj.DoSave("DOU_TRN_0004GS.do",FormQueryString(formObj));
    		break;
    	// Delete row
    	case IBDELETE:
	    	for( var i = sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i-- ) {
				if(sheetObj.GetCellValue(i, "del_chk") == 1){
					sheetObj.SetRowHidden(i, 1);
					sheetObj.SetRowStatus(i,"D");
				}
			}
			break;
	    
		}
    }
    
    /**
     * {setDefault} function that return Default whent click button "New" 
     * @param formObj
     */
    function setDefault(formObj) {
    	formObj.reset();
    	sheetObjects[0].RemoveAll();
    	s_carrier.SetSelectIndex(0);
    }
    
    /**
     * {s_carrier_OnCheckClick} functions that handling event check click in combobox "Carrier"
     * @param Index
     * @param Code
     * @param Checked
     */
    function s_carrier_OnCheckClick(Index, Code, Checked) {
    	// quantity of combobox 
    	var count = s_carrier.GetItemCount();
    	var checkSelectCount = 0;
    	var bChk = s_carrier.GetItemCheck(Code);
    	// if check
    	if (Code === 0){
    		var bChk = s_carrier.GetItemCheck(Code);
            if(bChk){
                for(var i=1 ; i < count ; i++) {
                	s_carrier.SetItemCheck(i,false);
                }
            }
    	}
    	//uncheck
    	else {
            var bChk=s_carrier.GetItemCheck(Code);
            if (bChk) {
            	s_carrier.SetItemCheck(0,false);
            }
        }
    	for (var i = 0; i < count; i++){
    		if (s_carrier.GetItemCheck(i)){
    			checkSelectCount += 1;
    		}	
    	}
    	if(checkSelectCount === 0) {
    		s_carrier.SetItemCheck(0,true,false);
    	}
    	
    	
    }
    /**
     * {sheet1_OnPopupClick} handle event click button show popup in cell
     * @param SheetObj
     * @param Row
     * @param Col
     */
    function sheet1_OnPopupClick(SheetObj,Row,Col){
    	var colName = SheetObj.ColSaveName(Col);
    	switch (colName) {
//	    	Ta sử dụng hàm ComOpenPopup(sUrl, iWidth, iHeight, sFunc, sDisplay, bModal, b2ndSheet, iRow, iCol, iSheetIdx, sWinName, sScroll, addHeight)
//	
//	    	Tham số: 
//	    	+ sUrl: Popup's url
//	    	+ iWidth: width of Popup
//	    	+ iHeight: height of Popup
//	    	+ sFunc: JavaScript function của window cha(màn hình mở popup này) nhận data when the final confirmation is made in the popup
//	    	+ sDisplay: set có hiển thị các column của grid hay ẩn nó đi (1: visible, 0: hidden)
//	    	+ bModal: set popup là modal hay không (true:Modal, false:normal popup), default=false
//	    	+ b2ndSheet: true: 2 sheet pop-ups được mở,  false: 1 single pop-up được mở (default=false)
//	    	+ iRow: Row Index of Cell of Sheet
//	    	+ iCol: Col Index of Cell of Sheet
//	    	+ iSheetIdx: Index of sheetObjects array of Sheet
//	    	+ sWinName: tên của popup window hoặc dialogArguments, default=compopup
//	    	+ sScroll: có tạo scrollbars hay không (no | yes), default=no
//	    	+ addHeight: dialogTop của popup window (Distance of the dialog box from the top edge of the desktop.)
//	
//	    	Để mở 1 dialog Popup ta set bModal là true
//	    	Để mở 1 window Popup ta set bModal là false
    		case "jo_crr_cd":
    			ComOpenPopup('COM_ENS_0N1.do', 800, 600, 'setCarrierInPopup', '1,0,1',Row,Col,1,1,1, true);
    			break;
    		case "trd_cd":
    			ComOpenPopup('COM_COM_0012.do', 800, 600, 'setTradeInPopup', '1,0,1',Row,Col,1,1,1, true);
    			break;
    		case "vndr_seq":
    			ComOpenPopup('COM_COM_0007.do', 800, 600, 'setVendorCodeInPopup', '1,0,1',Row,Col,1,1,1, true);
    			break;
    		case "cust_cnt_cd":
    		case "cust_seq":
    			ComOpenPopup('CustomerPopup.do', 700, 600, 'setCustomerCodeInPopup', '1,0,1',Row,Col,1,1,1, true);
    			break;
    	}
    	
    }
    /**
     * {setCarrierInPopup} when click button OK will return array. this function will get correct data to set cell value
     * @param aryPopupData
     */
    function setCarrierInPopup(aryPopupData) {
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", aryPopupData[0][3]);
    }
    
    function setTradeInPopup(aryPopupData) {
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "trd_cd", aryPopupData[0][3]);
    }
    
    function setVendorCodeInPopup(aryPopupData) {
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "vndr_seq", aryPopupData[0][2]);
    }
    
    function setCustomerCodeInPopup(aryPopupData) {
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_cnt_cd", aryPopupData[0][2]);
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_seq", aryPopupData[0][3]);
    }
    
    function sheet1_OnBeforeSearch(){
    	ComOpenWait(true);
    }
    
    function sheet1_OnSearchEnd(){
    	ComOpenWait(false);
    }
    
    function sheet1_OnBeforeSave(){
    	ComOpenWait(true);
    }
    
    
    /**
     * {sheet1_OnSaveEnd} handle event duplicate PK will focus this cell was duplicated 
     * @param SheetObj
     * @param Code
     * @param Msg
     * @param StCode
     * @param StMsg
     */
    function sheet1_OnSaveEnd(SheetObj,Code, Msg, StCode, StMsg){
    	ComOpenWait(false);
    	console.log(Code);
    	if(Code<0){
    		var sRow = SheetObj.FindStatusRow("I");
    		console.log(sRow);
    		if (sRow != "") {
    			var lRow = sRow.split(";");
    			console.log(lRow);
    			console.log(lRow.length);
				for(var i =0; i<lRow.length;i++){
					console.log(lRow[0]);
					console.log(Msg);
    				if (Msg.includes(SheetObj.GetCellValue(lRow[i],"jo_crr_cd")) &&
    						Msg.includes(SheetObj.GetCellValue(lRow[i],"rlane_cd"))){
    					SheetObj.SetRowBackColor(lRow[i],"#DF4759");
    				}
    			}
    		}
    	}
    	else {
    		doActionIBSheet(SheetObj, document.form, IBSEARCH);
    	}
    	
    }
    
    