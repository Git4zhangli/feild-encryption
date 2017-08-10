package com.example.feild.encryption.extension;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.feild.encryption.domain.pojo.dto.FailResponseEntity;
import com.example.feild.encryption.domain.pojo.dto.ResponseEntity;

@ControllerAdvice
@ResponseBody
public class ResponseExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ResponseExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<?> exceptionHandler(Exception ex) {
		LOG.info("服务器开小差了，请稍后再试！", ex);
		return new FailResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), StringUtils.defaultIfEmpty(ex.getMessage(), "服务器开小差了，请稍后再试！"));
	}
}
