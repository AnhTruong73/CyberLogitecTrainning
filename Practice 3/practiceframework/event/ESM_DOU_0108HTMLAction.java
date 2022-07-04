/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108HTMLAction.java
*@FileTitle : ESM_DOU_0108HTMLAction
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06 
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.06  JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practiceframework.event;


import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.event.EsmDou0108Event;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

/**
 * HTTP Parser<br>
 * - Parsing the value of the HTML DOM object sent to the server through the 
 * 		com.clt.apps.opus.esm.clv.practiceframework screen as a Java variable<br>
 * - Parsing information is converted into an event, put in the request and executed by PracticeFrameworkSC<br>
 * - Set EventResponse to request to send execution result from PracticeFrameworkSC to View(JSP)<br>
 * @author JayTruong
 * @see EsmDou0108Event 참조
 * @since J2EE 1.6
 */
public class ESM_DOU_0108HTMLAction extends HTMLActionSupport {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Create ESM_DOU_0108HTMLAction object
	 */
	public ESM_DOU_0108HTMLAction() {}
	
	/**
	 * Parsing HTML DOM object's value into Java variable<br>
	 * Parsing the information of HttpRequest as EsmDou0108Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return event Event interface
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
//		Parsing HTML DOM object's value into Java variable
    	FormCommand command = FormCommand.fromRequest(request);
    	EsmDou0108Event event = new EsmDou0108Event();
    	// search sumary
		if(command.isCommand(FormCommand.SEARCH)) {
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));
		}
		// search rlane
		else if(command.isCommand(FormCommand.SEARCH01)){
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));;
		}
		// search trade code
		else if(command.isCommand(FormCommand.SEARCH02)){
			SummarySearchTradeVO trade = new SummarySearchTradeVO();
			trade.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			trade.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			event.setTradeVO(trade);
		}
		// search detail
		else if (command.isCommand(FormCommand.SEARCH03)){
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));
		}
		// directdown2excel
		else if (command.isCommand(FormCommand.COMMAND01)){
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));
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
