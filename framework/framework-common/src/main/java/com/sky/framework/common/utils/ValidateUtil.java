package com.sky.framework.common.utils;

import java.util.Set;

import javax.validation.*;

public class ValidateUtil {
	public static void validate(Object param) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<Object>> set = validator.validate(param);
        
        if (set.size() == 0) {
        	return;
        }
        
        StringBuffer errorMessage = new StringBuffer();
        for (ConstraintViolation<Object> c: set) {
        	errorMessage.append(c.getRootBeanClass().getName());
        	errorMessage.append(".");
        	errorMessage.append(c.getPropertyPath());
        	errorMessage.append("校验失败,");
        	errorMessage.append(c.getMessage());
        	errorMessage.append(".输入为: ");
        	errorMessage.append(c.getInvalidValue());
        	errorMessage.append("\n");
        }
        throw new ValidationException(errorMessage.toString());
	}
}
