package com.x.y.mongodb.common;

import com.mongodb.*;
import com.x.y.common.ConfigProps;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@Configuration
@EnableMongoRepositories(basePackages = "com.x.y.mongodb")
public class MongoConfig extends AbstractMongoConfiguration {
    private static ServerAddress seed1 = new ServerAddress(ConfigProps.getProp("mongodb_node1"),
            Integer.parseInt(ConfigProps.getProp("mongodb_node1_port")));
    private static ServerAddress seed2 = new ServerAddress(ConfigProps.getProp("mongodb_node2"),
            Integer.parseInt(ConfigProps.getProp("mongodb_node2_port")));
    private static String username = ConfigProps.getProp("mongodb_username");
    private static String password = ConfigProps.getProp("mongodb_passwd");
    private static String replsetname = ConfigProps.getProp("mongodb_replsetname");
    private static String defaultDb = ConfigProps.getProp("mongodb_default_db");
    private static Boolean mogodbAliyunClusterEnabled = Boolean.parseBoolean(ConfigProps.getProp("mongodb_aliyun_cluster_enabled"));

    @Override
    protected String getDatabaseName() {
        return defaultDb;
    }

    @Override
    public Mongo mongo() {
        if (mogodbAliyunClusterEnabled) {
            return createMongoDBClient();
        }
        return createMongoTestDBClient();
    }

    private static MongoClient createMongoTestDBClient() {
        MongoCredential credential = MongoCredential.createCredential(username, defaultDb, password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().socketTimeout(20000).connectionsPerHost(80).build();
        return new MongoClient(seed1, Collections.singletonList(credential), options);
    }

    private static MongoClient createMongoDBClient() {
        List<ServerAddress> seedList = new ArrayList<>();
        seedList.add(seed1);
        seedList.add(seed2);
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createScramSha1Credential(username, defaultDb, password.toCharArray()));
        MongoClientOptions options = MongoClientOptions.builder().requiredReplicaSetName(replsetname).socketTimeout(20000).connectionsPerHost(80).build();
        return new MongoClient(seedList, credentials, options);
    }
}