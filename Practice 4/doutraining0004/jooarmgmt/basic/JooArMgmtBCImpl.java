/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooArMgmtBCImpl.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15  JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.integration.JooArMgmtDBDAO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CheckDuplicateVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.RlaneVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-DouTraining0004 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-DouTraining0004<br>
 *
 * @author JayTruong
 * @since J2EE 1.6
 */
/**
 * @author JayTruong
 *
 */
/**
 * @author JayTruong
 *
 */
/**
 * @author JayTruong
 *
 */
public class JooArMgmtBCImpl extends BasicCommandSupport implements JooArMgmtBC {

	// Database Access Object
	private transient JooArMgmtDBDAO dbDao = null;

	/**
	 * Creation object JooArMgmtBCImpl<br>
	 * To create JooArMgmtDBDAO.<br>
	 */
	public JooArMgmtBCImpl() {
		dbDao = new JooArMgmtDBDAO();
	}
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param JooArMnVO jooArMnVO
	 * @return List<JooArMnVO>
	 * @exception EventException
	 */
	public List<JooArMnVO> searchJooArMnVO(JooArMnVO jooArMnVO) throws EventException {
		try {
			return dbDao.searchJooArMnVO(jooArMnVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param JooArMnVO[] jooArMnVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageJooArMnVO(JooArMnVO[] jooArMnVO, SignOnUserAccount account) throws EventException{
		try {
			List<JooArMnVO> insertVoList = new ArrayList<JooArMnVO>();
			List<JooArMnVO> updateVoList = new ArrayList<JooArMnVO>();
			List<JooArMnVO> deleteVoList = new ArrayList<JooArMnVO>();
			for ( int i=0; i<jooArMnVO .length; i++ ) {
				if ( jooArMnVO[i].getIbflag().equals("I")){
					CheckDuplicateVO check = new CheckDuplicateVO();
					check.setJoCrrCd(jooArMnVO[i].getJoCrrCd());
					check.setRlaneCd(jooArMnVO[i].getRlaneCd());
					if(checkDup(check)>0){
						throw new DAOException(new ErrorHandler("ERR00002",new String[] {jooArMnVO[i].getJoCrrCd()+","+jooArMnVO[i].getRlaneCd()}).getMessage());
					}
					jooArMnVO[i].setCreUsrId(account.getUsr_id());
					jooArMnVO[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(jooArMnVO[i]);
				} else if ( jooArMnVO[i].getIbflag().equals("U")){
					jooArMnVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(jooArMnVO[i]);
				} else if ( jooArMnVO[i].getIbflag().equals("D")){
					deleteVoList.add(jooArMnVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageJooArMnVOS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageJooArMnVOS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageJooArMnVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	
	
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrier() throws EventException {
		try {	
			return dbDao.searchCarrier();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @return List<RlaneVO>
	 * @exception EventException
	 */
	@Override
	public List<RlaneVO> searchRlane() throws EventException {
		try {
			return dbDao.searchRlane();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [Act] for [Business Target].<br>
	 * check Duplicate
	 * 
	 * @param CheckDuplicateVO check
	 * @exception EventException
	 */
	@Override
	public int checkDup(CheckDuplicateVO check) throws EventException {
		try {	
			return dbDao.checkDup(check);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @exception EventException
	 */
	@Override
	public List<CustomerVO> searchCustomer(CustomerVO customerVO)
			throws EventException {
		try {	
			return dbDao.searchCustomer(customerVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}