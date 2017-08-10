package com.example.feild.encryption.extension;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.feild.encryption.domain.em.IntEnumInter;
import com.example.feild.encryption.extension.util.EnumUtil;

public class MybatisEnumHandler<E extends Enum<E> & IntEnumInter<E>> extends BaseTypeHandler<E> {

    private final E[] enums;
    private Class<E> type;

    public MybatisEnumHandler(Class<E> type) {
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.intValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getEnumByValue(value, type);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by " +
                        "ordinal value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getEnumByValue(value, type);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by " +
                        "ordinal value.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getEnumByValue(value, type);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by " +
                        "ordinal value.", ex);
            }
        }
    }
}
