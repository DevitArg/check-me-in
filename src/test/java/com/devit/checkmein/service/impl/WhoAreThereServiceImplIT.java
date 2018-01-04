package com.devit.checkmein.service.impl;

import com.devit.checkmein.AbstractIT;
import com.devit.checkmein.TestDBHelper;
import com.devit.checkmein.api.model.CheckInBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lucas.Godoy on 19/11/17.
 */
public class WhoAreThereServiceImplIT extends AbstractIT {

	@Autowired
	private WhoAreThereServiceImpl whoAreThereService;

	@Autowired
	private TestDBHelper testDBHelper;

	@Test
	public void whoAreThereSuccess_test() {
		int inCount = 5;
		int outCount = 3;
		testDBHelper.bulkCheckIn(inCount, outCount);

		List<CheckInBean> beans = whoAreThereService.whoAreThere("bar");

		assertThat(beans).isNotEmpty();
		assertThat(beans.size()).isEqualTo(inCount);
	}

}
