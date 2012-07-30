package jdk.research;

public class ClassTest {

	public static void main(String[] args) throws ClassNotFoundException {
		Class<Integer> intCls = Integer.class; //BootStrapClassLoader null
		Class<ClassTest> test = ClassTest.class;
		System.out.println(intCls.getName());
		System.out.println(test.getClassLoader());
		
		Class<?> cls = Class.forName("java.lang.String");
		System.out.println(cls.getName());
		
		System.out.println("isAnnotation--->"+intCls.isAnnotation());
		System.out.println("isAnonymousClass--->"+intCls.isAnonymousClass());
		System.out.println("isPrimitive--->"+intCls.isPrimitive());
		System.out.println("isArray--->"+intCls.isArray());
		System.out.println("isEnum--->"+intCls.isEnum());
		System.out.println("isInterface--->"+intCls.isInterface());
		System.out.println("isLocalClass--->"+intCls.isLocalClass());
		System.out.println("isMemberClass--->"+intCls.isMemberClass());
		System.out.println("isSynthetic--->"+intCls.isSynthetic());
	}

}
