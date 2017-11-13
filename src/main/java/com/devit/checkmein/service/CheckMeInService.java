package com.devit.checkmein.service;

import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public interface CheckMeInService {

	CheckInBean checkInUser(CheckInBean checkInBean) throws UserAlreadyCheckedIn;

}
