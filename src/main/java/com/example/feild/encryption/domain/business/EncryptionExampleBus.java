package com.example.feild.encryption.domain.business;

public interface EncryptionExampleBus {
	void addEntity(String mobile, String wechat, String qq, String email, String desc);

	void updateByMobile(String mobile, String wechat, String qq, String email, String desc);
}
