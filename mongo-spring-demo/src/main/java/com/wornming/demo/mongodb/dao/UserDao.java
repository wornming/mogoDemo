package com.wornming.demo.mongodb.dao;

import com.wornming.demo.mongodb.entry.UserEntry;

/**
 * @author wornming
 * @date 2021/1/14
 */
public interface UserDao {

    /**
     * 创建对象
     * @param entry
     */
    void saveUser(UserEntry entry);


    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    UserEntry findUserByUserName(String userName);

    /**
     * 更新对象
     * @param entry
     */
    void updateUser(UserEntry entry);

    /**
     * 删除对象
     * @param id
     */
    void deleteUser(Long id);
}
