package com.wonrming.demo.mongodb;

import com.mongodb.*;
import org.junit.Before;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wornming
 * @date 2021/1/14
 */
public abstract class BaseTestMongo {
    protected MongoClient mongoClient;
    protected DB mycolDb;
    protected DBCollection collection;

    @Before
    public void getClient() throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "111111".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        DB db = mongoClient.getDB("test");
        this.mongoClient = mongoClient;
        this.mycolDb = db;
        this.collection = db.getCollection("test");
    }
}
