package cn.newtouch.fdpp.console.utils;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class BeanUtils {
	
	private static final Hashtable<String, Field> FIELDS = new Hashtable<String, Field>();
	
	public static Object get(Object target, String fieldName) throws Exception {
		Class<?> clazz = target.getClass();
		String key = clazz.getName() + '.' + fieldName;
		Field  field = FIELDS.get(key);
		if (field == null) {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			FIELDS.put(key, field);
		}
		return field.get(target);
	}
	
	public static void set(Object target, String fieldName, Object value) throws Exception {
		Class<?> clazz = target.getClass();
		String key = clazz.getName() + '.' + fieldName;
		Field  field = FIELDS.get(key);
		if (field == null) {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			FIELDS.put(key, field);
		}
		field.set(target, value);
	}
	
	/*class Entry {
		
		public Map<String, Field> fields;
		
	}*/

}
