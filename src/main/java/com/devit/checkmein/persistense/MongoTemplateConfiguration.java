package com.devit.checkmein.persistense;

import com.devit.checkmein.configuration.mongo.MongoTemplateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.devit.checkmein.persistense.repository"},
		mongoTemplateRef = "mongoTemplate")
public class MongoTemplateConfiguration {

	@Autowired
	private MongoTemplateProvider mongoTemplateProvider;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return mongoTemplateProvider.getMongoTemplate();
	}
}
