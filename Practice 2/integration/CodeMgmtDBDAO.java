/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtDBDAO.java
*@FileTitle : Pactice 2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.dou.doutraining.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS CodeMgmtDBDAO <br>
 * - JDBC operation to process ALPS-DouTraining system business logic.<br>
 * 
 * @author anhtruong
 * @see CodeMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class CodeMgmtDBDAO extends DBDAOSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * [searchMaster] to get a list of Code.<br>
	 * 
	 * @param MasterVO masterVO
	 * @return List<MasterVO>
	 * @exception DAOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MasterVO> searchMaster(MasterVO masterVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<MasterVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(masterVO != null){
				Map<String, String> mapVO = masterVO .getColumnValues();			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOMasterRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, MasterVO .class);
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
	 * [searchDetail] to get a list of Code.<br>
	 * 
	 * @param DetailVO detailVO
	 * @return List<DetailVO>
	 * @exception DAOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DetailVO> searchDetail(DetailVO detailVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<DetailVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			if(detailVO != null){
				Map<String, String> mapVO = detailVO .getColumnValues();			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAODetailRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailVO .class);
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
	 * [addmanageMasterS] to add Codes.<br>
	 * 
	 * @param List<MasterVO> masterVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addmanageMasterS(List<MasterVO> masterVO) throws DAOException,Exception{
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(masterVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOMasterCSQL(), masterVO,null);
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
	 * [modifymanageMasterS] to update Codes.<br>
	 * 
	 * @param List<MasterVO> masterVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageMasterS(List<MasterVO> masterVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(masterVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOMasterUSQL(), masterVO,null);
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
	 * [removemanageMasterS] to delete Codes.<br>
	 * 
	 * @param List<MasterVO> masterVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageMasterS(List<MasterVO> masterVO) throws DAOException,Exception {
		// TODO Auto-generated method stub
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(masterVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOMasterDSQL(), masterVO,null);
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
	 * [addmanageDetailS] to add CodeDetails.<br>
	 * 
	 * @param List<DetailVO> detailVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addmanageDetailS(List<DetailVO> detailVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAODetailCSQL(), detailVO,null);
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
	 * [modifymanageDetailS] to update CodeDetails.<br>
	 * 
	 * @param List<DetailVO> detailVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageDetailS(List<DetailVO> detailVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAODetailUSQL(), detailVO,null);
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
	 * [removemanageDetailS] to delete CodeDetails.<br>
	 * 
	 * @param List<DetailVO> detailVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageDetailS(List<DetailVO> detailVO) throws DAOException,Exception {
		// TODO Auto-generated method stub
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAODetailDSQL(), detailVO,null);
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

	

}
