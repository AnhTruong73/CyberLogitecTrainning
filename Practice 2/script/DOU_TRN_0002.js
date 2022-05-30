/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtDBDAO.java
*@FileTitle : Pactice 2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/

// Common global variable
var sheetObjects=new Array();
var sheetCnt=0;
var rowcount=0;
//Define an event handler that receives and handles button click events
document.onclick=processButtonClick;


/**
 * {loadPage} functions that calls a common function that sets the default settings of the sheet
 * It is the first called area when fire jsp onload event
*/
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
	}
	doActionIBSheet(sheetObjects[0], document.form1, IBSEARCH);
	
}
/**
 * {processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
*/
function processButtonClick(){
	// get Sheet 
	var sheetObject1 = sheetObjects[0];
	var sheetObject2 = sheetObjects[1]
	// Get data from form
	var formObj = document.form1;
	try {
		//Get button's name
		var srcName = ComGetEvent("name");
		if (srcName == null){
			return;
		}
//		with case "Search | Add | Save"
		switch (srcName){
//			with Search
			case "btn_Retrieve":
//				call function doActionIBSheet with 3 field ( Sheet| Data | Action Search)
				clearSheet();
				doActionIBSheet(sheetObject1,formObj,IBSEARCH);
				break;
				// Add row in master sheet
			case "btn_rowadd_mst":
				clearSheet();
				doActionIBSheet(sheetObject1,formObj,IBINSERT);
				break;
				// Add row in detail sheet
			case "btn_rowadd_dtl":
				doActionIBSheet(sheetObject2,formObj,IBINSERT);
				break;
				// Save event
			case "btn_Save":
				//Confirm before Save
				if(confirm("Do you want to Save?")){
					if((sheetObjects[0].RowCount("I")+sheetObjects[0].RowCount("U")+sheetObjects[0].RowCount("D")) >0 ){
						doActionIBSheet(sheetObjects[0],formObj,IBSAVE);
	          	  	} 
					if((sheetObjects[1].RowCount("I")+sheetObjects[1].RowCount("U")+sheetObjects[1].RowCount("D")) >0 ) {
	          			doActionIBSheet(sheetObjects[1],formObj,IBSAVE);
	          	  	}
				}
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
 * {initSheet} functions that define the basic properties of the sheet on the screen
*/
function initSheet(sheetObj,sheetNo){
	var cnt =0; 
	switch (sheetNo){
	case 1: 
		with(sheetObj) {
			var HeadTitle = "STS|Del|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date"
			var headCount = ComCountHeadTitle(HeadTitle);
			var prefix="sheet1_";
			SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, FrozenCol:0, DataRowMerge : 0});
			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [ { Text : HeadTitle, Align : "Center" }];
			
			InitHeaders(headers, info);
			var cols =[{Type:"Status",   Hidden:1, Width: 10, Align:"Center", ColMerge:0, SaveName:"ibflag"},
			           {Type:"DelCheck", Hidden:0, Width: 40, Align:"Center", ColMerge:0, SaveName:"del_chk",          KeyField : 0 , UpdateEdit : 1, InsertEdit:1},
			           {Type: "Text",    Hidden:0, Width:100, Align:"Center", ColMerge:0, SaveName:"ownr_sub_sys_cd",  KeyField : 0 , UpdateEdit : 1, InsertEdit:1 ,EditLen:3, AcceptKeys : "E", InputCaseSensitive : 1},
			           {Type: "Text",    Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_id",       KeyField : 1 , UpdateEdit : 0, InsertEdit:1, EditLen:7,  InputCaseSensitive : 1},
			           {Type: "Text",    Hidden:0, Width:250, Align:"Left",   ColMerge:0, SaveName:"intg_cd_nm",       KeyField : 0 , UpdateEdit : 1, InsertEdit:1, InputCaseSensitive : 1},
			           {Type: "Text",    Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_len",      KeyField : 0 , UpdateEdit : 1, InsertEdit:1, AcceptKeys : "N"},
			           {Type: "Combo",   Hidden:0, Width:120, Align:"Center", ColMerge:0, SaveName:"intg_cd_tp_cd",    KeyField : 0 , UpdateEdit : 1, InsertEdit:1, ComboCode:"G|T",  ComboText: "General Code|Table Code"},
			           {Type: "Text",    Hidden:0, Width:200, Align:"Left",   ColMerge:0, SaveName:"mng_tbl_nm",       KeyField : 0 , UpdateEdit : 1, InsertEdit:1, InputCaseSensitive : 1},
			           {Type: "Text",    Hidden:0, Width:400, Align:"Left",   ColMerge:0, SaveName:"intg_cd_desc",     KeyField : 0 , UpdateEdit : 1, InsertEdit:1},
			           {Type: "Combo",   Hidden:0, Width:40,  Align:"Center", ColMerge:0, SaveName:"intg_cd_use_flg",  KeyField : 0 , UpdateEdit : 1, InsertEdit:1, ComboCode:"Y|N",ComboText: "Y|N"},
			           {Type: "Text",    Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"cre_usr_id",       KeyField : 0 , UpdateEdit : 0, InsertEdit:0},
			           {Type: "Date",    Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"cre_dt",           KeyField : 0 , UpdateEdit : 0, InsertEdit:0, Format : "Ymd"},
			           {Type: "Text",    Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"upd_usr_id",       KeyField : 0 , UpdateEdit : 0, InsertEdit:0},
			           {Type: "Date",    Hidden:0, Width:10,  Align:"Center", ColMerge:0, SaveName:"upd_dt",           KeyField : 0 , UpdateEdit : 0, InsertEdit:0, Format : "Ymd"}]
//			Inititalize Column
			InitColumns(cols);
			
//			Config overrall editability before intital load
			SetEditable(1);
			SetSheetHeight(300);
		}
		break;
	case 2:
		with(sheetObj){
			var HeadTitle = "STS|Del|Cd ID|Cd Val|Display Name|Description Remark|Order"
			var headCount = ComCountHeadTitle(HeadTitle);
			var prefix="sheet2_";
			SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});
			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [ { Text : HeadTitle, Align : "Center" }];
			InitHeaders(headers, info);
			
			var cols =[{Type:"Status",   Hidden:1, Width: 10, Align:"Center", ColMerge:0, SaveName:"ibflag"},
			           {Type:"DelCheck", Hidden:0, Width: 50, Align:"Center", ColMerge:0, SaveName:"del_chk",             KeyField : 0 ,   UpdateEdit : 1, InsertEdit:1},
			           {Type: "Text",    Hidden:1, Width:100, Align:"Center", ColMerge:0, SaveName:"intg_cd_id",          KeyField : 1 ,   UpdateEdit : 0, InsertEdit:0},
			           {Type:"Text",     Hidden:0, Width:100, Align:"Center", ColMerge:0, SaveName:"intg_cd_val_ctnt",    KeyField:1,      UpdateEdit:0,   InsertEdit:1,  AcceptKeys : "N"},
			           {Type: "Text",    Hidden:0, Width:200, Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_desc", KeyField : 0 ,   UpdateEdit : 1, InsertEdit:1,  AcceptKeys : "E", InputCaseSensitive : 1},
			           {Type:"Text",     Hidden:0, Width:600, Align:"Left",   ColMerge:0, SaveName:"intg_cd_val_desc",    KeyField:0,      UpdateEdit:1,   InsertEdit:1,  AcceptKeys : "E", InputCaseSensitive : 1},
					   {Type:"Text",     Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_seq",  KeyField:0,      UpdateEdit:1,   InsertEdit:1,  AcceptKeys : "N"}]
			InitColumns(cols);
            SetEditable(1);
            resizeSheet();	
		}
		break;
	}
}

/**
 * {setSheetObject} to put sheet objects in global variable "sheetObjects"
 */
function setSheetObject(sheet_obj){
	//Add sheet object to list 
	sheetObjects[sheetCnt++]=sheet_obj;
}
/**
 * {doActionIBSheet} functions that define transaction logic between UI and server
 * 
 * @param sheetObj
 * @param formObj
 * @param sAction
 */
function doActionIBSheet(sheetObj,formObj,sAction) {
	switch (sAction){
		case IBSEARCH:
			formObj.f_cmd.value = SEARCH;
			sheetObj.DoSearch("DOU_TRN_0002GS.do", FormQueryString(formObj));
			break;
		case IBSEARCHAPPEND:
			formObj.f_cmd.value = SEARCH01;
			sheetObj.DoSearch("DOU_TRN_0002GS.do", FormQueryString(formObj));
			break;
		case IBINSERT:
			var newrow= sheetObj.DataInsert(-1);
			if (formObj.codeid.value != ""){
				sheetObj.SetCellValue(newrow,"intg_cd_id",formObj.codeid.value)
			}
			break;
		case IBSAVE:
			if (!validateForm(sheetObj,formObj,sAction)){
				return;
			}
			else {
				if (sheetObj.id == "sheet1") {
					formObj.f_cmd.value = MULTI;
				}
				if (sheetObj.id == "sheet2") {
					formObj.f_cmd.value = MULTI01;
				}
				var SaveStr = sheetObj.GetSaveString();
				if (sheetObj.IsDataModified && SaveStr == "") return;
				
				var rtnData = sheetObj.GetSaveData("DOU_TRN_0002GS.do", SaveStr,FormQueryString(formObj));
				sheetObj.LoadSaveData(rtnData);
//				sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj));
				break;
			}
	}
}
/**
 * {resizeSheet} will change size Sheet according to window
 */
function resizeSheet() {
	ComResizeSheet(sheetObjects[1]);
	
}


/**
 * {sheet1_OnDblClick} define an event handler when double click row in master sheet
 * @param sheetObj
 * @param Row
 * @param Col
 */
function sheet1_OnDblClick(sheetObj, Row, Col) {
	ComSetObjValue(document.form1.codeid, sheetObj.GetCellValue(Row, "intg_cd_id"));
	doActionIBSheet(sheetObjects[1],document.form1,IBSEARCHAPPEND);
	document.getElementById("btn_rowadd_dtl").disabled=false;
}

/**
 * {clearSheet} clear detail sheet after Retrive event
 */
function clearSheet(){
	document.form1.codeid.value ="";
	sheetObjects[1].RemoveAll();
	document.getElementById("btn_rowadd_dtl").disabled=true;
}


/**
 * {sheet1_OnSaveEnd} event after save
 */
function sheet1_OnSaveEnd(sheetObj,Code, Msg, StCode, StMsg) {
	if (Code >= 0){
		ComShowCodeMessage("COM130102","Data");
		doActionIBSheet(sheetObj, document.form1, IBSEARCH);
	}
}

/**
 * {sheet2_OnSaveEnd} event after save
 */
function sheet2_OnSaveEnd(sheetObj,Code, Msg, StCode, StMsg) {
	if (Code >= 0){
		doActionIBSheet(sheetObj, document.form1, IBSEARCHAPPEND);
	}
}

/**
 * {validateForm} check validate when client insert data 
 * @param sheetObj
 * @param formObj
 * @param sAction
 * @returns {Boolean}
 */
function validateForm(sheetObj, formObj, sAction){
//	regex with 2 char is a uppercase letters and 5 char is number Interger  
	var regrex = new RegExp("^[A-Z]{2}[0-9]{5}$");
//	Get list row Insert
	var sRow = sheetObj.FindStatusRow("I");
	if (sRow!=""){
		var lRow = sheetObj.FindStatusRow("I").split(";");
//		with each row in list
		for (var i =0; i<lRow.length; i++) {
//					check validate Error message code
			var anh = sheetObj.GetCellValue(lRow[i],"intg_cd_id");
			if (!regrex.test(sheetObj.GetCellValue(lRow[i],"intg_cd_id"))){
//						Show row and message of row invalid
				ComShowCodeMessage("COM132201","Message code row " +lRow[i])
				return false;
			}
		}
	}
//	else return true
	return true;
}






