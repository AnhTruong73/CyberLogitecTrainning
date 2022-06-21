package com.clt.apps.opus.esm.clv.doutraining0004.CustomerPopup.event;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.framework.support.layer.event.EventSupport;

public class CustomerPopupEvent extends EventSupport {
	private CustomerVO customerVO;
	
	public CustomerPopupEvent(){}

	public CustomerVO getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}
	
}
