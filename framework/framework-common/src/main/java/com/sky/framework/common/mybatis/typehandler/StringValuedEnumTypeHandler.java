package com.sky.framework.common.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.sky.framework.common.mybatis.StringValuedEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class StringValuedEnumTypeHandler<T extends Enum<T> & StringValuedEnum> implements TypeHandler<T> {

    private Class<T> type;

    public StringValuedEnumTypeHandler(Class<T> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if( parameter == null ){
            ps.setNull(i, Types.CHAR);
        }else if (jdbcType == null) {
            ps.setString(i, parameter.getValue());
        } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE); // see r3589
        }        
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        if( StringUtils.isEmpty(s) )
            return null;
        else
            return StringValuedEnumReflect.valueToEnum(type, s, true);
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        if( StringUtils.isEmpty(s) )
            return null;
        else
            return StringValuedEnumReflect.valueToEnum(type, s, true);
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        if( StringUtils.isEmpty(s) )
            return null;
        else
            return StringValuedEnumReflect.valueToEnum(type, s, true);
    }

}
