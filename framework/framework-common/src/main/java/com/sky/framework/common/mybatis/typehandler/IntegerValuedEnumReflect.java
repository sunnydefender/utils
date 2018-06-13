package com.sky.framework.common.mybatis.typehandler;

import com.sky.framework.common.mybatis.IntegerValuedEnum;
import org.omg.CORBA.SystemException;

/**
 * Utility class designed to inspect IntegerValuedEnums.
 */
public final class IntegerValuedEnumReflect{

    /**
     * Don't let anyone instantiate this class.
     * 
     * @throws UnsupportedOperationException
     *             Always.
     */
    private IntegerValuedEnumReflect(){
        throw new UnsupportedOperationException("This class must not be instanciated.");
    }

    /**
     * All Enum constants (instances) declared in the specified class.
     * 
     * @param enumClass
     *            Class to reflect
     * @return Array of all declared EnumConstants (instances).
     */
    private static <T extends Enum<T>>T[] getValues(Class<T> enumClass){
        return enumClass.getEnumConstants();
    }

    /**
     * All possible string values of the string valued enum.
     * 
     * @param enumClass
     *            Class to reflect.
     * @return Available integer values.
     */
    public static <T extends Enum<T> & IntegerValuedEnum>int[] getStringValues(Class<T> enumClass){
        T[] values = getValues(enumClass);
        int[] result = new int[values.length];
        for(int i = 0; i < values.length; i++){
            result[i] = values[i].getValue();
        }
        return result;
    }

    /**
     * Name of the enum instance which hold the respecified int value. If value
     * has duplicate enum instances than returns the first occurrence.
     * 
     * @param enumClass
     *            Class to inspect.
     * @param value
     *            The int value.
     * @return name of the enum instance.
     */
    public static <T extends Enum<T> & IntegerValuedEnum>String getNameFromValue(Class<T> enumClass, int value){
        T[] values = getValues(enumClass);
        for(int i = 0; i < values.length; i++){
            if(values[i].getValue() == value){
                return values[i].name();
            }
        }
        return "";
    }
    
    /**
     * 获取字符串常量代码对应的枚举值
     * 
     * @param <T>
     *            枚举类
     * @param t
     *            枚举类实例
     * @param val
     *            常量代码
     * @param emptyEnable
     *            是否允许为空
     * @return 常量代码对应的枚举值
     * @throws SystemException
     *             如果没有对应的枚举值
     */
    public static final <T extends Enum<T> & IntegerValuedEnum>T valueToEnum(Class<T> t, int value,
                                                                            boolean emptyEnable){
//        if(emptyEnable && value < 0){
//            return null;
//        }

        String name = getNameFromValue(t, value);
        try{
            T type = Enum.valueOf(t, name);
            return type;
        }catch(Exception e){
            throw new RuntimeException("枚举值不合法"); // 枚举{0}值不合法：{1}
        }
    }
}
