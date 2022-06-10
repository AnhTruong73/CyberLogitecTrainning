package com.clt.apps.opus.esm.clv.practiceframework.event;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;

public class Practice0003Event extends EventSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	SummaryVO summaryVO = null;
	SummarySearchTradeVO tradeVO= null;
	ConditionVO conditionVO = null;
	DetailsVO detailsVO = null;
	
	
	
	
	SummaryVO[] summaryVOs = null;
	SummarySearchTradeVO[] tradeVOs = null;
	ConditionVO[] conditionVOs = null;
	DetailsVO[] detailsVOs = null;
	
	
	public ConditionVO getConditionVO() {
		return conditionVO;
	}

	public void setConditionVO(ConditionVO conditionVO) {
		this.conditionVO = conditionVO;
	}

	public SummarySearchTradeVO getTradeVO() {
		return tradeVO;
	}

	public void setTradeVO(SummarySearchTradeVO tradeVO) {
		this.tradeVO = tradeVO;
	}

	public Practice0003Event(){}
	
	public SummaryVO getSummaryVO() {
		return summaryVO;
	}
	public void setSummaryVO(SummaryVO summaryVO) {
		this.summaryVO = summaryVO;
	}

	public SummaryVO[] getSummaryVOs() {
		return summaryVOs;
	}

	public void setSummaryVOs(SummaryVO[] summaryVOs) {
		this.summaryVOs = summaryVOs;
	}

	public SummarySearchTradeVO[] getTradeVOs() {
		return tradeVOs;
	}

	public void setTradeVOs(SummarySearchTradeVO[] tradeVOs) {
		this.tradeVOs = tradeVOs;
	}

	public ConditionVO[] getConditionVOs() {
		return conditionVOs;
	}

	public void setConditionVOs(ConditionVO[] conditionVOs) {
		this.conditionVOs = conditionVOs;
	}

	public DetailsVO getDetailsVO() {
		return detailsVO;
	}

	public void setDetailsVO(DetailsVO detailsVO) {
		this.detailsVO = detailsVO;
	}

	public DetailsVO[] getDetailsVOs() {
		return detailsVOs;
	}

	public void setDetailsVOs(DetailsVO[] detailsVOs) {
		this.detailsVOs = detailsVOs;
	}
	
	
}
