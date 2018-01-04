package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.WhoAreThereApi;
import com.devit.checkmein.service.WhoAreThereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Component
public class WhoAreThereApiImpl implements WhoAreThereApi {

	@Autowired
	private WhoAreThereService whoAreThereService;

	@Override
	public Response whoAreThere(String businessId) {
		return Response.ok(whoAreThereService.whoAreThere(businessId)).build();
	}
}
