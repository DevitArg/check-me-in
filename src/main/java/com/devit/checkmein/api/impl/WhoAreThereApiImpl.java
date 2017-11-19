package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.WhoAreThereApi;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.service.WhoAreThereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@RestController
public class WhoAreThereApiImpl implements WhoAreThereApi {

	@Autowired
	private WhoAreThereService whoAreThereService;

	@Override
	public ResponseEntity<List<CheckInBean>> whoAreThere() {
		return new ResponseEntity<List<CheckInBean>>(whoAreThereService.whoAreThere(), HttpStatus.OK);
	}

}
