package com.devit.checkmein.persistense.repository;

import com.devit.checkmein.api.model.CheckInStatus;
import com.devit.checkmein.persistense.document.CheckInDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public interface CheckInRepository extends MongoRepository<CheckInDocument, String> {

	Optional<CheckInDocument> findByUserId(String userId);

	Optional<CheckInDocument> findById(String id);

	List<CheckInDocument> findByCheckInStatusAndBusinessId(CheckInStatus checkInStatus, String businessId);

}
