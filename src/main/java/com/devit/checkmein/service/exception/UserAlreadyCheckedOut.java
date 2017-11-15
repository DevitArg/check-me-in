package com.devit.checkmein.service.exception;

import com.devit.checkmein.api.exception.handler.ApiError;
import com.devit.checkmein.api.exception.handler.CustomApiException;

/**
 * @author Lucas.Godoy on 15/11/17.
 */
public class UserAlreadyCheckedOut extends CustomApiException {

	public UserAlreadyCheckedOut(ApiError apiError) {
		super(apiError);
	}

}
