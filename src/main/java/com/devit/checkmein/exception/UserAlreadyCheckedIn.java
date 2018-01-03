package com.devit.checkmein.exception;

import com.devit.checkmein.api.model.ErrorResponse;

import javax.ws.rs.core.Response;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public class UserAlreadyCheckedIn extends RestAPIException {

	public UserAlreadyCheckedIn(String userId) {
		super();
		errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(Response.Status.CONFLICT.getStatusCode());
		errorResponse.setMessage(String.format("The user %s is already checked in", userId));
	}

}
