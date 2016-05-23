package rpc;

import java.io.IOException
;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcImport<S> {
	@SuppressWarnings("unchecked")
	public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
		return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(),
				new Class<?>[] { serviceClass.getInterfaces()[0] },
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						Socket socket = null;
						ObjectInputStream input = null;
						ObjectOutputStream output = null;
						try{
							System.out.println("comint");
							socket = new Socket();
							socket.connect(addr);
							System.out.println("1");
							output = new ObjectOutputStream(socket.getOutputStream());
							output.writeUTF( serviceClass.getName());
							output.writeUTF(method.getName());
							output.writeObject(method.getParameterTypes());
							System.out.println("2");
							output.writeObject(args);
							input = new ObjectInputStream(socket.getInputStream()); 
							System.out.println("3");
							return input.readObject();
						}catch(Exception e ) {
							e.printStackTrace();
						} finally {
							try{
								
//								if (socket !=null) {
//									socket.close();
//								}
//
//								if (output !=null) {
//									output.close();
//								}

//								if (input !=null) {
//									input.close();
//								}
							
								 
							}catch(Exception e ) {
								e.printStackTrace();
							}
						}
						return input.readObject();
					}
				}

		 );
	}
	
	
public static void main ( String args [] ) throws IOException  {
	
	new Thread ( new Runnable() {
		 public void run()
		 {
			 try {
				System.out.println("start server");
				RpcExporter.exporter("localhost", 8888);
				System.out.println("start server ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
	}).start();
	;
	RpcImport <EchoService>  importer  = new RpcImport <EchoService> ();
	 
	EchoService echo = importer.importer(EchoSerivceImpl.class,  new InetSocketAddress ("localhost",8888) );
	System.out.println(echo.echo("123"));
}
}
