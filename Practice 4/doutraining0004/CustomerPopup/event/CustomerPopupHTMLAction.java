package com.clt.apps.opus.esm.clv.doutraining0004.CustomerPopup.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class CustomerPopupHTMLAction extends HTMLActionSupport {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		FormCommand command = FormCommand.fromRequest(request);
		CustomerPopupEvent event = new CustomerPopupEvent();
		if(command.isCommand(FormCommand.SEARCH)) {
			CustomerVO customerVO = new CustomerVO();
			
			customerVO.setCustCntCd(JSPUtil.getParameter(request, "s_cust_cnt_cd", ""));
			customerVO.setCustSeq(JSPUtil.getParameter(request, "s_cust_seq", ""));
			
			event.setCustomerVO(customerVO);
		}
		return event;
	}

}
