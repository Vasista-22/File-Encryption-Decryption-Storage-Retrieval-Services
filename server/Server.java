package server;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Random;
import java.nio.file.Files;

import server.services.ServerDropBox;
import server.services.ServerSticksGame;
import server.services.ServerEncryptionDecryption;

public class Server{

	public static int evaluate(String inp , Socket s , BufferedReader r , PrintWriter out){

		String service = "";

		try{
			switch(Integer.parseInt(inp)){

				case 1:	
						service = "Encryption and Decryption";
						System.out.println("File Encryption decryption running");
						ServerEncryptionDecryption e = new ServerEncryptionDecryption();
						e.fileEncryptionAndDecryption(s,r,out);
						break;
				case 2:	
						service = "Sticks game";
						System.out.println("21 sticks game service running");
						ServerSticksGame st = new ServerSticksGame();
						st.sticksGame(s , r , out);
						break;

				case 3:
						service = "Drop Box";
						System.out.println("DropBox Service running");
						ServerDropBox d = new ServerDropBox();
						d.dropBox(s,r,out);
						break;
			}
		}
		catch(AEADBadTagException e){
			System.out.println("AEADBadTagException occcured in "+ service + " service");
			return 0;
		}
		catch(FileNotFoundException e){
			System.out.println("FileNotFoundException occcured in "+ service + " service");
			return 0;
		}
		catch(IOException e){
			System.out.println("IOException occcured in "+ service + " service");
			return 0;
		}
		catch(NumberFormatException e){
			System.out.println("NumberFormatException occcured in "+ service + " service");
			return 0;
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException occcured in "+ service + " service");
			return 0;
		}
		catch(SecurityException e){
			System.out.println("SecurityException occcured in "+ service + " service");
			return 0;
		}
		catch(IllegalArgumentException e){
			System.out.println("IllegalArgumentException occcured in "+ service + " service");
			return 0;
		}
		catch(NoSuchAlgorithmException e){
			System.out.println("NoSuchAlgorithmException occcured in "+ service + " service");
			return 0;
		}
		catch(NoSuchPaddingException e){
			System.out.println("NoSuchPaddingException occcured in "+ service + " service");
			return 0;
		}
		catch(InvalidKeyException e){
			System.out.println("InvalidKeyException occcured in "+ service + " service");
			return 0;
		}
		catch(UnsupportedOperationException e){
			System.out.println("UnsupportedOperationException occcured in "+ service + " service");
			return 0;
		}
		catch(IllegalStateException e){
			System.out.println("IllegalStateException occcured in "+ service + " service");
			return 0;
		}
		catch(IllegalBlockSizeException e){
			System.out.println("IllegalBlockSizeException occcured in "+ service + " service");
			return 0;
		}
		catch(BadPaddingException e){
			System.out.println("BadPaddingException occcured in "+ service + " service");
			return 0;
		}
		
		return 1;
	}

	public static void main(String[] args) {
		
		try{
			ServerSocket ss = new ServerSocket(2212);
		
			while(true){
				System.out.println("This is a Server with IP address : " + InetAddress.getByName("localhost") + " listening on port : 2212");
				Socket s = ss.accept();
				System.out.println("Connected to a remote client : " + s.getRemoteSocketAddress());
				BufferedReader r = new BufferedReader( new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter( s.getOutputStream() , true);
				String inp = "a";
				int ret = 1;

				while( ret == 1 && !inp.equals("4") 	){

					out.println("\nHello Client. These are the services : \n 1. File Encryption - Decryption\n 2. 21 Sticks Game\n 3. DropBox\n 4.quit\n\t\t Enter your choice : \n-EOF-" );
					out.flush();
					inp = r.readLine();

					ret = evaluate(inp ,s,r,out);
					out.flush();
				}

				if(ret == 0){
					s.close();	
					r.close();
					out.close();
				}
			}

			
		}
		catch(Exception e){
			System.out.println("exception occured");
			e.printStackTrace();
		}
	}
}