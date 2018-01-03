package com.devit.checkmein.exception;

import com.devit.checkmein.api.model.ErrorResponse;

import javax.ws.rs.core.Response;

public class InvalidDatesException extends RestAPIException {

	public InvalidDatesException(String message) {
		super();
		errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(Response.Status.BAD_REQUEST.getStatusCode());
		errorResponse.setMessage(message);
	}
}
