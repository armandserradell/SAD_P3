import java.io.*;
import java.net.*;




public class MySocket implements AutoCloseable{

	private Socket socket;
	private BufferedReader bufferedReader; 
	private PrintWriter printWriter;
	

	public MySocket(String hostAddress, int hostPort){
		try{ 
			
			this.socket = new Socket(hostAddress, hostPort);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true); //Activem l'autoflush
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket(Socket s){
		try{
			this.socket = s;
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(String text){
		printWriter.println(text);
	}


	public String readLine(){
	
		String text = null;
		try{
			text = bufferedReader.readLine();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return text;
	}


	public void close(){
	
		try{
			bufferedReader.close();
			printWriter.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}