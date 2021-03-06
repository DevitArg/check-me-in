package com.devit.checkmein.exception;

import com.devit.checkmein.api.model.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author Lucas.Godoy on 11/12/17.
 */
public abstract class RestAPIException extends Exception {

	@Setter
	@Getter
	protected ErrorResponse errorResponse;

	public WebApplicationException throwRestException() {
		Response.Status httpStatus = Response.Status.fromStatusCode(errorResponse.getHttpStatus());
		Response response = Response.status(httpStatus).entity(errorResponse).build();
		return new WebApplicationException(response);
	}
}
