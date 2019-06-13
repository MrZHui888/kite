package com.kite.mapstruct;

import lombok.Data;

/**
 * @author : Guzh
 * @since : 2019-06-01
 */
@Data
public class UserVo {
	private Integer age;

	private String name;


	private String sex;

	private StudentVo student;
}
