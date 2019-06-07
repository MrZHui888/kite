package com.kite.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Guzh
 * @since : 2019-06-01
 */
@Mapper
public interface UserInterface {

	UserInterface userInterface = Mappers.getMapper(UserInterface.class);



	@Mapping(target = "user.student", source = "studentVo")
	UserDto userVoToDto(UserVo userVo,StudentVo studentVo);

}
