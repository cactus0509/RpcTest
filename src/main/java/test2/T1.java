package test2;

import java.util.HashMap;
class CA {
	private int id = 2 ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
public class T1 {
	public static void main(String args[]) {
		HashMap a = new HashMap();
		CA a1 = new CA();
		a1.setId(2);
		a.put("a", a); 
		
		Class<?> b = (Class<?>) a.get("a");
		
		System.out.println(b.getFields());
	}
}
