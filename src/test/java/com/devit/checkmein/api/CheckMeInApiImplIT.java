package com.devit.checkmein.api;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestBuilderHelper;
import com.devit.checkmein.api.exception.handler.ApiError;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.persistense.repository.CheckInRepository;
import com.devit.checkmein.service.CheckMeInService;
import com.devit.checkmein.service.exception.UserAlreadyCheckedIn;
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

	@Autowired
	private CheckMeInService checkMeInService;

	@Test
	public void checkUserInSuccessfully_test() {
		CheckInBean requestBody = TestBuilderHelper.buildCheckInBeanNullId();

		CheckInBean response = given()
				.body(requestBody)
				.when()
				.post("/checkMe/in")
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

		ApiError apiError = given()
				.body(requestBody)
				.when()
				.post("/checkMe/in")
				.then()
				.statusCode(equalTo(HttpStatus.CONFLICT.value()))
				.extract().body().as(ApiError.class);

		assertThat(apiError.getMessage()).containsIgnoringCase("is already checked in");
	}

	@Test
	public void checkUserInInvalidBodyWithNullValues_test() {
		CheckInBean requestBody = new CheckInBean();
		given()
				.body(requestBody)
				.when()
				.post("/checkMe/in")
				.then()
				.statusCode(equalTo(HttpStatus.BAD_REQUEST.value()));
	}
}
