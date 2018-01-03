package com.devit.checkmein.service.impl;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestBuilderHelper;
import com.devit.checkmein.TestDBHelper;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.api.model.CheckInStatus;
import com.devit.checkmein.exception.NotFoundException;
import com.devit.checkmein.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.exception.UserAlreadyCheckedOut;
import com.devit.checkmein.persistense.document.CheckInDocument;
import com.devit.checkmein.persistense.repository.CheckInRepository;
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

	@Autowired
	private TestDBHelper testDBHelper;

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

	@Test
	public void checkOutUserWithNullCheckInIdFailure_test() {
		Throwable throwable = catchThrowable(() -> checkMeInService.checkOutUser(null));

		assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void checkOutUserWithInvalidCheckInIdFailure_test() {
		Throwable throwable = catchThrowable(() -> checkMeInService.checkOutUser("SomeNonExistingId"));

		assertThat(throwable).isInstanceOf(NotFoundException.class);
	}

	@Test
	public void checkOutUserAlreadyCheckedOutFailure_test() {
		final String checkInId = testDBHelper.saveCheckInAndGet(true).getId();

		Throwable throwable = catchThrowable(() -> checkMeInService.checkOutUser(checkInId));

		assertThat(throwable).isInstanceOf(UserAlreadyCheckedOut.class);
	}

	@Test
	public void checkOutUserSuccessfully_test() throws NotFoundException, UserAlreadyCheckedOut {
		final String checkInId = testDBHelper.saveCheckInAndGet(false).getId();

		CheckInBean checkInBean = checkMeInService.checkOutUser(checkInId);

		assertThat(checkInBean.getId()).isEqualTo(checkInId);
		assertThat(checkInBean.getCheckInStatus()).isEqualTo(CheckInStatus.CHECKEDOUT);
	}

}
