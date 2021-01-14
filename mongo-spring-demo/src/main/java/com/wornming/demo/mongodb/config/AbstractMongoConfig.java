package com.wornming.demo.mongodb.config;

import com.mongodb.ConnectionString;
import lombok.Data;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * Mongconfig创建工厂
 * @author wornming
 * @date 2021/1/14
 */
@Data
public abstract class AbstractMongoConfig {
    //连接MongoDB地址
    private String uri;

    /**
     * 获取mongoDBTtemplate对象
     */
    public abstract MongoTemplate getMongoTemplate() throws Exception;

    /**
     * 创建mongoDb工厂
     */
    public MongoDatabaseFactory mongoDbFactory() throws Exception {
        ConnectionString connectionString = new ConnectionString(uri);
        return new SimpleMongoClientDatabaseFactory(connectionString);
    }
}
