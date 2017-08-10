package com.example.feild.encryption.domain.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.feild.encryption.domain.dao.EncryptionExampleMapper;
import com.example.feild.encryption.domain.pojo.model.EncryptionExample;
import com.example.feild.encryption.domain.service.EncryptionExampleService;

@Service
public class EncryptionExampleServiceImpl implements EncryptionExampleService {
	
	@Autowired
	private EncryptionExampleMapper encryptionExampleMapper;

	@Override
	public EncryptionExample queryByMobile(String mobile) {
		return encryptionExampleMapper.queryByMobile(mobile);
	}

	@Override
	public List<EncryptionExample> pagingAll() {
		return encryptionExampleMapper.pagingAll();
	}

	@Override
	public int addEntity(String mobile, String wechat, String qq, String email, String desc) {
		EncryptionExample entity = new EncryptionExample();
		entity.setMobile(mobile);
		entity.setWechat(wechat);
		entity.setQq(qq);
		entity.setEmail(email);
		Date now = new Date();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		entity.setDesc(desc);
		return encryptionExampleMapper.addEntity(entity);
	}

	@Override
	public int updateById(Long id, String mobile, String wechat, String qq, String email, String desc) {
		EncryptionExample entity = new EncryptionExample();
		entity.setId(id);
		entity.setMobile(mobile);
		entity.setWechat(wechat);
		entity.setQq(qq);
		entity.setEmail(email);
		entity.setUpdateTime(new Date());
		entity.setDesc(desc);
		return encryptionExampleMapper.updateEntity(entity);
	}
}
