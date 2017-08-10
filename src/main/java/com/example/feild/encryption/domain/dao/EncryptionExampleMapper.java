package com.example.feild.encryption.domain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.feild.encryption.domain.pojo.model.EncryptionExample;

public interface EncryptionExampleMapper {
	EncryptionExample queryByMobile(@Param("mobile") String mobile);

	List<EncryptionExample> pagingAll();

	int addEntity(EncryptionExample entity);

	int updateEntity(EncryptionExample entity);
}
