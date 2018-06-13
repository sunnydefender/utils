package com.sky.framework.test;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MainConfig.class)
@ContextConfiguration(locations = { "classpath:application.xml" })
public class SpringTestCase {
	
	/**
	 * 获取代理类的实际对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	public static Object getTargetObject(Object proxy) throws Exception {

		if (!AopUtils.isAopProxy(proxy)) { // 判断是否是代理类
			return proxy;
		}

		return getTargetObject(getProxyTargetObject(proxy));

	}

	private static Object getProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);
		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
	}

	
/*	public static <T> void injectMocks(T t,Object object, Map<String, Object> mockFields, Logger LOGGER) throws Exception {
		// 若果T是代理类,@InjectMocks不会生效
		T impl = (T) getTargetObject(object);

		for (Map.Entry<String, Object> entry : mockFields.entrySet()) {
			LOGGER.debug("字段名:{}, mock:{}", entry.getKey(), entry.getValue());
			Field field = T.class.getDeclaredField(entry.getKey());
			field.setAccessible(true);
			field.set(impl, entry.getValue());
		}
	
	}
*/}
