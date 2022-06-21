/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004HTMLAction.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15 JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15  JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;


/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.doutraining0004 will parsing the value of the HTML DOM object sent to the server through the screen as a Java variable<br>
 * - Convert the parsing information into Event, put it in the request and request execution with DouTraining0004SC<br>
 * - Set EventResponse to request to send execution result from DouTraining0004SC to View (JSP)<br>
 * @author JayTruong
 * @see Reference DouTraining0004Event
 * @since J2EE 1.6
 */

public class DOU_TRN_0004HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * Create an object DOU_TRN_0004HTMLAction
	 */
	public DOU_TRN_0004HTMLAction() {}

	/**
	 * Parsing the HTML DOM object's Value as a Java variable<br>
	 * Parsing the information of HttpRequst as DouTraining0004Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event object that implements the interface Event
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		DouTrn0004Event event = new DouTrn0004Event();
		
		//for manage like CRUD 
		if(command.isCommand(FormCommand.MULTI)) {
			event.setJooArMnVOS((JooArMnVO[])getVOs(request, JooArMnVO .class,""));
		}
		// for search
		else if(command.isCommand(FormCommand.SEARCH)) {
			JooArMnVO jooArMnVO = new JooArMnVO();
			jooArMnVO.setJoCrrCd(JSPUtil.getParameter(request, "s_carrier", ""));
			jooArMnVO.setVndrSeq(JSPUtil.getParameter(request, "s_vndr_seq", ""));
			jooArMnVO.setSCreDtFm(JSPUtil.getParameter(request, "s_cre_dt_fm", ""));
			jooArMnVO.setSCreDtTo(JSPUtil.getParameter(request, "s_cre_dt_to", ""));
			event.setJooArMnVO(jooArMnVO);
		}

		return event;
	}

	/**
	 * Saving the business scenario execution result value in the attribute of HttpRequest<br>
	 * ResultSet that transmits execution result from ServiceCommand to View (JSP) is set in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse object that implements the interface EventResponse
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * Save HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event object that implements the interface Event
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}