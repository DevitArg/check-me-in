package com.devit.checkmein.api;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestBuilderHelper;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.api.model.CheckInStatus;
import com.devit.checkmein.api.model.ErrorResponse;
import com.devit.checkmein.exception.UserAlreadyCheckedIn;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.CheckMeInService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public class CheckMeInApiImplIT extends AbstractIT {

	private final static String BASE_URI = "/checkMeIn/v1";
	private final static String CHECK_ME_IN_URI = BASE_URI + "/checkMe/in";
	private final static String CHECK_ME_OUT_URI = BASE_URI + "/checkMe/out/{checkInId}";

	@Autowired
	private CheckMeInService checkMeInService;

	@Autowired
	private CheckInRepository repository;

	@Test
	public void checkUserInSuccessfully_test() {
		CheckInBean requestBody = TestBuilderHelper.buildCheckInBeanNullId();

		CheckInBean response = given()
				.body(requestBody)
				.when()
				.post(CHECK_ME_IN_URI)
				.then()
				.statusCode(equalTo(HttpStatus.CREATED.value()))
				.extract().body().as(CheckInBean.class);

		assertThat(response).isNotNull();
		assertThat(response.getTableId()).isEqualTo(requestBody.getTableId());
		assertThat(response.getUserId()).isEqualTo(requestBody.getUserId());
	}

	@Test
	public void checkUserInAgainUserFailure_test() throws UserAlreadyCheckedIn {
		CheckInBean requestBody = TestBuilderHelper.buildCheckInBeanNullId();
		checkMeInService.checkInUser(requestBody);

		ErrorResponse errorResponse = given()
				.body(requestBody)
				.when()
				.post(CHECK_ME_IN_URI)
				.then()
				.statusCode(equalTo(HttpStatus.CONFLICT.value()))
				.extract().body().as(ErrorResponse.class);

		assertThat(errorResponse.getMessage()).containsIgnoringCase("is already checked in");
	}

	@Test
	public void checkUserInInvalidBodyWithNullValues_test() {
		CheckInBean requestBody = new CheckInBean();
		given()
				.body(requestBody)
				.when()
				.post(CHECK_ME_IN_URI)
				.then().log().body(true)
				.statusCode(equalTo(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void checkUserOutSuccessfully_test() throws UserAlreadyCheckedIn {
		CheckInBean checkInBean = TestBuilderHelper.buildCheckInBeanNullId();
		checkInBean = checkMeInService.checkInUser(checkInBean);

		CheckInBean response = given()
				.pathParam("checkInId", checkInBean.getId())
				.when()
				.delete(CHECK_ME_OUT_URI)
				.then()
				.statusCode(equalTo(HttpStatus.OK.value()))
				.extract().body().as(CheckInBean.class);

		assertThat(response).isNotNull();
		assertThat(response.getTableId()).isEqualTo(checkInBean.getTableId());
		assertThat(response.getUserId()).isEqualTo(checkInBean.getUserId());
		assertThat(response.getCheckInStatus()).isEqualTo(CheckInStatus.CHECKEDOUT);
	}

	@Test
	public void checkUserOutNullCheckinIdFailure_test() {
		ErrorResponse errorResponse = given()
				.pathParam("checkInId", "nonExistingId")
				.when()
				.delete(CHECK_ME_OUT_URI)
				.then()
				.statusCode(equalTo(HttpStatus.NOT_FOUND.value()))
				.extract().body().as(ErrorResponse.class);

		assertThat(errorResponse).isNotNull();
		assertThat(errorResponse.getMessage()).containsIgnoringCase("The ckeckin with id nonExistingId does not exists");
	}

	@Test
	public void checkUserOutThatHasAlreadyCheckedOutBeforeFailure_test() throws UserAlreadyCheckedIn {
		CheckInBean checkInBean = TestBuilderHelper.buildCheckInBeanNullId();
		checkInBean = checkMeInService.checkInUser(checkInBean);
		repository.findById(checkInBean.getId())
				.ifPresent(doc -> {
					doc.setCheckInStatus(CheckInStatus.CHECKEDOUT);
					repository.save(doc);
				});

		ErrorResponse errorResponse = given()
				.pathParam("checkInId", checkInBean.getId())
				.when()
				.delete(CHECK_ME_OUT_URI)
				.then()
				.statusCode(equalTo(HttpStatus.CONFLICT.value()))
				.extract().body().as(ErrorResponse.class);

		assertThat(errorResponse).isNotNull();
		assertThat(errorResponse.getMessage())
				.containsIgnoringCase("The checkin with id " + checkInBean.getId() + " has been checked out already");
	}
}
