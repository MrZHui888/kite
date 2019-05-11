package com;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author : Guzh
 * @since : 2018/11/23
 */
@Data
public class Domain implements Serializable{
    /** 门店编号 */
    private String shopCode;

    /** 排班日期 */
    private Date date;


    private List<Integer> rosterTypeList;


    private List<Byte> failRosterStatusList;


    private LocalDateTime hehe;
}
