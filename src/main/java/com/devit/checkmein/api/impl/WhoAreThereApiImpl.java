package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.WhoAreThereApi;
import com.devit.checkmein.api.model.CheckInBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@RestController
public class WhoAreThereApiImpl implements WhoAreThereApi {

	@Override
	public ResponseEntity<List<CheckInBean>> whoAreThere() {
		return null;
	}

}
