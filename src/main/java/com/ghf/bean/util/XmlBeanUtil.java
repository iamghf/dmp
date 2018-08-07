package com.ghf.bean.util;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.bean.annotation.XmlNullValue;
import com.ghf.bean.annotation.XmlTagIgnore;
import com.ghf.bean.annotation.XmlTagName;

/**
 * @author ghf
 *
 */
public class XmlBeanUtil {
	private static final transient Logger log = LoggerFactory.getLogger(XmlBeanUtil.class);
	private static final String PREFIX = "<";
	private static final String SUFFIX = ">";
	private static final String SLASH = "/";
	private static final String NULLSTR = "";
	public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static <T> String bean2Xml(T bean, boolean isParent) throws Exception {
		if (null == bean) {
			return null;
		}
		StringBuilder root = new StringBuilder(1024);
		StringBuilder sub = new StringBuilder(1024);
		Class<?> clazz = bean.getClass();
		if (!isPojo(clazz)) {
			return bean.toString();
		}

		String rootTag = getTagName(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String tagName = getTagName(field); // 标签名
			Object value = field.get(bean); // 标签值
			if (null != value) {
				if (value.getClass().isArray()) {
					// list
					// Class elementType = value.getClass().getComponentType();
					int len = Array.getLength(value);
					for (int i = 0; i < len; i++) {
						sub.append(createPreXmlTag(tagName));
						Object subObj = Array.get(value, i);
						sub.append(bean2Xml(subObj, false));
						sub.append(createSufXmlTag(tagName));
					}
				} else {
					sub.append(createPreXmlTag(tagName));
					sub.append(isPojo(field.getType()) ? bean2Xml(value, false) : value);
					sub.append(createSufXmlTag(tagName));
				}
			}
		}
		return isParent ? root.append(buildTagBySimpleType(rootTag, sub.toString())).toString() : sub.toString();
	}

	/**
	 * Javabean转xml 空值也会生成相应的标签
	 * 
	 * @param clazz
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static <T> String bean2Xml(Class<?> clazz, T bean) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("req bean is : " + (null != bean ? bean.toString() : ""));
		}
		return bean2Xml(clazz, bean, true, null, false);
	}

	@SuppressWarnings("unchecked")
	private static <T> String bean2Xml(Class<?> clazz, T bean, boolean isParent, String tagName, boolean ignore) throws Exception {
		StringBuilder root = new StringBuilder(1024);
		StringBuilder sub = new StringBuilder(1024);
		if (ignore) {
			return NULLSTR;
		}
		String rootTag = getTagName(clazz);
		rootTag = StringUtils.isNotBlank(rootTag) ? rootTag : tagName;
		// log.debug("rootTag is " + rootTag);
		if (clazz.isArray()) {
			// 数组
			Class<?> elementType = clazz.getComponentType();
			int len = isEmpty(bean) ? 0 : Array.getLength(bean);
			if (0 == len) {
				len = 1;
				bean = (T) Array.newInstance(elementType, len);
			}
			for (int i = 0; i < len; i++) {
				sub.append(bean2Xml(elementType, Array.get(bean, i), isPojo(elementType), rootTag, false));
			}
		} else if (isPojo(clazz)) {
			// pojo
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String subTagName = getTagName(field); // 标签名
				// log.debug("subTagName is " + subTagName);
				Object value = isEmpty(bean) ? NULLSTR : field.get(bean); // 标签值
				value = isNull(field, value) ? NULLSTR : value;
				sub.append(bean2Xml(field.getType(), value, isPojo(field.getType()), subTagName, ignore(field)));
			}
		} else {
			// 基本类型 or 基本类型包装类
			sub.append(buildTagBySimpleType(rootTag, Object2String(bean)));
		}
		return isParent ? root.append(buildTagBySimpleType(rootTag, sub.toString())).toString() : sub.toString();
	}

	public static boolean isEmpty(Object bean) {
		return null == bean || NULLSTR.equals(bean);
	}

	private static <T> String Object2String(T bean) {
		return null != bean ? bean.toString() : NULLSTR;
	}

	private static boolean ignore(Field field) {
		return field.isAnnotationPresent(XmlTagIgnore.class);
	}

	private static boolean isNull(Field field, Object value) {
		return field.isAnnotationPresent(XmlNullValue.class) ? StringUtils.equals(Object2String(value), field.getAnnotation(XmlNullValue.class).value())
				: StringUtils.isBlank(Object2String(value));
	}

	private static String buildTagBySimpleType(String tagName, String tagValue) {
		StringBuilder sb = new StringBuilder(512);
		return sb.append(createPreXmlTag(tagName)).append(tagValue).append(createSufXmlTag(tagName)).toString();
	}

	/**
	 * 判断是否为javabean 是返还true
	 * 
	 * @return
	 */
	public static boolean isPojo(Class<?> clazz) {
		if (String.class == clazz) {
			return false;
		} else if (Integer.class == clazz) {
			return false;
		} else if (Long.class == clazz) {
			return false;
		} else if (Byte.class == clazz) {
			return false;
		} else if (Short.class == clazz) {
			return false;
		} else if (Float.class == clazz) {
			return false;
		} else if (Double.class == clazz) {
			return false;
		} else if (Boolean.class == clazz) {
			return false;
		} else if (Character.class == clazz) {
			return false;
		} else if (clazz.isPrimitive()) {
			return false;
		}
		return true;
	}

	private static Object getValue(Class<?> clazz, String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}
		if (Long.TYPE == clazz) {
			return new Long(value);
		} else if (Integer.TYPE == clazz) {
			return new Integer(value);
		} else if (Byte.TYPE == clazz) {
			return new Byte(value);
		} else if (Short.TYPE == clazz) {
			return new Short(value);
		} else if (Float.TYPE == clazz) {
			return new Float(value);
		} else if (Boolean.TYPE == clazz) {
			return new Boolean(value);
		} else if (Character.TYPE == clazz) {
			return new Character(value.charAt(0));
		}
		return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T xml2Bean(Class<T> clazz, String xml) throws Exception {
		String xmlUtf8 = getUtf8StrFromGBKStr(xml);
		InputStreamReader utf8In = new InputStreamReader(new  ByteArrayInputStream(xmlUtf8.getBytes("UTF-8")), "UTF-8");
		Document document = new SAXReader().read(utf8In);
		Element ele = document.getRootElement();
		if (!isPojo(clazz)) {
			return (T) getValue(clazz, ele.getText());
		}
		T bean = clazz.newInstance();

		Field[] fields = clazz.getDeclaredFields();
		Element tmp = null;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getType().isArray()) {// 判断是否为数组
				List<Element> list = ele.selectNodes(getTagName(field));
				int len = null != list ? list.size() : 0;
				Object objArr = Array.newInstance(field.getType().getComponentType(), len);
				for (int i = 0; i < len; i++) {
					// field.set(objArr,
					// xml2Bean(field.getType().getComponentType(),
					// list.get(i).asXML()));
					// String数组需要特殊处理
					Array.set(objArr, i, xml2Bean(String.class != field.getType() ? (Class<T>) field.getType().getComponentType() : (Class<T>) field.getType(), list.get(i).asXML()));
				}
				field.set(bean, objArr);
			} else {
				tmp = ele.element(getTagName(field));
				if (hasChildNode(tmp)) {
					// 如果还有下级结构 说明是个pojo 不是基本类型
					if (log.isDebugEnabled()) {
						log.debug(tmp.asXML());
					}
					field.set(bean, xml2Bean(field.getType(), tmp.asXML()));
				} else {
					String value = tmp != null ? tmp.getText() : null;
					if (StringUtils.isNotBlank(value)) {
						if (field.getType().isPrimitive()) {
							field.set(bean, getValue(field.getType(), value));
						} else {
							Constructor con = field.getType().getConstructor(String.class);
							field.set(bean, con.newInstance(value));
							// field.set(bean, value);
						}
					}
				}
			}
		}
		return bean;
	}

	private static boolean hasChildNode(Element ele) {
		if (null == ele) {
			return false;
		}
		return ele.elementIterator().hasNext();
	}

	private static String getTagName(Class<?> clazz) {
		if (clazz.isAnnotationPresent(XmlTagName.class)) {
			return ((XmlTagName) clazz.getAnnotation(XmlTagName.class)).value();
		}
		return null;
	}

	public static String getTagName(Field field) {
		String tagName = getTagNameByAnnotation(field);
		return StringUtils.isNotBlank(tagName) ? tagName : getTagNameByField(field);
	}

	private static String getTagNameByAnnotation(Field field) {
		return field.isAnnotationPresent(XmlTagName.class) ? field.getAnnotation(XmlTagName.class).value() : null;
	}

	private static String getTagNameByField(Field field) {
		String fieldName = field.getName();
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 生成xml开始标签
	 * 
	 * @param tagName
	 * @return
	 */
	private static String createPreXmlTag(String tagName) {
		return new StringBuilder(56).append(PREFIX).append(tagName).append(SUFFIX).toString();
	}

	/**
	 * 生成xml结束标签
	 * 
	 * @param tagName
	 * @return
	 */
	private static String createSufXmlTag(String tagName) {
		return new StringBuilder(56).append(PREFIX).append(SLASH).append(tagName).append(SUFFIX).toString();
	}

	private static String getUtf8StrFromGBKStr(String gbk) throws Exception {
		int n = gbk.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbk.charAt(i);
			if (m < 128 && m >= 0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return new String(tmp, "UTF-8");
		}
		return new String(utfBytes, "UTF-8");
	}

}
