/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkBCImpl.java
*@FileTitle : PracticeFrameworkBCImpl
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06 JayTruong
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.06  JayTruong
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.esm.clv.practiceframework.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practiceframework.integration.PracticeFrameworkDBDAO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
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


	@Override
	public List<Object> searchDetailsRSForExcel(ConditionVO conditionVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			DBRowSet rs = dbDao.searchDetailsRSForExcel(conditionVO);
			List<Object> li=new ArrayList<>();
			Map<Object, Object> mp=null;
			while (rs.next()){
				mp=new HashMap<>(); 
				mp.put("csr_no",rs.getString("CSR_NO"));
			    mp.put("inv_rev_act_amt",rs.getString("INV_REV_ACT_AMT")); 
			    mp.put("locl_curr_cd",rs.getString("LOCL_CURR_CD"));
			    mp.put("cust_vndr_seq",rs.getString("CUST_VNDR_SEQ")); 
			    mp.put("jo_crr_cd",rs.getString("JO_CRR_CD"));
			    mp.put("rlane_cd",rs.getString("RLANE_CD")); 
			    mp.put("rev_exp",rs.getString("REV_EXP"));
			    mp.put("cust_vndr_cnt_cd",rs.getString("CUST_VNDR_CNT_CD")); 
			    mp.put("inv_no",rs.getString("INV_NO"));
			    mp.put("cust_vndr_eng_nm",rs.getString("CUST_VNDR_ENG_NM"));
			    mp.put("inv_exp_act_amt",rs.getString("INV_EXP_ACT_AMT")); 
			    mp.put("item",rs.getString("ITEM"));
			    mp.put("prnr_ref_no",rs.getString("PRNR_REF_NO")); 
			    mp.put("apro_flg",rs.getString("APRO_FLG")); 
			    li.add(mp); 
			}
//			Search in DBDAO
			return li;
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	
}
