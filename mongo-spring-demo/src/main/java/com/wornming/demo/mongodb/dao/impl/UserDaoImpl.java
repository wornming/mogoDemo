package com.wornming.demo.mongodb.dao.impl;

import com.wornming.demo.mongodb.dao.UserDao;
import com.wornming.demo.mongodb.entry.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author wornming
 * @date 2021/1/14
 */
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(UserEntry entry) {
        mongoTemplate.save(entry);
    }

    @Override
    public UserEntry findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserEntry user = mongoTemplate.findOne(query, UserEntry.class);
        return user;
    }

    @Override
    public void updateUser(UserEntry entry) {
        Query query = new Query(Criteria.where("id").is(entry.getId()));
        Update update = new Update().set("userName", entry.getUserName()).set("passWord", entry.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, UserEntry.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    @Override
    public void deleteUser(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserEntry.class);
    }
}
