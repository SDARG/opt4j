package org.opt4j.core.config;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.inject.Binder;
import com.google.inject.Module;

public class PropertyTest {
	@Test
	public void getInfo() {
		String name = "test";
		Property p = new Property(null, null, null, null, null, Collections.<Annotation> emptySet());

		Assertions.assertEquals("", p.getInfo());
		p.setInfo(name);
		Assertions.assertEquals(name, p.getInfo());
	}

	@Test
	public void getOrder() {
		Property p = new Property(null, null, null, null, null, Collections.<Annotation> emptySet());

		p.setOrder(1);
		Assertions.assertEquals(1, p.getOrder());
	}

	@Test
	public void getName() {
		String name = "test";
		Property p = new Property(null, name, null, null, null, Collections.<Annotation> emptySet());

		Assertions.assertEquals(name, p.getName());
	}

	@Test
	public void getType() {
		Class<Boolean> type = Boolean.class;
		Property p = new Property(null, null, type, null, null, Collections.<Annotation> emptySet());

		Assertions.assertEquals(type, p.getType());
	}

	@Test
	public void getGetter() throws NoSuchMethodException, SecurityException {
		Method getter = PropertyTest.class.getMethod("getGetter");
		Property p = new Property(null, null, null, getter, null, Collections.<Annotation> emptySet());

		Assertions.assertEquals(getter, p.getGetter());
	}

	@Test
	public void getSetter() throws NoSuchMethodException, SecurityException {
		Method setter = PropertyTest.class.getMethod("getSetter");
		Property p = new Property(null, null, null, null, setter, Collections.<Annotation> emptySet());

		Assertions.assertEquals(setter, p.getSetter());
	}

	@Test
	public void addRequirement() {
		Requirement r = new Requirement() {

			@Override
			public boolean isFulfilled() {
				return true;
			}

			@Override
			public Property getProperty() {
				return null;
			}
		};

		Property p = new Property(null, null, null, null, null, Collections.<Annotation> emptySet());

		p.addRequirement(r);
		Assertions.assertEquals(1, p.getRequirements().size());
		Assertions.assertTrue(p.getRequirements().contains(r));
	}

	@Test
	public void isActive() {
		Requirement r = new Requirement() {

			@Override
			public boolean isFulfilled() {
				return true;
			}

			@Override
			public Property getProperty() {
				return null;
			}
		};

		Property p = new Property(null, null, null, null, null, Collections.<Annotation> emptySet());

		p.addRequirement(r);
		Assertions.assertTrue(p.isActive());
	}

	@Test
	public void isActiveFalse() {
		Requirement r = new Requirement() {

			@Override
			public boolean isFulfilled() {
				return false;
			}

			@Override
			public Property getProperty() {
				return null;
			}
		};

		Property p = new Property(null, null, null, null, null, Collections.<Annotation> emptySet());

		p.addRequirement(r);
		Assertions.assertFalse(p.isActive());
	}

	@Test
	public void getValue() throws NoSuchMethodException, SecurityException {
		Method getter = MyModuleMock.class.getMethod("getMock");
		Property p = new Property(new MyModuleMock(), null, null, getter, null, Collections.<Annotation> emptySet());

		Assertions.assertEquals(1234, p.getValue());
	}

	@Test
	public void setValue() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockInt", Integer.class);
		Property p = new Property(new MyModuleMock(), null, Integer.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue(4321);
		p.setValue("4321");
	}

	@Test
	public void setValueException() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		assertThrows(InvocationTargetException.class, () -> {
			Method setter = MyModuleMock.class.getMethod("setMockInt", Integer.class);
			Property p = new Property(new MyModuleMock(), null, Integer.TYPE, null, setter,
					Collections.<Annotation> emptySet());

			p.setValue(3.0d);
		});
	}

	@Test
	public void setValueExceptionString() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		assertThrows(InvocationTargetException.class, () -> {
			Method setter = MyModuleMock.class.getMethod("setMockInt", Integer.class);
			Property p = new Property(new MyModuleMock(), null, Integer.TYPE, null, setter,
					Collections.<Annotation> emptySet());

			p.setValue("3.0");
		});
	}

	@Test
	public void setValueLong() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockLong", Long.class);
		Property p = new Property(new MyModuleMock(), null, Long.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("43210000");
	}

	@Test
	public void setValueFloat() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockFloat", Float.class);
		Property p = new Property(new MyModuleMock(), null, Float.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("3.0");
	}

	@Test
	public void setValueDouble() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockDouble", Double.class);
		Property p = new Property(new MyModuleMock(), null, Double.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("1234.1234");
	}

	@Test
	public void setValueBoolean() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockBoolean", Boolean.class);
		Property p = new Property(new MyModuleMock(), null, Boolean.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("true");
	}

	@Test
	public void setValueByte() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockByte", Byte.class);
		Property p = new Property(new MyModuleMock(), null, Byte.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("-2");
	}

	@Test
	public void setValueShort() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockShort", Short.class);
		Property p = new Property(new MyModuleMock(), null, Short.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("250");
	}

	@Test
	public void setValueString() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockString", String.class);
		Property p = new Property(new MyModuleMock(), null, String.class, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("abc");
	}

	@Test
	public void setValueStringNull() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockStringNull", String.class);
		Property p = new Property(new MyModuleMock(), null, String.class, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue(null);
	}

	@Test
	public void setValueChar() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockChar", Character.TYPE);
		Property p = new Property(new MyModuleMock(), null, Character.TYPE, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("c");
	}

	@Test
	public void setValueCharacter() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockChar", Character.TYPE);
		Property p = new Property(new MyModuleMock(), null, Character.class, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("c");
	}

	@Test
	public void setValueEnum() throws NoSuchMethodException, SecurityException, InvocationTargetException {
		Method setter = MyModuleMock.class.getMethod("setMockEnum", MyEnum.class);
		Property p = new Property(new MyModuleMock(), null, MyEnum.class, null, setter,
				Collections.<Annotation> emptySet());

		p.setValue("X");
	}

	enum MyEnum {
		X, Y
	}

	public static class MyModuleMock implements Module {
		public int getMock() {
			return 1234;
		}

		public void setMockInt(Integer i) {
			Assertions.assertEquals(4321, i.intValue());
		}

		public void setMockLong(Long i) {
			Assertions.assertEquals(43210000l, i.longValue());
		}

		public void setMockFloat(Float i) {
			Assertions.assertEquals(3.0f, i, 0.0000001);
		}

		public void setMockDouble(Double i) {
			Assertions.assertEquals(1234.1234f, i, 0.001);
		}

		public void setMockBoolean(Boolean i) {
			Assertions.assertTrue(i);
		}

		public void setMockByte(Byte i) {
			Assertions.assertTrue(-2 == i);
		}

		public void setMockShort(Short i) {
			Assertions.assertTrue(250 == i);
		}

		public void setMockStringNull(String i) {
			Assertions.assertEquals("", i);
		}

		public void setMockString(String i) {
			Assertions.assertEquals("abc", i);
		}

		public void setMockChar(char i) {
			Assertions.assertEquals('c', i);
		}

		public void setMockEnum(MyEnum i) {
			Assertions.assertEquals(MyEnum.X, i);
		}

		@Override
		public void configure(Binder binder) {
			// nothing to be done
		}
	}

	@Test
	public void isNumberChar() {
		Property p = new Property(new MyModuleMock(), null, Character.TYPE, null, null,
				Collections.<Annotation> emptySet());

		Assertions.assertFalse(p.isNumber());
	}

	@Test
	public void isNumberInt() {
		Property p = new Property(new MyModuleMock(), null, Integer.TYPE, null, null,
				Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void isNumberLong() {
		Property p = new Property(new MyModuleMock(), null, Long.TYPE, null, null, Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void isNumberDouble() {
		Property p = new Property(new MyModuleMock(), null, Double.TYPE, null, null,
				Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void isNumberFloat() {
		Property p = new Property(new MyModuleMock(), null, Float.TYPE, null, null,
				Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void isNumberByte() {
		Property p = new Property(new MyModuleMock(), null, Byte.TYPE, null, null, Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void isNumberShort() {
		Property p = new Property(new MyModuleMock(), null, Short.TYPE, null, null,
				Collections.<Annotation> emptySet());

		Assertions.assertTrue(p.isNumber());
	}

	@Test
	public void getAnnotations() {
		Property p = new Property(new MyModuleMock(), null, null, null, null, Collections.<Annotation> emptySet());
		Assertions.assertTrue(p.getAnnotations().isEmpty());
	}
}
