package com.devit.checkmein.api;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestDBHelper;
import com.devit.checkmein.api.model.CheckInBean;
import com.devit.checkmein.service.impl.WhoAreThereServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Lucas.Godoy on 19/11/17.
 */
public class WhoAreThereApiImplIT extends AbstractIT {

	private final static String BASE_URI = "/checkMeIn/v1";
	private final static String WHO_ARE_THERE_URI = BASE_URI + "/whoAreThere";

	@Autowired
	private WhoAreThereServiceImpl whoAreThereService;

	@Autowired
	private TestDBHelper testDBHelper;

	@Test
	public void whoAreThereSuccess_test() {
		int inCount = 5;
		int outCount = 3;
		testDBHelper.bulkCheckIn(inCount, outCount);

		List<CheckInBean> beans = Arrays.asList(
				given()
						.get(WHO_ARE_THERE_URI)
						.then()
						.statusCode(equalTo(HttpStatus.OK.value()))
						.extract().body().as(CheckInBean[].class));

		assertThat(beans).isNotEmpty();
		assertThat(beans.size()).isEqualTo(inCount);
	}

	@Test
	public void whoAreThereEmptyListSuccess_test() {
		int inCount = 0;
		int outCount = 3;
		testDBHelper.bulkCheckIn(inCount, outCount);

		List<CheckInBean> beans = Arrays.asList(
				given()
						.get(WHO_ARE_THERE_URI)
						.then()
						.statusCode(equalTo(HttpStatus.OK.value()))
						.extract().body().as(CheckInBean[].class));

		assertThat(beans).isEmpty();
	}

}
