package com.example.feild.encryption.domain.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.feild.encryption.domain.business.EncryptionExampleBus;
import com.example.feild.encryption.domain.pojo.model.EncryptionExample;
import com.example.feild.encryption.domain.service.EncryptionExampleService;

@Service
public class EncryptionExampleBusImpl implements EncryptionExampleBus {

	@Autowired
	private EncryptionExampleService encryptionExampleService;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void addEntity(String mobile, String wechat, String qq, String email, String desc) {
		// 1.查询
		EncryptionExample entity = encryptionExampleService.queryByMobile(mobile);
		Assert.isNull(entity, "用户信息已存在");
		
		// 2.修改
		encryptionExampleService.addEntity(mobile, wechat, qq, email, desc);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateByMobile(String mobile, String wechat, String qq, String email, String desc) {
		// 1.查询
		EncryptionExample entity = encryptionExampleService.queryByMobile(mobile);
		Assert.notNull(entity, "用户信息不存在");
		
		// 2.修改
		encryptionExampleService.updateById(entity.getId(), mobile, wechat, qq, email, desc);
	}
}
