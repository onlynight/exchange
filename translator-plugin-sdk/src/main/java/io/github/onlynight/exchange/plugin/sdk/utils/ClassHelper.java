package io.github.onlynight.exchange.plugin.sdk.utils;

import java.lang.reflect.*;

public class ClassHelper {

	public static Object createHandler(Class tempClass) {

		try {
			Type superClass = tempClass.getGenericSuperclass();
			Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
			Class<?> clazz = ClassHelper.getRawType(type);
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// type不能直接实例化对象，通过type获取class的类型，然后实例化对象
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type rawType = parameterizedType.getRawType();
			return (Class) rawType;
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			return Array.newInstance(getRawType(componentType), 0).getClass();
		} else if (type instanceof TypeVariable) {
			return Object.class;
		} else if (type instanceof WildcardType) {
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		} else {
			String className = type == null ? "null" : type.getClass().getName();
			throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
		}
	}

}
