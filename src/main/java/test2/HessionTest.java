package test2;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
 

public class HessionTest {

	public void run() {
		TestBean a = new TestBean();
		a.setId(1);
		a.setName("myname");
		try{ 
			byte[] s = serialize( a ) ;
			TestBean b = (TestBean)deserialize(s);
			System.out.println(b.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HessionTest t = new HessionTest();
		t.run();
		
	}
	
	 
    public byte[] serialize(Object obj) throws IOException{  
        if(obj==null) throw new NullPointerException();  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        HessianOutput ho = new HessianOutput(os);  
        ho.writeObject(obj);  
        return os.toByteArray();  
    }  
      
    public Object deserialize(byte[] by) throws IOException{  
        if(by==null) throw new NullPointerException();  
          
        ByteArrayInputStream is = new ByteArrayInputStream(by);  
        HessianInput hi = new HessianInput(is);  
        return hi.readObject();  
    }  
}
