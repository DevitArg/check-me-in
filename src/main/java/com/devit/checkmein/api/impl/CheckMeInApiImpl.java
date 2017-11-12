package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.CheckMeApi;
import com.devit.checkmein.api.model.CheckinWS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@RestController
public class CheckMeInApiImpl implements CheckMeApi {

	@Override
	public ResponseEntity<String> userCheckOut(UUID userUuid) {
		return null;
	}

	@Override
	public ResponseEntity<String> userCheckin(CheckinWS checkinWS) {
		return null;
	}

}
