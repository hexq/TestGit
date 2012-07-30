package jdk.research;

/**
 * Object¿‡—–æø
 * 
 * @author hexq
 *
 */
public class ObjectTest {
	
	public static void main(String[] args) {
		Object obj = new Object();
		int hashCode = obj.hashCode();
		System.out.println(hashCode);
		System.out.println(Integer.toHexString(hashCode));
		System.out.println(obj);
		
		Class cls = obj.getClass();
		System.out.println(cls.getName());
		System.out.println(cls.getCanonicalName());
		System.out.println(cls.getSimpleName());
	}

}
