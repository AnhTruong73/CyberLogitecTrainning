/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0108Event.java
*@FileTitle : EsmDou0108Event
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.06  JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practiceframework.event;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;


/**
 * PDTO(Data Transfer Object including Parameters) for ESM_DOU_0108 <br>
 * -  Create from ESM_DOU_0108HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author anhtruong
 * @see ESM_DOU_0108HTMLAction
 * @since J2EE 1.6
 */
public class EsmDou0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object search condition and single event processing  */
	SummaryVO summaryVO = null;
	SummarySearchTradeVO tradeVO= null;
	ConditionVO conditionVO = null;
	DetailsVO detailsVO = null;
	
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

	public EsmDou0108Event(){}
	
	public SummaryVO getSummaryVO() {
		return summaryVO;
	}
	public void setSummaryVO(SummaryVO summaryVO) {
		this.summaryVO = summaryVO;
	}


	
	
}
