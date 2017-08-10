package com.example.feild.encryption.extension.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对数据库字段内容加解密工具
 * 对称加密算法：基于口令加密-PBE算法实现
 * 使用java6提供的PBEWITHMD5andDES算法进行展示
 */
public final class DataCoderUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DataCoderUtil.class);
	
	private static final DataCoderUtil SINGLETON = new DataCoderUtil();

	/**
	 * JAVA6支持以下任意一种算法
	 * PBEWITHMD5ANDDES
	 * PBEWITHMD5ANDTRIPLEDES
	 * PBEWITHSHAANDDESEDE
	 * PBEWITHSHA1ANDRC2_40
	 * PBKDF2WITHHMACSHA1
	 */
	private static final String ALGORITHM = "PBEWithMD5AndDES";

	/**迭代次数*/
	private static final int ITERATION_COUNT = 100;

	/**编码格式*/
	private static final String CHARSET = "UTF-8";
	
	/**PBE密钥名称*/
	private static final String PBE_KEY_NAME = "DB_PBE_PWD";

	/**PBE加密的密钥*/
	private static Key PBE_KEY;
	
	private DataCoderUtil() {
		super();
		
		try {
			// 从环境变量获取
			String dbPbePwd = System.getenv(PBE_KEY_NAME);
			// 密钥彩礼转换
			PBEKeySpec keySpec = new PBEKeySpec(dbPbePwd.toCharArray());
			// 实例化
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			// 生成密钥
			PBE_KEY = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new RuntimeException("环境变量未设置秘钥DB_PBE_PWD");
		}
	}

	public static DataCoderUtil getInstance() {
		return SINGLETON;
	}

	/**
	 * 对数据进行加密
	 * @param value 加密的值
	 * @param saltSeed 盐值种子
	 * @return 加密后的字符串
	 */
	public String encrypt(String in) {
		try {
			// 数据byte[]
			byte[] input = in.getBytes(CHARSET);
			
			// 计算盐
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(input);
			in = DatatypeConverter.printHexBinary(md5.digest(input)).toLowerCase();
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < in.length(); i += 4) {
				sb.append(in.charAt(i));
			}
			byte[] salt = sb.toString().getBytes(CHARSET);

			// 加密数据
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, PBE_KEY, paramSpec);
			byte[] data =  cipher.doFinal(input);
			
			return sb.toString() + Base64.encodeBase64String(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对数据进行解密
	 * @param value 解密的值
	 * @param saltSeed 盐值种子
	 * @return 解密后的字符串，解密如果失败，返回原字符串
	 */
	public String decrypt(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		if (StringUtils.length(value) < 8) {
			return value;
		}

		try {
			// 先解密得到盐
			String saltEncrypt = StringUtils.substring(value, 0, 8);
			byte[] salt = saltEncrypt.getBytes(CHARSET);

			// base解密
			String dataEncrypt = StringUtils.substring(value, 8);
			byte[] data = Base64.decodeBase64(dataEncrypt);
			if (null == data || data.length == 0) {
				return value;
			}

			// PEB解密
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, PBE_KEY, paramSpec);
			data = cipher.doFinal(data);

			return new String(data, CHARSET);
		} catch (Exception e) {
			return value;
		}
	}
	
	public class ImportExcel {

		/**文件对象*/
		private File file;

		/**工作薄对象*/
		private Workbook wb;

		/**工作表对象*/
		private Sheet sheet;

		/**标题行号*/
		private int headerNum;

		/**
		 * 构造函数
		 * @param filePath 文件地址，读取第一个工作表，标题默认为第一行
		 * @throws InvalidFormatException 
		 * @throws IOException 
		 */
		public ImportExcel(String filePath) throws InvalidFormatException, IOException {
			this(filePath, 0, 0);
		}

		/**
		 * 构造函数
		 * @param filePath 文件地址
		 * @param headerNum 标题行号，数据行号=标题行号+1
		 * @param sheetIndex 工作表编号
		 * @throws InvalidFormatException 
		 * @throws IOException 
		 */
		private ImportExcel(String filePath, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
			this.file = new File(filePath);
			if (!this.file.exists()) {
				throw new RuntimeException("导入文档为空!");
			}
			
			InputStream is = new FileInputStream(this.file);
			String fileName = this.file.getName().toLowerCase();
			try {
				if (fileName.endsWith("xls")) {
					this.wb = new HSSFWorkbook(is);
				} else if (fileName.endsWith("xlsx")) {
					this.wb = new XSSFWorkbook(is);
				} else {
					throw new RuntimeException("文档格式不正确!");
				}
			} finally {
				is.close();
			}
			if (this.wb.getNumberOfSheets() < sheetIndex) {
				throw new RuntimeException("文档中没有工作表!");
			}
			this.sheet = this.wb.getSheetAt(sheetIndex);
			this.headerNum = headerNum;
			log.info("{} Initialize success.", fileName);
		}

		/**
		 * 获取行对象
		 * @param rownum
		 * @return
		 */
		public Row getRow(int rownum) {
			return this.sheet.getRow(rownum);
		}

		/**
		 * 获取数据行号
		 * @return
		 */
		public int getDataRowNum() {
			return headerNum + 1;
		}

		/**
		 * 获取最后一个数据行号
		 * @return
		 */
		public int getLastDataRowNum() {
			return this.sheet.getPhysicalNumberOfRows();
		}

		/**
		 * 获取最后一个列号
		 * @return
		 */
		public int getLastCellNum() {
			return this.getRow(headerNum).getLastCellNum();
		}

		/**
		 * 解密excel中存在的加密数据
		 * 1.只解密字符串类型
		 * 2.只解密excel第一个sheet表
		 * @author zhangli 2017年8月8日
		 */
		public void decryptAndSave() {
			// 数据解密
			for (int i = 1; i < this.getLastDataRowNum(); i++) {
				// 当前行
				Row row = this.sheet.getRow(i);
				
				// 解密数据
				for (int j = 1; j < this.getLastCellNum(); j++) {
					// 只解密字符串类型
					Cell cell = row.getCell(j);
					if (cell != null && CellType.STRING == cell.getCellTypeEnum()) {
						String souceValue = cell.getStringCellValue();// 源数据
						String treatedValue = SINGLETON.decrypt(souceValue);
						cell.setCellValue(treatedValue);
					}
				}
			}
			
			// 文件保存
			try {
				String newFilePath = file.getParent() + "\\(decrypt)" + file.getName();
				OutputStream os = new FileOutputStream(new File(newFilePath));
				this.wb.write(os);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				log.info("{} decrypt end.", file.getName());
			}
		}
	}
}