package com.devit.checkmein.persistense.document;

import com.devit.checkmein.api.model.CheckInStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Data
@Document(collection = "CheckInDocumentV1")
public class CheckInDocument {

	private String id;
	@Indexed
	private String userId;
	@Indexed
	private String tableId;
	@Indexed
	private String businessId;
	private CheckInStatus checkInStatus;

}
