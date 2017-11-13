package com.devit.checkmein.api.exception.handler;

import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {UserAlreadyCheckedIn.class})
	protected ResponseEntity<Object> handleUserAlreadyCheckedInException(CustomApiException customApiException,
																		 WebRequest request) {
		return handleExceptionInternal(customApiException, customApiException.getApiError(),
				null, customApiException.getApiError().getHttpStatus(), request);
	}

}