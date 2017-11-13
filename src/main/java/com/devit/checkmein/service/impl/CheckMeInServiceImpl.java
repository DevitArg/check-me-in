package com.devit.checkmein.service.impl;

import com.devit.checkmein.api.exception.handler.ApiError;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.CheckMeInService;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Service
public class CheckMeInServiceImpl implements CheckMeInService {

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private CheckInRepository checkInRepository;

	@Override
	public CheckInBean checkInUser(CheckInBean checkInBean) throws UserAlreadyCheckedIn {
		if (checkInBean == null) {
			throw new IllegalArgumentException("CheckInBean must not be null");
		}
		if (isUserCheckedIn(checkInBean.getUserId())) {
			ApiError apiError = new ApiError(HttpStatus.CONFLICT,
					String.format("The user %s is already checked in", checkInBean.getUserId()));
			throw new UserAlreadyCheckedIn(apiError);
		}
		CheckInDocument checkInDocument = dozerBeanMapper.map(checkInBean, CheckInDocument.class);
		checkInDocument = checkInRepository.save(checkInDocument);

		// TODO: send event to social_checkin kafka topic

		return dozerBeanMapper.map(checkInDocument, CheckInBean.class);
	}

	private boolean isUserCheckedIn(String userId) {
		return checkInRepository.findByUserId(userId).isPresent();
	}

}
