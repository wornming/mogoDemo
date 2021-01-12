package com.wonrming.demo.mongodb;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * mogodb java驱动测试
 * @author wornming
 * @date 2021/1/10
 */
@Slf4j
public class TestMongo {

    private MongoClient mongoClient;
    private DB mycolDb;
    private DBCollection collection;

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


    @Test
    public void testConnection() {
        try {
            log.info("Connect to database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void testCreateCollection() {
        DBObject object = new BasicDBObject();
        DBCollection dbCollection = mycolDb.createCollection("test", object);
        log.info("集合创建成功");
    }

    @Test
    public void testGetCollection() {
        DBCollection dbCollection = mycolDb.getCollection("test");
        log.info("集合获取成功");
    }

    @Test
    public void testInsertDocument() {
        DBCollection dbCollection = mycolDb.getCollection("test");
        System.out.println("集合获取成功");
        //插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
        DBObject document = new BasicDBObject().
                append("description", "database").
                append("likes", 130).
                append("by", "Fly").append("flat", new BasicDBObject().append("key", "2222"));
        List<DBObject> documents = new ArrayList<>();
        documents.add(document);
        dbCollection.insert(documents);
        System.out.println("文档插入成功");
    }

    @Test
    public void testFindDocument() {
        DBCollection dbCollection = mycolDb.getCollection("test");
        System.out.println("集合获取成功");

        //检索所有文档
        /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */
        DBCursor cursor = dbCollection.find();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object);
        }
    }

    @Test
    public void testFindOneDocument() {
        DBCollection dbCollection = mycolDb.getCollection("test");
        log.info("获取集合成功");

        DBObject queryObject = new BasicDBObject("description", "database");
        DBObject dbObject = dbCollection.findOne(queryObject);
        log.info(dbObject.toString());
    }

    @Test
    public void testLikeFindDocument() {
        Pattern queryPattern = Pattern.compile("a", Pattern.CASE_INSENSITIVE);
        DBObject queryObject = new BasicDBObject("description", queryPattern);
        Cursor cursor = collection.find(queryObject);

        while(cursor.hasNext()){
            DBObject obj = cursor.next();
            System.out.println(obj.toString());
        }
    }

    @Test
    public void testCompareFindDocument() {
        DBObject caseQuery = new BasicDBObject("$gt", 120);
        DBObject queryObject = new BasicDBObject("likes", caseQuery);

        Cursor cursor = collection.find(queryObject);

        while(cursor.hasNext()){
            DBObject obj = cursor.next();
            System.out.println(obj.toString());
        }
    }

    @Test
    public void testUpdateDocument() {
        DBObject newDocument = new BasicDBObject();
        newDocument.put("$set", new BasicDBObject().append("clients", 110));

        DBObject searchObject = new BasicDBObject().append("description", "database");
        WriteResult result = collection.update(searchObject, newDocument);
        System.out.println(result);
    }

    @Test
    public void testUpdateDocument2() {
        BasicDBObject newDocument =
                new BasicDBObject().append("$inc",
                        new BasicDBObject().append("total clients", 99));

        DBObject searchObject = new BasicDBObject().append("description", "database");
        WriteResult result = collection.update(searchObject, newDocument);
        System.out.println(result);
    }
}
