package com.devit.checkmein.api.exception.handler;

import lombok.Getter;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Getter
public class CustomApiException extends RuntimeException {

	private ApiError apiError;

	public CustomApiException(ApiError apiError) {
		super(apiError.getMessage());
		this.apiError = apiError;
	}

}
