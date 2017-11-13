package com.devit.checkmein.persistense.repository;

import com.devit.checkmein.persistense.document.CheckInDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
public interface CheckInRepository extends MongoRepository<CheckInDocument, String> {
}
