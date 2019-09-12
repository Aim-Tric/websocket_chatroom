package utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * 队列属性复制
 * 
 * @author wujing
 * @param <T>
 */
public final class BeanUtil<T> {

	private BeanUtil() {
	}

	public static <T> T copyProperties(Object source, Class<T> clazz) {
		if (source == null) {
			return null;
		}
		T t = null;
		try {
			t = clazz.newInstance();
			BeanUtils.copyProperties(source, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> List<T> copyProperties(List<?> source, Class<T> clazz) {
		if (source == null || source.size() == 0) {
			return Collections.emptyList();
		}
		List<T> res = new ArrayList<>(source.size());
		for (Object o : source) {
			T t = null;
			try {
				t = clazz.newInstance();
				BeanUtils.copyProperties(o, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res.add(t);
		}
		return res;
	}

}
