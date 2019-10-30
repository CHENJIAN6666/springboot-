package com.newlife.s4.common.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class UniversalEnumConverterFactory implements ConverterFactory<String, IBaseEnum> {
	private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

	@Override
	public <T extends IBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		Converter result = converterMap.get(targetType);
		if (result == null) {
			result = new IntegerStrToEnum<T>(targetType);
			converterMap.put(targetType, result);
		}
		return result;
	}

	class IntegerStrToEnum<T extends IBaseEnum> implements Converter<String, T> {
		private final Class<T> enumType;
		private Map<String, T> enumMap = new HashMap<>();

		public IntegerStrToEnum(Class<T> enumType) {
			this.enumType = enumType;
			T[] enums = enumType.getEnumConstants();
			for (T e : enums) {
				
				enumMap.put(e.getId() + "", e);
				if(e.getName()!=null) {
					enumMap.put(e.getName(), e);
				}
				
			}
		}

		@Override
		public T convert(String source) {
			T result = enumMap.get(source);
			if (result == null) {
				throw new IllegalArgumentException("No element matches " + source);
			}
			return result;
		}
	}
}
