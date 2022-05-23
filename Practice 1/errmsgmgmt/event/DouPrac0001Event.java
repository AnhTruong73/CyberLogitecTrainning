/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouPrac0001Event.java
*@FileTitle : Pactice 1
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * PDTO(Data Transfer Object including Parameters) for DOU_PRAC_0001 <br>
 * -  Create from DOU_PRAC_0001HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author anhtruong
 * @see See DOU_PRAC_0001HTMLAction
 * @since J2EE 1.6
 */

public class DouPrac0001Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object search condition and single event processing  */
	ErrMsgVO errMsgVO = null;
	
	/** Table Value Object Multi Data processing */
	ErrMsgVO[] errMsgVOs = null;

	public DouPrac0001Event(){}
	
	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this. errMsgVOs = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}

}