package com.x.y.mongodb.common;

import com.mongodb.*;
import com.x.y.common.ConfigProps;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Log4j2
@Configuration
@EnableMongoRepositories(basePackages = "com.x.y.mongodb")
public class MongoConfig extends AbstractMongoConfiguration {
    private static ServerAddress seed = new ServerAddress(ConfigProps.getProp("mongodb_node"),
            Integer.parseInt(ConfigProps.getProp("mongodb_node_port")));
    private static String username = ConfigProps.getProp("mongodb_username");
    private static String password = ConfigProps.getProp("mongodb_password");
    private static String defaultDb = ConfigProps.getProp("mongodb_default_db");

    @Override
    protected String getDatabaseName() {
        return defaultDb;
    }

    @Override
    public Mongo mongo() {
        MongoCredential credential = MongoCredential.createCredential(username, defaultDb, password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().socketTimeout(20000).connectionsPerHost(80).build();
        return new MongoClient(seed, Collections.singletonList(credential), options);
    }
}