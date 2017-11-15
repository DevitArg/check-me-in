package com.devit.checkmein.service;

import com.devit.checkmein.api.exception.handler.NotFoundException;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.service.exception.UserAlreadyCheckedOut;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public interface CheckMeInService {

	CheckInBean checkInUser(CheckInBean checkInBean) throws UserAlreadyCheckedIn;

	CheckInBean checkOutUser(String checkInId) throws UserAlreadyCheckedOut, NotFoundException;
}
