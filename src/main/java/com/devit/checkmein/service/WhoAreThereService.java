package com.devit.checkmein.service;

import com.devit.checkmein.api.model.CheckInBean;

import java.util.List;

/**
 * @author Lucas.Godoy on 19/11/17.
 */
public interface WhoAreThereService {

	List<CheckInBean> whoAreThere(String businessId);

}
