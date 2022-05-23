/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtBCImpl.java
*@FileTitle : Pactice 1
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.integration.ErrMsgMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-PracticeTraining Business Logic Command Interface<br>
 * - ALPS-PracticeTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author anhtruong
 * @since J2EE 1.6
 */
public class ErrMsgMgmtBCImpl extends BasicCommandSupport implements ErrMsgMgmtBC {

	// Database Access Object
	private transient ErrMsgMgmtDBDAO dbDao = null;

	/**
	 * Create ErrMsgMgmtBCImpl object<br>
	 * Create ErrMsgMgmtDBDAO.<br>
	 */
	public ErrMsgMgmtBCImpl() {
		dbDao = new ErrMsgMgmtDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
//	Search
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
//			Search in DBDAO
			return dbDao.searchErrMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
//	Multi
	public void manageErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
//			Create List for I: insert | U: update | D: delete  
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			for ( int i=0; i<errMsgVO .length; i++ ) {
//				Check action I|U|D
				if ( errMsgVO[i].getIbflag().equals("I")){
					errMsgVO[i].setCreUsrId(account.getUsr_id());
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
//					Check duplicate
					ErrMsgVO checkdup = new ErrMsgVO();
					checkdup.setErrMsgCd(errMsgVO[i].getErrMsgCd());
					if ( searchErrMsg(checkdup).size() >= 1){
						throw new EventException(new ErrorHandler("ERR00001").getMessage());
					}
					else {
//						Add VO object in List Insert
						insertVoList.add(errMsgVO[i]);
					}
				} else if ( errMsgVO[i].getIbflag().equals("U")){
//					Add VO object in List Update
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
//					Add VO object in List Delete
					deleteVoList.add(errMsgVO[i]);
				}
			}
//			Transform to DBDAO to execute in SQL if list > 0 
			if ( insertVoList.size() > 0 ) {

				dbDao.addmanageErrMsgS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageErrMsgS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageErrMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}