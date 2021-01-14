package com.wornming.demo.mongodb.dao;

import com.mongodb.client.result.UpdateResult;
import com.wornming.demo.mongodb.exception.MongoOpetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class MongoDbDao {

    @Autowired
    @Qualifier(value = "primaryMongoTemplate")
    private MongoTemplate primaryMongoTemplate;

    @Autowired
    @Qualifier(value = "secondaryMongoTemplate")
    private MongoTemplate secondaryMongoTemplate;

    /**
     * 单个添加数据
     */
    public Boolean save(Object object, String collectionName, MongoDBType type) {
        try {
            this.getTemplate(type).save(object, collectionName);
            return true;
        } catch (Exception e) {
            log.error("新增数据到mongoDB失败!", e);
            return false;
        }
    }

    /**
     * 批量删除数据
     */
    public void delete(String[] ids, String collectionName, Class<?> entityClass, MongoDBType type) {
        this.getTemplate(type).findAllAndRemove(Query.query(Criteria.where("id").in(ids)), entityClass, collectionName);
    }

    /**
     * 删除整张表数据
     */
    public void dropCollection(String collectionName, MongoDBType type) {
        this.getTemplate(type).dropCollection(collectionName);
    }

    /**
     * 单个修改或新增数据
     */
    public boolean update(Query query, Update update, Class<?> entityClass, String collectionName, MongoDBType type) {
        try {
            UpdateResult infos = this.getTemplate(type).upsert(query, update, entityClass, collectionName);
            return infos.wasAcknowledged();
        } catch (Exception e) {
            log.error("修改数据异常!", e);
            return false;
        }
    }

    /**
     * 单个查询数据
     */
    public <T> T findById(String id, String connectionName, Class<T> tClass, MongoDBType type) {
        try {
            return this.getTemplate(type).findById(id, tClass, connectionName);
        } catch (Exception e) {
            log.error("根据ID:[" + id + "]查询数据异常!", e);
            return null;
        }
    }

    /**
     * 根据条件查询数据
     */
    public <T> List<T> findInfoList(Query query, Class<T> tClass, String collectionName, MongoDBType type) {
        try {
            List<T> infos = this.getTemplate(type).find(query, tClass, collectionName);
            return infos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据条件查询数据数量
     */
    public <T> Long count(Query query, Class<T> tClass, String collectionName, MongoDBType type) {
        try {
            Long count = this.getTemplate(type).count(query, tClass, collectionName);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 新增集合数据
     */
    public boolean insert(List<Object> list, String collectionName, MongoDBType type) {
        try {
            this.getTemplate(type).insert(list, collectionName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public enum MongoDBType {
        PRIMARY("主节点"),
        SECONDARY("副节点");

        private String desc;

        MongoDBType(String desc) {
            setDesc(desc);
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    private MongoTemplate getTemplate(MongoDBType type) {
        if (type == null) {
            throw new MongoOpetException("MongoDBType is null!");
        }
        switch (type) {
            case PRIMARY:
                return primaryMongoTemplate;
            case SECONDARY:
                return secondaryMongoTemplate;
            default:
                throw new MongoOpetException("MongoDBType not find!");
        }
    }
}
