/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.tansun.scf.log.loan.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class BaseAppAction {

	@Autowired
	private HttpServletRequest request;
	
	private String viewPrefix;

	public BaseAppAction() {
		setViewPrefix(defaultViewPrefix());
	}

	public void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	public String getViewPrefix() {
		return this.viewPrefix;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	protected String defaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping requestMapping = (RequestMapping) AnnotationUtils.findAnnotation(super.getClass(),
				RequestMapping.class);
		if ((requestMapping != null) && (requestMapping.value().length > 0)) {
			currentViewPrefix = requestMapping.value()[0];
		}

		if (StringUtils.isEmpty(currentViewPrefix)) {
			currentViewPrefix = "page";
		}

		return currentViewPrefix;
	}

	protected ModelAndView initPublicView(String src) {
		ModelAndView view = new ModelAndView("public");
		view.addObject("jsp", src);
		view.addObject("footer", "template/public_footer.jsp");
		return view;
	}

	protected ModelAndView initDialogView(String src) {
		ModelAndView view = new ModelAndView("dialog");
		view.addObject("jsp", src);
		return view;
	}

	protected ModelAndView initCenterView(String src) {
		ModelAndView view = new ModelAndView("public");
		view.addObject("jsp", "center.jsp");
		view.addObject("rightJsp", src);
		view.addObject("footer", "template/center_footer.jsp");
		return view;
	}

	protected ModelAndView initCommonView(String src) {
		ModelAndView view = new ModelAndView("public");
		view.addObject("jsp", src);
		view.addObject("footer", "template/center_footer.jsp");
		return view;
	}

	protected ModelAndView initPublicView() {
		ModelAndView view = new ModelAndView("public");
		return view;
	}

	protected ModelAndView initDialogView() {
		ModelAndView view = new ModelAndView("dialog");
		return view;
	}



	protected Map<String, String> getParameterMap(HttpServletRequest request) {
		Map properties = request.getParameterMap();

		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();

		String key = "";
		String value = "";
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			key = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) (String[]) valueObj;
				for (int i = 0; i < values.length; ++i) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(key, value);
		}
		return returnMap;
	}


	protected String getIp() {
		String ipAddress = null;
		ipAddress = this.request.getHeader("X-Forwarded-For");
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = this.request.getHeader("X-Real-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = this.request.getHeader("Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = this.request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = this.request.getRemoteAddr();
		}

		if ((ipAddress != null) && (ipAddress.length() > 15) && (ipAddress.indexOf(",") > 0)) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}

		return ipAddress;
	}
}