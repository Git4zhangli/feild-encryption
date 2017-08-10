package com.example.feild.encryption.domain.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "返回数据")
public abstract class ResponseEntity<T> {
	/**返回状态码*/
	@ApiModelProperty(value = "响应码", dataType = "int", required = true, position = 0)
    private int code;
    
    /**提示信息*/
	@ApiModelProperty(value = "响应码描述", dataType = "String", required = true, position = 1)
    private String message;
    
    /**数据模型*/
	@ApiModelProperty(value = "返回数据", position = 2)
    private T data;
	
	public ResponseEntity() {
		super();
	}
    
	public ResponseEntity(int code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
