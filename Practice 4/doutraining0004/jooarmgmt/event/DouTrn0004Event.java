/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrn0004Event.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15JayTruong
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.event;

import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CheckDuplicateVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.framework.support.layer.event.EventSupport;



/**
 * - PDTO(Data Transfer Object including Parameters) for DOU_TRN_0004<br>
 * - Writen in DOU_TRN_0004HTMLAction <br>
 * - Used as PDTO to pass to ServiceCommand Layer<br>
 *
 * @author JayTruong
 * @see Reference atDOU_TRN_0004HTMLAction 
 * @since J2EE 1.6
 */


public class DouTrn0004Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object for Search conditions and single case processing  */
	JooArMnVO jooArMnVO = null;
	CheckDuplicateVO[] checkDuplicateVOs = null;
	
	/** Table Value Object Multi Data process */
	JooArMnVO[] jooArMnVOs = null;

	
	public DouTrn0004Event(){}
	
	public void setJooArMnVO(JooArMnVO jooArMnVO){
		this. jooArMnVO = jooArMnVO;
	}

	public void setJooArMnVOS(JooArMnVO[] jooArMnVOs){
		this. jooArMnVOs = jooArMnVOs;
	}

	public JooArMnVO getJooArMnVO(){
		return jooArMnVO;
	}

	public JooArMnVO[] getJooArMnVOS(){
		return jooArMnVOs;
	}

	public CheckDuplicateVO[] getCheckDuplicateVOs() {
		return checkDuplicateVOs;
	}

	public void setCheckDuplicateVOs(CheckDuplicateVO[] checkDuplicateVOs) {
		this.checkDuplicateVOs = checkDuplicateVOs;
	}
	
	

}