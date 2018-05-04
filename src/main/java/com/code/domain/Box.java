package com.code.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MaJian on 18/2/3.
 */
@Setter
@Getter
public class Box implements Serializable {
    private String id;
    private String userid;
    private String num;
    private String place;
    private String money;
    private String status;
    private String createTime;
    private String modifyTime;
    private String type;
    private String data;
    private List<Record> record;
    private User user;
    private int allCount;
    private int arawCount;

    private int YEAR;
    private int MONTH;
    private int DAY;

}
