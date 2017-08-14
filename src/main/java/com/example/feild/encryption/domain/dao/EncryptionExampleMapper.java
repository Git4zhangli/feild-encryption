package com.example.feild.encryption.domain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.feild.encryption.domain.pojo.model.EncryptionExample;

@Mapper
public interface EncryptionExampleMapper {
	EncryptionExample queryByMobile(@Param("mobile") String mobile);

	List<EncryptionExample> pagingQuery();

	int addEntity(EncryptionExample entity);

	int updateEntity(EncryptionExample entity);
}
