package com.devit.checkmein.service.impl;

import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.api.model.CheckInStatus;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.WhoAreThereService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lucas.Godoy on 19/11/17.
 */
@Service
public class WhoAreThereServiceImpl implements WhoAreThereService {

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private CheckInRepository checkInRepository;


	@Override
	public List<CheckInBean> whoAreThere() {
		List<CheckInDocument> documents = checkInRepository.findByCheckInStatus(CheckInStatus.CHECKEDIN);
		return documents
				.stream()
				.map(d -> dozerBeanMapper.map(d, CheckInBean.class))
				.collect(Collectors.toList());
	}
}
