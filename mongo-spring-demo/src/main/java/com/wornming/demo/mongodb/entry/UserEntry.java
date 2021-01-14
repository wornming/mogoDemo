package com.wornming.demo.mongodb.entry;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wornming
 * @date 2021/1/14
 */
@Data
@Document("user")
public class UserEntry {
    private Long id;
    private String userName;
    private String passWord;
}
