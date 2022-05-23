/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_PRAC_0001HTMLAction.java
*@FileTitle : Pactice 1
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;


/**
 * HTTP Parser<br>
 * - Parsing the value of the HTML DOM object sent to the server through the 
 * 		com.clt.apps.opus.esm.clv.practicetraining screen as a Java variable<br>
 * - Parsing information is converted into an event, put in the request and executed by PracticeTrainingSC<br>
 * - Set EventResponse to request to send execution result from PracticeTrainingSC to View(JSP)<br>
 * @author anhtruong
 * @see PracticeTrainingEvent 참조
 * @since J2EE 1.6
 */

public class DOU_PRAC_0001HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * Creat DOU_PRAC_0001HTMLAction object
	 */
	public DOU_PRAC_0001HTMLAction() {}

	/**
	 * Parsing HTML DOM object's value into Java variable<br>
	 * Parsing the information of HttpRequest as PracticeTrainingEvent and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return event Event interface
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
//		Parsing HTML DOM object's value into Java variable
    	FormCommand command = FormCommand.fromRequest(request);
//    	Create new Event
		DouPrac0001Event event = new DouPrac0001Event();
//		Check Type request
		if(command.isCommand(FormCommand.MULTI)) {
//			Put ErrMsgVO from request in event.
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
//			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO .class));
//			Create new errMsg to search
			ErrMsgVO errMsg = new ErrMsgVO();
//			SetErrMsgCd and setErrMsg for errMsg
			errMsg.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd", ""));
			errMsg.setErrMsg(JSPUtil.getParameter(request, "s_err_msg" , ""));
//			Put it into Event
			event.setErrMsgVO(errMsg);
		}
		return  event;
	}

	/**
	 * Saving the value of execution result in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View(JSP) in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
//		Set response event in HTTPRequest to display in View(JSP)
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * Saving HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface
	 */
	public void doEnd(HttpServletRequest request, Event event) {
//		Set event in HTTPRequest attribute to execute in SC
		request.setAttribute("Event", event);
	}
}