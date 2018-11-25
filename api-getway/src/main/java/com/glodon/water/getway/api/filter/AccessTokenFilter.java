package com.glodon.water.getway.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.glodon.water.common.common.constant.Constants;
import com.glodon.water.common.model.request.TokenVO;
import com.glodon.water.common.model.result.AuthResult;
import com.glodon.water.common.util.TokenUtil;
import com.glodon.water.getway.api.AuthApi;
import com.netflix.zuul.context.RequestContext;

/**
 * 登录拦截器,未登录的用户直接返回未登录数据。
 * <p>
 * 只拦截post方式的请求
 * </p>
 * 
 * @author cml
 *
 */
public class AccessTokenFilter extends AbstractZuulFilter {


	@Value("${system.config.accessTokenFilter.ignore}")
	private String ignoreUrl;

	@Autowired
	private AuthApi authApi;

	private ResponseHandler responseHandler;

	@Override
	public Object run() {
		try {
			RequestContext context = getCurrentContext();		
			String token = context.getRequest().getHeader(Constants.PARAM_TOKEN);
				//	.getParameter();		
			logger.info("accessToken:" + token);
			logger.info("params:" + context.getRequestQueryParams());
			logger.info("contentLength:" + context.getRequest().getContentLength());
			logger.info("contentType:" + context.getRequest().getContentType());			

			// token 为空不处理
			if (StringUtils.isNotBlank(token)) {
				AuthResult authResult = authApi.parseToken(token);
				// 校验成功
				if (authResult.isSuccess()) {
					TokenVO tvo = TokenUtil.getInnerToken(authResult.getToken());
					String path = context.getRequest().getRequestURI();
					AuthResult authpath = authApi.pathAuthorization(tvo.getUserId(), path);
					//authResult = authApi.pathAuthorization(path);
					if (authpath.isSuccess()) {
						context.addZuulRequestHeader(Constants.IN_PARAM_TOKEN, authResult.getToken());
						return null;
					}
				}
			}
			if (responseHandler != null) {
				context.getResponse().setCharacterEncoding("UTF-8");
				context.setResponseStatusCode(responseHandler.getResponseCode());
				context.setResponseBody(responseHandler.getResponseBody(null, null));
			}
			context.setSendZuulResponse(false);		
			
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
		logger.info("" + ignoreUrl + "," + req.getRequestURI().toString());
		return StringUtils.equalsIgnoreCase(req.getMethod(), "post") && !StringUtils.contains(ignoreUrl, req.getRequestURI().toString());
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
