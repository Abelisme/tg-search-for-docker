package com.will.tgchannel.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * BaseInterceptor
 */
@Component
public class BaseInterceptor implements HandlerInterceptor{

    // @Resource
	// private Integer defaultPageCount;

	// @Resource
	// private List<String> b2bCommonDateFormatPattern;

	// @Resource
	// private Map<String, String> appUrl;

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String basePath = request.getContextPath();
//		String fullPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//		+ basePath;

		// set RequestAttributes as inheritable for child threads
		ServletRequestAttributes att = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		RequestContextHolder.setRequestAttributes(att, true);

		// boolean isAjaxCall = request.getHeader("Accept") == null || request.getHeader("x-requested-with") != null
		// 		|| request.getHeader("Accept").indexOf("text/html") == -1;

		request.setAttribute("basePath", basePath);
		System.out.println(basePath);
		// request.setAttribute("dateFormatPattern", b2bCommonDateFormatPattern);
		// request.setAttribute("appUrl", appUrl);
		// request.setAttribute("pageCount", defaultPageCount);
		// request.setAttribute("isAjaxCall", isAjaxCall);
		// request.setAttribute("reqDate", new Date());

		return true;
	}
}