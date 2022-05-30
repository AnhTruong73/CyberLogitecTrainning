/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtBCImpl.java
*@FileTitle : Pactice 2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class CodeMgmtBCImpl extends BasicCommandSupport implements CodeMgmtBC {

	private transient CodeMgmtDBDAO dbDao = null;
	public CodeMgmtBCImpl(){
		dbDao= new CodeMgmtDBDAO();
	}
	
	@Override
	public List<MasterVO> searchMasterVO(MasterVO masterVO) throws EventException{
		try {
//			Search in DBDAO
			return dbDao.searchMaster(masterVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<DetailVO> searchDetailVO(DetailVO detailVO) throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchDetail(detailVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public void manageMaster(MasterVO[] masterVO, SignOnUserAccount account) 
			throws EventException {
		try {
//			Create List for I: insert | U: update | D: delete  
			List<MasterVO> insertVoList = new ArrayList<MasterVO>();
			List<MasterVO> updateVoList = new ArrayList<MasterVO>();
			List<MasterVO> deleteVoList = new ArrayList<MasterVO>();
			for ( int i=0; i<masterVO .length; i++ ) {
//				Check action I|U|D
				if ( masterVO[i].getIbflag().equals("I")){
					masterVO[i].setCreUsrId(account.getUsr_id());
					masterVO[i].setUpdUsrId(account.getUsr_id());
//					Check duplicate
					MasterVO checkdup = new MasterVO();
					checkdup.setIntgCdId(masterVO[i].getIntgCdId());
					if ( searchMasterVO(checkdup).size() >= 1){
						throw new DAOException(new ErrorHandler("ERR00001",new String[] {masterVO[i].getIntgCdId()}).getMessage());
					}
					else {
//						Add VO object in List Insert
						masterVO[i].setCreUsrId(account.getUsr_id());
						masterVO[i].setUpdUsrId(account.getUsr_id());
						insertVoList.add(masterVO[i]);
					}
				} else if ( masterVO[i].getIbflag().equals("U")){
					masterVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(masterVO[i]);
				} else if ( masterVO[i].getIbflag().equals("D")){
					DetailVO checkExist = new DetailVO();
					checkExist.setIntgCdId(masterVO[i].getIntgCdId());
					if ( searchDetailVO(checkExist).size() >= 1){
						throw new EventException(new ErrorHandler("ERR0005").getMessage());
					}
					else {
						deleteVoList.add(masterVO[i]);
					}
					
				}
			}
//			Transform to DBDAO to execute in SQL if list > 0 
			if ( insertVoList.size() > 0 ) {

				dbDao.addmanageMasterS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageMasterS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageMasterS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public void manageDetail(DetailVO[] detailVO, SignOnUserAccount account)
			throws EventException {
		try {
//			Create List for I: insert | U: update | D: delete  
			List<DetailVO> insertVoList = new ArrayList<DetailVO>();
			List<DetailVO> updateVoList = new ArrayList<DetailVO>();
			List<DetailVO> deleteVoList = new ArrayList<DetailVO>();
			for ( int i=0; i<detailVO .length; i++ ) {
//				Check action I|U|D
				if ( detailVO[i].getIbflag().equals("I")){

//					Check duplicate
					DetailVO checkdup = new DetailVO();
					checkdup.setIntgCdValCtnt(detailVO[i].getIntgCdValCtnt());
					if ( searchDetailVO(checkdup).size() >= 1){
						throw new EventException(new ErrorHandler("ERR00006", new String[] {detailVO[i].getIntgCdValCtnt()}).getMessage());
					}
					else {
//						Add VO object in List Insert
						detailVO[i].setCreUsrId(account.getUsr_id());
						detailVO[i].setUpdUsrId(account.getUsr_id());
						insertVoList.add(detailVO[i]);
					}
				} else if ( detailVO[i].getIbflag().equals("U")){
					detailVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(detailVO[i]);
				} else if ( detailVO[i].getIbflag().equals("D")){
					deleteVoList.add(detailVO[i]);
				}
			}
//			Transform to DBDAO to execute in SQL if list > 0 
			if ( insertVoList.size() > 0 ) {

				dbDao.addmanageDetailS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageDetailS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageDetailS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}

}
