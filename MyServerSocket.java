import java.io.*;
import java.net.*;



public class MyServerSocket implements AutoCloseable{

	private ServerSocket serverSocket;

	public MyServerSocket(int port){
		try{
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server is listening on port <" + port + ">");
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket accept(){
		try{
			return new MySocket(serverSocket.accept());
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}


	public void close(){
		try{
			this.serverSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}

