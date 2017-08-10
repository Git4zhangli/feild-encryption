package com.example.feild.encryption.domain.pojo.model;

import java.util.Date;

/**
 * 加密示例表
 * @author zhangli 2017年8月9日
 */
public class EncryptionExample {
	/**ID*/
	private Long id;

	/**手机号*/
	private String mobile;

	/**微信号*/
	private String wechat;

	/**QQ号*/
	private String qq;

	/**邮箱号*/
	private String email;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**备注*/
	private String desc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
