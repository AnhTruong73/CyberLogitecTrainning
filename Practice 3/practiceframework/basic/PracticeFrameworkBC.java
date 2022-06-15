package com.clt.apps.opus.esm.clv.practiceframework.basic;

import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;


public interface PracticeFrameworkBC{
	public List<SummaryVO> searchSummaryVO(ConditionVO summaryVO) throws EventException;

	public List<SummaryVO> searchPartner() throws EventException;

	public List<SummaryVO> searchLane(ConditionVO lane) throws EventException;

	public Map<String, String> searchTrade(SummarySearchTradeVO trade) throws EventException;

	public List<DetailsVO> searchDetailsVO(ConditionVO detailsVO) throws EventException;
	public List<Object> searchDetailsListForExcel(ConditionVO detailsVO) throws EventException;
}
