package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.CheckMeApi;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.service.CheckMeInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@RestController
public class CheckMeInApiImpl implements CheckMeApi {

	@Autowired
	private CheckMeInService checkMeInService;

	@Override
	public ResponseEntity<CheckInBean> userCheckOut(@PathVariable("checkInId") String checkInId) {
		return new ResponseEntity<>(checkMeInService.checkOutUser(checkInId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CheckInBean> userCheckin(@Valid @RequestBody CheckInBean checkInBean) {
		return new ResponseEntity<>(checkMeInService.checkInUser(checkInBean), HttpStatus.CREATED);
	}

}
