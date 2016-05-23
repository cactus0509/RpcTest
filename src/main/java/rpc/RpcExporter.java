package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RpcExporter {
	static Executor exec = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());
	
	
	public static void exporter (String host, int port) throws IOException {
		
		ServerSocket server= new ServerSocket();
		server.bind(new InetSocketAddress(host,port));
		try{
			while( true ) {
				exec.execute( new ExporterTask(  server.accept()   ));
			}
		}catch(Exception e) {
			e.printStackTrace( );
		}finally {
			server.close();
		}
		
	}
	
	private static class ExporterTask implements Runnable {
		
		Socket client = null;
		public ExporterTask ( Socket sc) {
			this.client = sc;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			
			try{
				input = new ObjectInputStream(client.getInputStream()) ;
				String interfaceName = input.readUTF();
				Class <?> service = Class.forName(  interfaceName  );
				String methodName = input.readUTF();
				Class<?> parameterTypes = (Class<?>) input.readObject();
				Object[] arguments =  (Object[]) input.readObject();
				Method method = service.getMethod(methodName, parameterTypes);
				
				Object result = method.invoke(   service.newInstance() , arguments );
				output = new ObjectOutputStream(client.getOutputStream());
				output.writeObject( result );
			}catch(Exception e) {
				e.getMessage();
			}finally {
				try{
					if (output != null ) {
						output.close();
					}
					if (input != null ) {
						input.close();
					}
					if (client != null ) {
						client.close();
					} 
				}catch(Exception e ) {
					e.printStackTrace();
				}
				
				
			}
		}
		
	}
}
