package com.devit.checkmein.com.devit.checkmein.service.impl;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestBuilderHelper;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.service.impl.CheckMeInServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public class CheckMeInServiceImplIT extends AbstractIT {

	@Autowired
	private CheckMeInServiceImpl checkMeInService;

	@Autowired
	private CheckInRepository checkInRepository;

	@Test
	public void checkInDocumentSavedSuccessfully_test() throws UserAlreadyCheckedIn {
		CheckInBean checkInBean = TestBuilderHelper.buildCheckInBeanNullId();
		checkInBean = checkMeInService.checkInUser(checkInBean);
		String id = checkInBean.getId();

		CheckInDocument document = checkInRepository.findOne(id);

		assertThat(document).isNotNull();
		assertThat(document.getId()).isEqualTo(id);
		assertThat(document.getTableId()).isEqualTo(checkInBean.getTableId());
		assertThat(document.getUserId()).isEqualTo(checkInBean.getUserId());
	}

	@Test
	public void nullCheckInDocumentNotSaved_test() {
		Throwable throwable = catchThrowable(() -> checkMeInService.checkInUser(null));

		assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
	}

}
