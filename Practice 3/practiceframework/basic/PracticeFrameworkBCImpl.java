package com.clt.apps.opus.esm.clv.practiceframework.basic;

import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practiceframework.integration.PracticeFrameworkDBDAO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;

public class PracticeFrameworkBCImpl  extends BasicCommandSupport implements PracticeFrameworkBC {

	private transient PracticeFrameworkDBDAO dbDao = null;
	
	public PracticeFrameworkBCImpl() {
		dbDao = new PracticeFrameworkDBDAO();
	}
	
	@Override
	public List<SummaryVO> searchSummaryVO(ConditionVO summaryVO)
			throws EventException {
		
		// TODO Auto-generated method stub
		try {
//			Search in DBDAO
			return dbDao.searchSummaryVO(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<SummaryVO> searchPartner() throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchPartner();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<SummaryVO> searchLane(ConditionVO lane) throws EventException {
		try {
			return dbDao.searchLane(lane);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}	
	
	@Override
	public Map<String, String> searchTrade(SummarySearchTradeVO trade) throws EventException {
		try {
			return dbDao.searchTrade(trade);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<DetailsVO> searchDetailsVO(ConditionVO detailsVO)
			throws EventException {
		try {
//			Search in DBDAO
			return dbDao.searchDetailsVO(detailsVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	
}
