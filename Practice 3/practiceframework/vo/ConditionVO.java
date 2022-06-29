/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ConditionVO.java
*@FileTitle : ConditionVO
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.06 JayTruong
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.06  JayTruong
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.esm.clv.practiceframework.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.clt.framework.component.common.AbstractValueObject;
import com.clt.framework.component.util.JSPUtil;

public class ConditionVO extends AbstractValueObject{
	private static final long serialVersionUID = 1L;
	
	private Collection<ConditionVO> models = new ArrayList<ConditionVO>();
	
	
	/* Column Info */
	private String joCrrCd = null;
	/* Column Info */
	private String rlaneCd = null;
	/* Column Info */
	private String acctYrmonFr = null;
	/* Column Info */
	private String acctYrmonTo = null;
	/* Column Info */
	private String trdCd = null;
	
	public String getJoCrrCd() {
		return joCrrCd;
	}

	public void setJoCrrCd(String joCrrCd) {
		this.joCrrCd = joCrrCd;
	}

	public String getRlaneCd() {
		return rlaneCd;
	}

	public void setRlaneCd(String rlaneCd) {
		this.rlaneCd = rlaneCd;
	}

	public String getAcctYrmonFr() {
		return acctYrmonFr;
	}

	public void setAcctYrmonFr(String acctYrmonFr) {
		this.acctYrmonFr = acctYrmonFr;
	}

	public String getAcctYrmonTo() {
		return acctYrmonTo;
	}

	public void setAcctYrmonTo(String acctYrmonTo) {
		this.acctYrmonTo = acctYrmonTo;
	}

	public String getTrdCd() {
		return trdCd;
	}

	public void setTrdCd(String trdCd) {
		this.trdCd = trdCd;
	}

	
	
	private HashMap<String, String> hashColumns = new LinkedHashMap<String, String>();

	private HashMap<String, String> hashFields = new LinkedHashMap<String, String>();
	
	public ConditionVO(){}
	
	public ConditionVO(String joCrrCd, String rlaneCd, String acctYrmonFr, String acctYrmonTo, String trdCd) {
		this.joCrrCd = joCrrCd;
		this.rlaneCd = rlaneCd;
		this.acctYrmonFr = acctYrmonFr;
		this.acctYrmonTo = acctYrmonTo;
		this.trdCd = trdCd;
	}
	
	public HashMap<String, String> getColumnValues(){
		this.hashColumns.put("jo_crr_cd", getJoCrrCd());
		this.hashColumns.put("rlane_cd", getRlaneCd());
		this.hashColumns.put("acct_yrmon_fr", getAcctYrmonFr());
		this.hashColumns.put("acct_yrmon_to", getAcctYrmonTo());
		this.hashColumns.put("trd_cd", getTrdCd());
		return this.hashColumns;
	}
	
	public HashMap<String, String> getFieldNames(){
		this.hashFields.put("jo_crr_cd", "joCrrCd");
		this.hashFields.put("rlane_cd", "rlaneCd");
		this.hashFields.put("s_fr_acct_yrmon", "acctYrmonFr");
		this.hashFields.put("s_to_acct_yrmon", "acctYrmonTo");
		this.hashFields.put("trd_cd", "trdCd");
		return this.hashFields;
	}
	
	
	public void fromRequest(HttpServletRequest request, String prefix) {
		setJoCrrCd(JSPUtil.getParameter(request, prefix + "s_jo_crr_cd", ""));
		setRlaneCd(JSPUtil.getParameter(request, prefix + "s_rlane_cd", ""));
		setAcctYrmonFr(JSPUtil.getParameter(request, prefix + "s_fr_acct_yrmon", ""));
		setAcctYrmonTo(JSPUtil.getParameter(request, prefix + "s_to_acct_yrmon", ""));
		setTrdCd(JSPUtil.getParameter(request, prefix + "s_trd_cd", ""));
	}
	
	public ConditionVO[] fromRequestGrid(HttpServletRequest request) {
		return fromRequestGrid(request, "");
	}
	
	public ConditionVO[] fromRequestGrid(HttpServletRequest request, String prefix) {
		ConditionVO model = null;
		
		String[] tmp = request.getParameterValues(prefix + "ibflag");
  		if(tmp == null)
   			return null;

  		int length = request.getParameterValues(prefix + "ibflag").length;
  
		try {
			String[] joCrrCd = (JSPUtil.getParameter(request, prefix	+ "s_jo_crr_cd", length));
			String[] rlaneCd = (JSPUtil.getParameter(request, prefix	+ "s_rlane_cd", length));
			String[] acctYrmonFr = (JSPUtil.getParameter(request, prefix	+ "s_fr_acct_yrmon", length));
			String[] acctYrmonTo = (JSPUtil.getParameter(request, prefix	+ "s_to_acct_yrmon", length));
			String[] trdCd = (JSPUtil.getParameter(request, prefix	+ "s_trd_cd", length));
			
			for (int i = 0; i < length; i++) {
				model = new ConditionVO();
				if (joCrrCd[i] != null)
					model.setJoCrrCd(joCrrCd[i]);
				if (rlaneCd[i] != null)
					model.setRlaneCd(rlaneCd[i]);
				if (acctYrmonFr[i] != null)
					model.setAcctYrmonFr(acctYrmonFr[i]);
				if (acctYrmonTo[i] != null)
					model.setAcctYrmonTo(acctYrmonTo[i]);
				if (trdCd[i] != null)
					model.setTrdCd(trdCd[i]);
				models.add(model);
			}

		} catch (Exception e) {
			return null;
		}
		return getConditionVOs();
	}
	
	public ConditionVO[] getConditionVOs(){
		ConditionVO[] vos = (ConditionVO[])models.toArray(new ConditionVO[models.size()]);
		return vos;
	}
	public String toString() {
		   return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE );
	   }

	public void unDataFormat(){
		this.joCrrCd = this.joCrrCd .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.rlaneCd = this.rlaneCd .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.acctYrmonFr = this.acctYrmonFr .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.acctYrmonTo = this.acctYrmonTo .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.trdCd = this.trdCd .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
	}
}
