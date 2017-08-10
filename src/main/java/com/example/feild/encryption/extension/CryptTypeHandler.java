package com.example.feild.encryption.extension;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.feild.encryption.extension.util.DataCoderUtil;

/**
 * 数据加解密转换器
 * @author zhangli 2017年8月10日
 */
public class CryptTypeHandler extends BaseTypeHandler<String> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, DataCoderUtil.getInstance().encrypt(parameter));
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
        	String value = rs.getString(columnName);
        	return DataCoderUtil.getInstance().decrypt(value);
        }
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if (rs.wasNull()) {
            return null;
        } else {
        	String value = rs.getString(columnIndex);
        	return DataCoderUtil.getInstance().decrypt(value);
        }
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if (cs.wasNull()) {
			return null;
		} else {
			String value = cs.getString(columnIndex);
			return DataCoderUtil.getInstance().decrypt(value);
		}
	}
}
