/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooArMgmtDBDAO.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15 JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CheckDuplicateVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.RlaneVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;



/**
 * ALPS JooArMgmtDBDAO <br>
 * - JDBC operation to process ALPS-DouTraining0004 system business logic.<br>
 * 
 * @author JayTruong
 * @see Reference JooArMgmtBCImpl
 * @since J2EE 1.6
 */
@SuppressWarnings("serial")
public class JooArMgmtDBDAO extends DBDAOSupport {

	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param JooArMnVO jooArMnVO
	 * @return List<JooArMnVO>
	 * @exception DAOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<JooArMnVO> searchJooArMnVO(JooArMnVO jooArMnVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<JooArMnVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(jooArMnVO != null){
				Map<String, String> mapVO = jooArMnVO .getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(null != jooArMnVO.getJoCrrCd()){
					String[] crr_cd = jooArMnVO.getJoCrrCd().split(",");
					for(int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooArMgmtDBDAOJooArMnVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooArMnVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	

	
	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param List<JooArMnVO> jooArMnVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] addmanageJooArMnVOS(List<JooArMnVO> jooArMnVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooArMnVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new JooArMgmtDBDAOJooArMnVOCSQL(), jooArMnVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param List<JooArMnVO> jooArMnVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageJooArMnVOS(List<JooArMnVO> jooArMnVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooArMnVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new JooArMgmtDBDAOJooArMnVOUSQL(), jooArMnVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param List<JooArMnVO> jooArMnVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageJooArMnVOS(List<JooArMnVO> jooArMnVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooArMnVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new JooArMgmtDBDAOJooArMnVODSQL(), jooArMnVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}

	/**
	 * 
	 * [Act] to [process] information.<br>
	 * 
	 * @return List<CarrierVO>
	 * @throws DAOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CarrierVO> searchCarrier() throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<CarrierVO> list = null;
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooArMgmtDBDAOCarrierRSQL(), null, null);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);	
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	/**
	 * 
	 * [Act] to [process] information.<br>
	 * 
	 * @return List<RlaneVO>
	 * @throws DAOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RlaneVO> searchRlane() throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<RlaneVO> list = null;
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooArMgmtDBDAORlaneRSQL(), null, null);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, RlaneVO .class);	
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}



	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param CheckDuplicateVO check
	 * @throws DAOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int checkDup(CheckDuplicateVO check)  throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<CheckDuplicateVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(check != null){
				Map<String, String> mapVO = check .getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooArMgmtDBDAOCheckDuplicateRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CheckDuplicateVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return Integer.parseInt(list.get(0).getCnt());
	}



	/**
	 * [Act] to [process] information.<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @throws DAOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CustomerVO> searchCustomer(CustomerVO customerVO) throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<CustomerVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(customerVO != null){
				Map<String, String> mapVO = customerVO .getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooArMgmtDBDAOCustomerVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CustomerVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
}