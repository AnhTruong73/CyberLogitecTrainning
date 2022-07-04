/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkBC.java
*@FileTitle : PracticeFramework
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06 
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.06  JayTruong
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.esm.clv.practiceframework.basic;

import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-PracticeFramework Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-PracticeFramework<br>
 *
 * @author JayTruong
 * @since J2EE 1.6
 */
public interface PracticeFrameworkBC{
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param ConditionVO summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(ConditionVO summaryVO) throws EventException;

	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> searchPartner() throws EventException;

	/**
	 * [Act] for [Business Target]
	 * 
	 * @param ConditionVO lane
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> searchLane(ConditionVO lane) throws EventException;

	/**
	 * [Act] for [Business Target]
	 * 
	 * @param SummarySearchTradeVO trade
	 * @return  Map<String, String>
	 * @throws EventException
	 */
	public Map<String, String> searchTrade(SummarySearchTradeVO trade) throws EventException;

	/**
	 * [Act] for [Business Target]
	 * 
	 * @param ConditionVO detailsVO
	 * @return  Map<String, String>
	 * @throws EventException
	 */
	public List<DetailsVO> searchDetailsVO(ConditionVO detailsVO) throws EventException;

	/**
	 * [Act] for [Business Target]
	 * 
	 * @param ConditionVO detailsVO
	 * @return List<DetailsVO>
	 * @throws EventException
	 */
	public List searchDetailsRSForExcel(ConditionVO conditionVO) throws EventException;
}
