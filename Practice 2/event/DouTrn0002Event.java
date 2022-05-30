/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrn0002Event.java
*@FileTitle : Pactice 2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.framework.support.layer.event.EventSupport;


/**
 * PDTO (Data Transfer Object including Parameters) for DOU_TRN_0002<br>
 * - Created from DOU_TRN_0002HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author anhtruong
 * @see DOU_TRN_0002HTMLAction 참조
 * @since J2EE 1.6
 */
public class DouTrn0002Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Table Value Object query conditions and single case processing 
	 */
	MasterVO masterVO = null;
	DetailVO detailVO = null;
	
	/** 
	 * Table Value Object Multi Data Processing
	 */
	MasterVO[] masterVOs = null;
	DetailVO[] detailVOs = null;
	
	
	public DouTrn0002Event() {}


	public MasterVO getMasterVO() {
		return masterVO;
	}


	public void setMasterVO(MasterVO masterVO) {
		this.masterVO = masterVO;
	}


	public DetailVO getDetailVO() {
		return detailVO;
	}


	public void setDetailVO(DetailVO detailVO) {
		this.detailVO = detailVO;
	}


	public MasterVO[] getMasterVOs() {
		return masterVOs;
	}


	public void setMasterVOs(MasterVO[] masterVOs) {
		this.masterVOs = masterVOs;
	}


	public DetailVO[] getDetailVOs() {
		return detailVOs;
	}


	public void setDetailVOs(DetailVO[] detailVOs) {
		this.detailVOs = detailVOs;
	}
	
	

}
