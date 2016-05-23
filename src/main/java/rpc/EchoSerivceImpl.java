package rpc;
public class EchoSerivceImpl  implements EchoService{
	public String echo (String ping) {
		return "this is a ping:";
	}
	public String echo2  () { return "hi" ; }  
	
}
