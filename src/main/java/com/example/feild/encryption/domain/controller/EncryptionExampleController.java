package com.example.feild.encryption.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.feild.encryption.domain.business.EncryptionExampleBus;
import com.example.feild.encryption.domain.pojo.dto.ResponseEntity;
import com.example.feild.encryption.domain.pojo.dto.SuccessResponseEntity;
import com.example.feild.encryption.domain.pojo.model.EncryptionExample;
import com.example.feild.encryption.domain.service.EncryptionExampleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class EncryptionExampleController {
	@Autowired
	private EncryptionExampleService encryptionExampleService;
	@Autowired
	private EncryptionExampleBus encryptionExampleBus;
	
	@RequestMapping(value = "/query/{mobile}", method = RequestMethod.GET)
	@ApiOperation("根据手机号查询用户信息")
	@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "path")
	public ResponseEntity<EncryptionExample> queryByAcctId(@PathVariable String mobile) {
		EncryptionExample entity = encryptionExampleService.queryByMobile(mobile);
		return new SuccessResponseEntity<>(entity);
	}

	@RequestMapping(value = "/query/paging/{pageNum}/{pageSize}", method = RequestMethod.GET)
	@ApiOperation("分页查询数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pageNum", value = "查询页码", dataType = "int", required = true, paramType = "path"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = true, paramType = "path")
	})
	public ResponseEntity<PageInfo<EncryptionExample>> pagingQuery(@PathVariable int pageNum, @PathVariable int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<EncryptionExample> dataList = encryptionExampleService.pagingQuery();
		PageInfo<EncryptionExample> pageInfo = new PageInfo<>(dataList);
		return new SuccessResponseEntity<>(pageInfo);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation("新增用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
		@ApiImplicitParam(name = "wechat", value = "微信号码", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "qq", value = "QQ号码", dataType = "long", paramType = "query"),
		@ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "desc", value = "描述", dataType = "String", paramType = "query")
	})
	public ResponseEntity<Boolean> add(@RequestParam String mobile, 
			@RequestParam(required = false) String wechat, 
			@RequestParam(required = false) String qq,
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) String desc) {
		encryptionExampleBus.addEntity(mobile, wechat, qq, email, desc);
		return new SuccessResponseEntity<>(true);
	}
	
	@RequestMapping(value = "/change/{mobile}", method = RequestMethod.PUT)
	@ApiOperation("修改用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "path"),
		@ApiImplicitParam(name = "wechat", value = "微信号码", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "qq", value = "QQ号码", dataType = "long", paramType = "query"),
		@ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "desc", value = "描述", dataType = "String", paramType = "query")
	})
	public ResponseEntity<Boolean> change(@PathVariable String mobile, 
			@RequestParam(required = false) String wechat, 
			@RequestParam(required = false) String qq,
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) String desc) {
		encryptionExampleBus.updateByMobile(mobile, wechat, qq, email, desc);
		return new SuccessResponseEntity<>(true);
	}
}
