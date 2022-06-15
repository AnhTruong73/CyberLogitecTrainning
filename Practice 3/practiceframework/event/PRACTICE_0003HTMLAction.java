package com.clt.apps.opus.esm.clv.practiceframework.event;


import javax.servlet.http.HttpServletRequest;
import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class PRACTICE_0003HTMLAction extends HTMLActionSupport {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Creat DOU_PRAC_0001HTMLAction object
	 */
	public PRACTICE_0003HTMLAction() {}
	
	
	public Event perform(HttpServletRequest request) throws HTMLActionException {
//		Parsing HTML DOM object's value into Java variable
    	FormCommand command = FormCommand.fromRequest(request);
    	Practice0003Event event = new Practice0003Event();
		if(command.isCommand(FormCommand.SEARCH)) {
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH01)){
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));;
		}
		else if(command.isCommand(FormCommand.SEARCH02)){
			SummarySearchTradeVO trade = new SummarySearchTradeVO();
			trade.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			trade.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			event.setTradeVO(trade);
		}
		else if (command.isCommand(FormCommand.SEARCH03)){
			event.setConditionVO((ConditionVO)getVO(request, ConditionVO .class,""));
		}
		else if (command.isCommand(FormCommand.SEARCH04)){
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
