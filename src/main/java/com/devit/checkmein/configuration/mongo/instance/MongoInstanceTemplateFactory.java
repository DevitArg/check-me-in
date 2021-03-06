package com.devit.checkmein.configuration.mongo.instance;

import com.devit.checkmein.configuration.mongo.MongoTemplateFactory;
import com.devit.checkmein.configuration.mongo.MongoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Lucas.Godoy on 13/11/17.
 */
@Component
public class MongoInstanceTemplateFactory implements MongoTemplateFactory {

	@Autowired
	private MongoFactoryProvider mongoFactoryProvider;

	@Override
	public boolean supports(MongoType mongoType) {
		return MongoType.INSTANCE.equals(mongoType);
	}

	@Override
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoFactoryProvider.getMongoFactory());
	}
}
