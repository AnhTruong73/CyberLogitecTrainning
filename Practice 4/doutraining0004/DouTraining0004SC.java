/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTraining0004SC.java
*@FileTitle : DOU_TRN_0004
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.15 JayTruong
*@LastModifier :  JayTruong
*@LastVersion : 1.0
* 2022.06.15 JayTruong
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining0004;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining0004.CustomerPopup.event.CustomerPopupEvent;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.basic.JooArMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.basic.JooArMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.event.DouTrn0004Event;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.JooArMnVO;
import com.clt.apps.opus.esm.clv.doutraining0004.jooarmgmt.vo.RlaneVO;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


/**
 * ALPS-DouTraining0004 Business Logic ServiceCommand - Process business transaction for ALPS-DouTraining0004.
 * 
 * @author JayTruong
 * @see JooArMgmtDBDAO
 * @since J2EE 1.6
 */

public class DouTraining0004SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * DouTraining0004 system work scenario precedent work<br>
	 * Creating related internal objects when calling a business scenario<br>
	 */
	public void doStart() {
		log.debug("Start DouTraining0004SC");
		try {
			// First comment --> Login check part
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * DouTraining0004 system work scenario finishing work<br>
	 * Release related internal objects at the end of the business scenario<br>
	 */
	public void doEnd() {
		log.debug("End DouTraining0004SC");
	}

	/**
	 * Carry out business scenarios corresponding to each event<br>
	 * Branch processing of all events occurring in ALPS-DouTraining0004 system work<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// The part to use when SC handles multiple events
		if (e.getEventName().equalsIgnoreCase("DouTrn0004Event")) {
			//for search
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchJooArMnVO(e);
			}
			//for init combobox
			else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initCombobox(e);
			}
			//for manage data
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageJooArMnVO(e);
			}
		}
		// for search Popup
		else if (e.getEventName().equalsIgnoreCase("CustomerPopupEvent")){
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCustomer(e);
			}
		}
		return eventResponse;
	}


	/**
	 * Using search data to for Popup screen<br>
	 * 
	 * @param Event e
	 * @return EventResponse eventResponse 
	 * @throws EventException
	 */
	private EventResponse searchCustomer(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CustomerPopupEvent event = (CustomerPopupEvent)e;
		JooArMgmtBC command = new JooArMgmtBCImpl();
		try{
			List<CustomerVO> list = command.searchCustomer(event.getCustomerVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}

	/**
	 * Using init data for combobox Carrier & Rlane<br>
	 * 
	 * @param Event e
	 * @return EventResponse eventResponse
	 * @throws EventException
	 */
	private EventResponse initCombobox(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		JooArMgmtBC command = new JooArMgmtBCImpl();
		try{
			List<CarrierVO> list = command.searchCarrier();
			StringBuilder carrierBuilder = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					carrierBuilder.append(list.get(i).getJoCrrCd());
					if (i < list.size() - 1){
						carrierBuilder.append("|");
					}	
				}
			}
			List<RlaneVO> list1 = command.searchRlane();
			StringBuilder rlaneBuilder = new StringBuilder();
			if(null != list1 && list1.size() > 0){
				for(int i =0; i < list1.size(); i++){
					rlaneBuilder.append(list1.get(i).getRlaneCd());
					if (i < list1.size() - 1){
						rlaneBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("carrier", carrierBuilder.toString());
			eventResponse.setETCData("rlane", rlaneBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}

	/**
	 * Using search data for sheetObj<br>
	 * 
	 * @param Event e
	 * @return EventResponse eventResponse
	 * @exception EventException
	 */
	private EventResponse searchJooArMnVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		JooArMgmtBC command = new JooArMgmtBCImpl();
		try{
			List<JooArMnVO> list = command.searchJooArMnVO(event.getJooArMnVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * Using manage data like CRUD
	 *
	 * @param Event e
	 * @return EventResponse eventResponse
	 * @exception EventException
	 */
	private EventResponse manageJooArMnVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		JooArMgmtBC command = new JooArMgmtBCImpl();
		
		try{
			begin();
			command.manageJooArMnVO(event.getJooArMnVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
}