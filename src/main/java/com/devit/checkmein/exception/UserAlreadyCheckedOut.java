package com.devit.checkmein.exception;

import com.devit.checkmein.api.model.ErrorResponse;

import javax.ws.rs.core.Response;

/**
 * @author Lucas.Godoy on 15/11/17.
 */
public class UserAlreadyCheckedOut extends RestAPIException {

	public UserAlreadyCheckedOut(String checkInId) {
		super();
		errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(Response.Status.CONFLICT.getStatusCode());
		errorResponse.setMessage(String.format("The checkin with id %s has been checked out already", checkInId));
	}

}
