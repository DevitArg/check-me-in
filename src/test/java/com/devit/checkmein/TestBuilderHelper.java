package com.devit.checkmein;

import com.devit.checkmein.api.model.CheckInBean;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public class TestBuilderHelper {

	public static CheckInBean buildCheckInBeanNullId() {
		CheckInBean nullId = random(CheckInBean.class);
		nullId.setId(null);
		return nullId;
	}

}
