/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PracticeFrameworkSC.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : JayTruong
*@LastVersion : 1.0
* 2022.06.02 JayTruong
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.esm.clv.practiceframework;

import java.util.List;


import java.util.Map;


import com.clt.apps.opus.esm.clv.practiceframework.basic.PracticeFrameworkBC;
import com.clt.apps.opus.esm.clv.practiceframework.basic.PracticeFrameworkBCImpl;
import com.clt.apps.opus.esm.clv.practiceframework.event.EsmDou0108Event;
import com.clt.apps.opus.esm.clv.practiceframework.vo.DetailsVO;
import com.clt.apps.opus.esm.clv.practiceframework.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class PracticeFrameworkSC extends ServiceCommandSupport{

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
	 * Do perform
	 * Doing related internal objects when calling a business scenario
	 * 
	 * (non-Javadoc)
	 * @see com.clt.framework.core.layer.service.Command#perform(com.clt.framework.core.layer.event.Event)
	 */
	@Override
	public EventResponse perform(Event e) throws EventException {
		// TODO Auto-generated method stub
		EventResponse eventResponse = null;
		
		if (e.getEventName().equalsIgnoreCase("EsmDou0108Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				//search Sumary
				eventResponse = searchSummary(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)){
				//init data for combobox Partner
				eventResponse = initData(); 
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)){
				//search data for combobox rlane
				eventResponse = searchLane(e); 
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)){
				//search data for combobox trade code
				eventResponse = searchTrade(e); 
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)){
				//search details
				eventResponse = searchDetails(e); 
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)){
				// direct down2excel
				eventResponse = searchDetailsRSForExcel(e); 
			}
			
		}
		return eventResponse;
	}
	
	
	/**
	 * This function is used for searching data for DownExcel2
	 * 
	 * @param Event e
	 * @return eventResponse
	 * @throws EventException
	 */
	private EventResponse searchDetailsRSForExcel(Event e) throws EventException {
		// TODO Auto-generated method stub
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		try{
			eventResponse.setRsVoList(command.searchDetailsRSForExcel(event.getConditionVO()));
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}


	/**
	 * ESM_DOU_0108 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 * This method is used for searching Details data  
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchDetails(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		
		try{
//			Executing in BCImpl
			List<DetailsVO> list = command.searchDetailsVO(event.getConditionVO());
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
	 * This function is used for initializing data for Lane combo box
	 * @return eventResponse
	 * @throws EventException
	 */
	private EventResponse searchLane(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		
		try{
			List<SummaryVO> list = command.searchLane(event.getConditionVO());
			StringBuilder rlaneBuilder = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					rlaneBuilder.append(list.get(i).getRlaneCd());
					if (i < list.size() - 1){
						rlaneBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("rlane_cd", rlaneBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}


	/**
	 * This function is used for initializing data for Partner combo box
	 * @return eventResponse
	 * @throws EventException
	 */
	private EventResponse initData() throws EventException{
		// TODO Auto-generated method stub
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		try{
			List<SummaryVO> list = command.searchPartner();
			StringBuilder partnerBuilder = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					partnerBuilder.append(list.get(i).getJoCrrCd());
					if (i < list.size() - 1){
						partnerBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("jo_crr_cd", partnerBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	
	
	/**
	 * This function is used for initializing data for Trade combo box
	 * @return eventResponse
	 * @throws EventException
	 */
	private EventResponse searchTrade(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		try{		
			Map<String,String> list = command.searchTrade(event.getTradeVO());
			StringBuilder tradeBuilder = new StringBuilder();
			String tmp = "";
			for (Map.Entry<String, String> entry : list.entrySet()){
				tradeBuilder.append("|" + entry.getValue());
			}
			if (tradeBuilder.toString().length() > 0){
				tmp = tradeBuilder.toString().substring(1);
			}
			eventResponse.setETCData("trd_cd",tmp);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}


	/**
	 * ESM_DOU_0108 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 * This method is used for searching Summary data  
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchSummary(Event e) throws EventException  {
		// TODO Auto-generated method stub
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		PracticeFrameworkBC command = new PracticeFrameworkBCImpl();
		
		try{
//			Executing in BCImpl
			List<SummaryVO> list = command.searchSummaryVO(event.getConditionVO());
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
	 * Do after perform<br>
	 * Release related internal objects at the end of the business scenario<br>
	 */
	public void doEnd() {
		log.debug("PracticeTrainingSC 종료");
	}


}
