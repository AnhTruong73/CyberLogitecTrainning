package com.clt.apps.opus.esm.clv.practiceframework.integration;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practiceframework.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;



public class PracticeFrameworkDBDAO extends DBDAOSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SummaryVO> searchSummaryVO(ConditionVO summaryVO) throws EventException,DAOException {
		// TODO Auto-generated method stub
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO .getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if (null!=summaryVO.getJoCrrCd()){
					String[] partners = summaryVO.getJoCrrCd().split(",");
					for(int i = 0; i < partners.length; i++){
						obj_list_no.add(partners[i]);
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
				}
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new PracticeFrameworkDBDAOSummaryVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);	
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SummaryVO> searchPartner() throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		 
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new PracticeFrameworkDBDAOSummarySearchPartnerRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SummaryVO> searchLane(ConditionVO lane) throws DAOException{
		
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		 
		try{
			if(lane != null){
				Map<String, String> mapVO = lane.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(null != lane.getJoCrrCd()){
					String[] crr_cd = lane.getJoCrrCd().split(",");
					for(int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
					
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new PracticeFrameworkDBDAOSummarySearchRlaneRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	public Map<String,String> searchTrade(SummarySearchTradeVO trade) throws DAOException {
		 DBRowSet dbRowset = null;
		 Map<String, String> list = new HashMap<String, String>();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 if(trade != null){
				 Map<String, String> mapVO = trade.getColumnValues();
				 List<String> obj_list_no = new ArrayList<>();
					if(null != trade.getJoCrrCd()){
						String[] crr_cd = trade.getJoCrrCd().split(",");
						for(int i = 0; i < crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
			 }
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new PracticeFrameworkDBDAOSummarySearchTradeRSQL(), param, velParam);
			 while(dbRowset.next()){
				 String trade_cd = dbRowset.getString(1);
				 list.put(trade_cd, trade_cd);
			 }	
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DetailsVO> searchDetailsVO(ConditionVO detailsVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<DetailsVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(detailsVO != null){
				Map<String, String> mapVO = detailsVO .getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if (null!=detailsVO.getJoCrrCd()){
					String[] partners = detailsVO.getJoCrrCd().split(",");
					for(int i = 0; i < partners.length; i++){
						obj_list_no.add(partners[i]);
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
				}
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new FracticeFrameworkDBDAODetailsVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailsVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	public DBRowSet searchDetailsRSForExcel(ConditionVO conditionVO) throws DAOException {
		DBRowSet dbRowset = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try{
			if(conditionVO != null){
				Map<String, String> mapVO = conditionVO .getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if (null!=conditionVO.getJoCrrCd()){
					String[] partners = conditionVO.getJoCrrCd().split(",");
					for(int i = 0; i < partners.length; i++){
						obj_list_no.add(partners[i]);
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
				}
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new FracticeFrameworkDBDAODetailsVORSQL(), param, velParam);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return dbRowset;
	}


}
