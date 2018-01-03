package com.devit.checkmein.api.impl;

import com.devit.checkmein.api.CheckMeInApi;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.exception.NotFoundException;
import com.devit.checkmein.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.exception.UserAlreadyCheckedOut;
import com.devit.checkmein.service.CheckMeInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Component
public class CheckMeInApiImpl implements CheckMeInApi {

	@Autowired
	private CheckMeInService checkMeInService;

	@Override
	public Response userCheckOut(String checkInId) {
		try {
			return Response.ok(checkMeInService.checkOutUser(checkInId)).build();
		} catch (UserAlreadyCheckedOut | NotFoundException e) {
			throw e.throwRestException();
		}
	}

	@Override
	public Response userCheckin(CheckInBean checkInBean) {
		try {
			return Response.status(Response.Status.CREATED).entity(checkMeInService.checkInUser(checkInBean)).build();
		} catch (UserAlreadyCheckedIn e) {
			throw e.throwRestException();
		}
	}

}
