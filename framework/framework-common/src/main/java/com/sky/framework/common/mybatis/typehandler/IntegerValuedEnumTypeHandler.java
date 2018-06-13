package com.sky.framework.common.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.sky.framework.common.mybatis.IntegerValuedEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class IntegerValuedEnumTypeHandler<T extends Enum<T> & IntegerValuedEnum> implements TypeHandler<T> {

    private Class<T> type;

    public IntegerValuedEnumTypeHandler(Class<T> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }
    
    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if( parameter == null){
            ps.setNull(i, Types.INTEGER);
        }else if (jdbcType == null) {
            ps.setInt(i, parameter.getValue());
          } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE); // see r3589
          }
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if( value == null ){
            return null;
        }else{
             return IntegerValuedEnumReflect.valueToEnum(type, Integer.parseInt(value.toString()), true);
        }
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex);
        if( value == null ){
            return null;
        }else{
             return IntegerValuedEnumReflect.valueToEnum(type, Integer.parseInt(value.toString()), true);
        }
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getObject(columnIndex);
        if( value == null ){
            return null;
        }else{
             return IntegerValuedEnumReflect.valueToEnum(type, Integer.parseInt(value.toString()), true);
        }
    }
}
