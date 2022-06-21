/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooArMgmtBC.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15 JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CheckDuplicateVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.RlaneVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-Doutraining0004 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Doutraining0004<br>
 *
 * @author JayTruong
 * @since J2EE 1.6
 */

public interface JooArMgmtBC {

	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param JooArMnVO	jooArMnVO
	 * @return List<JooArMnVO>
	 * @exception EventException
	 */
	public List<JooArMnVO> searchJooArMnVO(JooArMnVO jooArMnVO) throws EventException;
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param JooArMnVO[] jooArMnVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageJooArMnVO(JooArMnVO[] jooArMnVO,SignOnUserAccount account) throws EventException;
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @return List<CarrierVO>
	 * @throws EventException
	 */
	public List<CarrierVO> searchCarrier() throws EventException;

	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @return List<RlaneVO>
	 * @throws EventException
	 */
	public List<RlaneVO> searchRlane() throws EventException;
	
	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param check
	 * @throws EventException
	 */
	public int checkDup(CheckDuplicateVO check) throws EventException;

	/**
	 * [Act] for [Business Target].<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @throws EventException
	 */
	public List<CustomerVO> searchCustomer(CustomerVO customerVO) throws EventException;
}