package com.devit.checkmein;

import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lucas.Godoy on 16/11/17.
 */
@Component
public class TestDBHelper {

	@Autowired
	private CheckInRepository checkInRepository;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	public CheckInDocument saveCheckInAndGet(boolean shouldBeOut) {
		CheckInBean checkInBean = shouldBeOut ? TestBuilderHelper.buildCheckInWithCheckedOutStatusBeanNullId()
				: TestBuilderHelper.buildCheckInBeanNullId();

		CheckInDocument document = checkInRepository.save(dozerBeanMapper.map(checkInBean, CheckInDocument.class));

		return document;
	}

	public void bulkCheckIn(int inCount, int outCount) {
		for (int i = 0; i < inCount; i++) {
			saveCheckInAndGet(false);
		}
		for (int i = 0; i < outCount; i++) {
			saveCheckInAndGet(true);
		}
	}

}
