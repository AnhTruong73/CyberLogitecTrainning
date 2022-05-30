/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtBC.java
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

import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-Doutraining Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Doutraining<br>
 *
 * @author anhtruong
 * @since J2EE 1.6
 */
public interface CodeMgmtBC {
	
	/**
	 * [searchMasterVO] to get a list of code.<br>
	 * 
	 * @param MasterVO	masterVO
	 * @return List<MasterVO>
	 * @exception EventException
	 */
	public List<MasterVO> searchMasterVO(MasterVO masterVO) throws EventException;
	
	/**
	 * [searchDetailVO] to get a list of code.<br>
	 * 
	 * @param DetailVO detailVO
	 * @return List<DetailVO>
	 * @exception EventException
	 */
	public List<DetailVO> searchDetailVO(DetailVO detailVO) throws EventException;
	
	/**
	 * [manageMaster] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param MasterVO[] masterVO
	 * @param SignOnUserAccount account
	 * @exception EventException
	 */
	public void manageMaster(MasterVO[] masterVO,SignOnUserAccount account) throws EventException;
	
	
	/**
	 * [manageDetail] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param DetailVO[] detailVO
	 * @param SignOnUserAccount account
	 * @exception EventException
	 */
	public void manageDetail(DetailVO[] detailVO,SignOnUserAccount account) throws EventException;
}
