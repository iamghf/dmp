package com.ghf.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * beanתxmlʱ�����жϻ��������Ƿ�Ϊ��
 * @author ghf
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlNullValue {
	String value();//��ֵ
}
