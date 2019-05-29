package com.lvshihao.userEntity;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
public class User implements Serializable{
    private Integer id;

    private String name;

    private String meat;

    private String big;

    public User(String name, String meat, String big) {
        this.name = name;
        this.meat = meat;
        this.big = big;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
