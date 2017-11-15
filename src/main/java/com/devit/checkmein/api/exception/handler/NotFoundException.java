package com.devit.checkmein.api.exception.handler;

/**
 * @author Lucas.Godoy on 15/11/17.
 */
public class NotFoundException extends CustomApiException {

	public NotFoundException(ApiError apiError) {
		super(apiError);
	}

}
