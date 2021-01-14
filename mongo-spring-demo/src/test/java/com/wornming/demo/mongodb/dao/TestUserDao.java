package com.wornming.demo.mongodb.dao;

import com.wornming.demo.mongodb.entry.UserEntry;
import net.bytebuddy.asm.Advice;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wornming
 * @date 2021/1/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserDao {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MongoDbDao mongoDbDao;

    @Test
    public void testSaveUser() throws Exception {
        UserEntry user=new UserEntry();
        user.setId(2l);
        user.setUserName("小明");
        user.setPassWord("fffooo123");
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName(){
        UserEntry user= userDao.findUserByUserName("小明");
        System.out.println("user is "+user);
    }

    @Test
    public void updateUser(){
        UserEntry user=new UserEntry();
        user.setId(2l);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById(){
        userDao.deleteUser(1l);
    }

    @Test
    public void testInsertPrimary() {
        UserEntry user=new UserEntry();
        user.setId(3l);
        user.setUserName("小红");
        user.setPassWord("wosss");
        boolean insert = mongoDbDao.insert(Lists.newArrayList(user), "user", MongoDbDao.MongoDBType.PRIMARY);

        Assert.assertTrue(insert);
    }

    @Test
    public void testInsertSecondary() {
        UserEntry user=new UserEntry();
        user.setId(4L);
        user.setUserName("小花");
        user.setPassWord("sss");
        boolean insert = mongoDbDao.insert(Lists.newArrayList(user), "user", MongoDbDao.MongoDBType.SECONDARY);

        Assert.assertTrue(insert);
    }
}
