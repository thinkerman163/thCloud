package com.glodon.water.getway;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.glodon.water.getway.api.filter.AccessTokenFilter;
import com.glodon.water.getway.api.filter.AuthResponseFilter;
import com.glodon.water.getway.api.filter.ErrorFilter;
import com.glodon.water.getway.api.filter.GetRequestAccessTokenFilter;
import com.glodon.water.getway.api.filter.ResponseHandler;
import com.google.gson.Gson;

@Configuration
public class CustomZuulFilterConfiguration {

	@Bean
	public AccessTokenFilter accessTokenFilter(AccessTokenResponseHandler handler) {
		AccessTokenFilter accessTokenFilter = new AccessTokenFilter();
		accessTokenFilter.setResponseHandler(handler);
		return accessTokenFilter;
	}

	@Bean
	public ErrorFilter errorFilter(CustomErrorHandler errorHandler) {
		ErrorFilter errorFilter = new ErrorFilter();
		errorFilter.setErrorHandler(errorHandler);
		return errorFilter;
	}

	@Bean
	public GetRequestAccessTokenFilter getRequestAccessTokenFilter() {
		return new GetRequestAccessTokenFilter();
	}

	@Bean
	public AuthResponseFilter responseFilter() {
		return new AuthResponseFilter();
	}

	@Component
	public class AccessTokenResponseHandler implements ResponseHandler {

		@Value("${system.config.error.invalidToken}")
		private String invalidTokenMessage;

		@Override
		public int getResponseCode() {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		@Override
		public String getResponseBody(String originMessage, Throwable e) {
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", HttpServletResponse.SC_BAD_REQUEST);
			result.put("message", invalidTokenMessage);
			return gson.toJson(result);
		}
	}

	/**
	 * zuul 错误处理
	 * 
	 * @author cml
	 *
	 */
	@Component
	public class CustomErrorHandler implements ResponseHandler {

		@Value("${system.config.error.message}")
		private String errorMessage;

		@Override
		public int getResponseCode() {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		@Override
		public String getResponseBody(String originMessage, Throwable e) {
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result.put("message", errorMessage);
			return gson.toJson(result);
		}
	}

}
