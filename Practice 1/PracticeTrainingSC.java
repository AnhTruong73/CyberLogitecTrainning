/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeTrainingSC.java
*@FileTitle : Pactice 1
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practicetraining;

import java.util.List;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.basic.ErrMsgMgmtBC;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.event.DouPrac0001Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practicetraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS-PracticeTraining Business Logic ServiceCommand - Process business transaction for ALPS-PracticeTraining.
 * 
 * @author anhtruong
 * @see ErrMsgMgmtDBDAO
 * @since J2EE 1.6
 */

public class PracticeTrainingSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * Do before perform
	 * Creating related internal objects when calling a business scenario
	 */
	public void doStart() {
		log.debug("PracticeTrainingSC 시작");
		try {
			// First comment --> Check Authenticated
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * Do after perform<br>
	 * Release related internal objects at the end of the business scenario<br>
	 */
	public void doEnd() {
		log.debug("PracticeTrainingSC 종료");
	}

	/**
	 * Carry out business scenarios for each event<br>
	 * Brand processing of all events occurring in ALPS-PracticeTraining system work <br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// Check event Multi or Search
		if (e.getEventName().equalsIgnoreCase("DouPrac0001Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchErrMsg(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageErrMsg(e);
			}
		}
		return eventResponse;
	}
	/**
	 * Executing Search request
	 * DOU_PRAC_0001 : [Event]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
//		Get event
		DouPrac0001Event event = (DouPrac0001Event)e;
//		Working with BCImlp
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();

		try{
//			Executing in BCImpl
			List<ErrMsgVO> list = command.searchErrMsg(event.getErrMsgVO());
//			Add List Respone in eventResponse
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * 
	 * DOU_PRAC_0001 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
//	Executing Multi request
	private EventResponse manageErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
//		Get event
		DouPrac0001Event event = (DouPrac0001Event)e;
//		Working with BCImlp
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try{
//			Begin transaction
			begin();
//			Executing in BCImpl
			command.manageErrMsg(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
//			Using apply all change 
			commit();
		} catch(EventException ex) {
//			Restore all change in transaction executed in database
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
}