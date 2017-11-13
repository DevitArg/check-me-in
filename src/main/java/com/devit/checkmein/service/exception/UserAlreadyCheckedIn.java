package com.devit.checkmein.service.exception;

import com.devit.checkmein.api.exception.handler.ApiError;
import com.devit.checkmein.api.exception.handler.CustomApiException;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public class UserAlreadyCheckedIn extends CustomApiException {

	public UserAlreadyCheckedIn(ApiError apiError) {
		super(apiError);
	}
}
