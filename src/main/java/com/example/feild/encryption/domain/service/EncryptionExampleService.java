package com.example.feild.encryption.domain.service;

import java.util.List;

import com.example.feild.encryption.domain.pojo.model.EncryptionExample;

public interface EncryptionExampleService {
	EncryptionExample queryByMobile(String mobile);

	List<EncryptionExample> pagingAll();

	int addEntity(String mobile, String wechat, String qq, String email, String desc);

	int updateById(Long id, String mobile, String wechat, String qq, String email, String desc);
}
