package com.devit.checkmein.service.impl;

import com.devit.checkmein.api.exception.handler.ApiError;
import com.devit.checkmein.api.exception.handler.NotFoundException;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.api.model.CheckInStatus;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.CheckMeInService;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.service.exception.UserAlreadyCheckedOut;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

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
		checkInDocument.setCheckInStatus(CheckInStatus.CHECKEDIN);
		checkInDocument = checkInRepository.save(checkInDocument);

		// TODO: send event to social_checkin kafka topic

		return dozerBeanMapper.map(checkInDocument, CheckInBean.class);
	}

	@Override
	public CheckInBean checkOutUser(String checkInId) throws UserAlreadyCheckedOut, NotFoundException {
		if (StringUtils.isEmpty(checkInId)) {
			throw new IllegalArgumentException("CheckInBean must not be null");
		}
		CheckInDocument checkInDocument = checkInRepository.findById(checkInId)
				.orElseThrow(() -> new NotFoundException(new ApiError(HttpStatus.NOT_FOUND,
						String.format("The ckeckin with id %s does not exists", checkInId))));

		if(CheckInStatus.CHECKEDOUT.equals(checkInDocument.getCheckInStatus())) {
			throw new UserAlreadyCheckedOut(new ApiError(HttpStatus.CONFLICT,
					String.format("The checkin with id %s has been checked out already", checkInId)));
		}

		checkInDocument.setCheckInStatus(CheckInStatus.CHECKEDOUT);
		checkInRepository.save(checkInDocument);

		return dozerBeanMapper.map(checkInDocument, CheckInBean.class);
	}

	private boolean isUserCheckedIn(String userId) {
		Optional<CheckInDocument> documentOptional = checkInRepository.findByUserId(userId);
		return documentOptional.isPresent()
				&& CheckInStatus.CHECKEDIN.equals(documentOptional.get().getCheckInStatus());
	}

}
