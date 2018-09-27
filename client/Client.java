package client;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import java.util.Arrays;
import java.nio.file.Files;

import client.services.ClientDropBox;
import client.services.ClientEncryptionDecryption;
import client.services.ClientSticksGame;

public class Client{

	public static int evaluate(String inp , Socket s, BufferedReader r , PrintWriter out , BufferedReader in){

		String service = "";

		try{
			switch(Integer.parseInt(inp)){

				case 1:
						service = "Encryption and decryption";
						ClientEncryptionDecryption c = new ClientEncryptionDecryption();
						c.clientFileEncryption(s,r,out,in);
						break;
			
				case 2:	
						service = "Sticks Game";
						ClientSticksGame cst = new ClientSticksGame();
						cst.clientSticksGame(s,r,out,in);
						break;
						
				case 3:	
						service = "DropBox";
						ClientDropBox d = new ClientDropBox();
						d.clientDropBox(s,r,out,in);
						break;
			}
		}
		catch(FileNotFoundException e){
			System.out.println("FileNotFoundException occcured in "+ service + " service. Closing socket.");
			return 0;
		}
		catch(IOException e){
			System.out.println("IOException occcured in "+ service + " service. Closing socket.");
			return 0;
		}
		catch(NumberFormatException e){
			System.out.println("NumberFormatException occcured in "+ service + " service. Closing socket.");
			return 0;
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException occcured in "+ service + " service. Closing socket.");
			return 0;
		}
		catch(SecurityException e){
			System.out.println("SecurityException occcured in "+ service + " service. Closing socket.");
			return 0;
		}

		return 1;

	}

	public static void main(String[] args) {
		
		String host = "";
		System.out.println("Enter the IP address of server");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
			host = in.readLine();
		}
		catch(IOException e){
			System.out.println("IOException occured");
		}

		String resp = null;

		try{
			InetAddress server = InetAddress.getByName(host);
			Socket s = new Socket(server,2212);

			BufferedReader r = new BufferedReader( new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter( new OutputStreamWriter(s.getOutputStream()) , true);
			
			String choice = "a";
			int ret = 0;
	
			while(!choice.equals("4")){

				while(!(resp = r.readLine()).equals("-EOF-")){			
					System.out.println(resp);
				}

				choice = in.readLine();
				
				out.println(choice);
				out.flush();
				ret = evaluate(choice , s , r, out ,in);
				
				if(ret == 0){
					s.close();
					System.out.println("Reopening the socket");
					s = new Socket(server , 2212);
					r = new BufferedReader( new InputStreamReader(s.getInputStream()));
					out = new PrintWriter( new OutputStreamWriter(s.getOutputStream()) , true);
				}

				out.flush();
			}

			s.close();
			out.close();
			r.close();

		}catch(IOException e){
			System.out.println("IO Exception occured" );
		}
	}
}