package com.example.feild.encryption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.util.StopWatch;

import com.example.feild.encryption.extension.util.DataCoderUtil;

public class Test {

	public static void main(String[] args) throws InvalidFormatException, IOException {
//		System.out.println(DataCoderUtil.getInstance().encrypt("13121152666"));
//		System.out.println(DataCoderUtil.getInstance().encrypt("alibaba666666"));
//		System.out.println(DataCoderUtil.getInstance().encrypt("123456789"));
//		System.out.println(DataCoderUtil.getInstance().encrypt("123456789@qq.com"));
//		System.out.println(DataCoderUtil.getInstance().encrypt("阿里巴巴"));
		
		// 长度测试
//		lenghTest();
		
		// excel解密测试
		decryptTest("C:\\Users\\ali\\Desktop\\解密测试.xlsx");
	}
	
	public static void lenghTest() {
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= 50; i++) {
			String str = RandomStringUtils.randomAlphanumeric(i);
			
			System.out.println("原文：" + str + "\t长度：" + str.length());

			// 加密数据
			String data = DataCoderUtil.getInstance().encrypt(str);
			System.out.println("加密后：" + data + "\t长度：" + data.length());
			list.add("原长度：" + str.length() + "，加密长度：" + data.length() + "，长度差：" + (data.length() - str.length()));
			
			// 解密数据
			data = DataCoderUtil.getInstance().decrypt(data);
			System.out.println("解密后：" + data);
			System.out.println("==========================");
		}
		
		for (String string : list) {
			System.out.println(string);
		}
	}

	public static void decryptTest(String filePath) throws InvalidFormatException, IOException {
		StopWatch sw = new StopWatch();
		sw.start();
		DataCoderUtil.getInstance().new ImportExcel(filePath).decryptAndSave();
		sw.stop();
		
		System.out.println("--------------------------------------------------------");
		System.out.printf("------------Service is started. cost:%s s---------------\n", sw.getTotalTimeSeconds());
		System.out.println("--------------------------------------------------------");
	}
}
