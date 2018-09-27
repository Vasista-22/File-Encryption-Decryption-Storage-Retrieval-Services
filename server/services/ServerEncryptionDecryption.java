package server.services;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;


public class ServerEncryptionDecryption{

	public String fetchKey(BufferedReader r , PrintWriter out) throws IOException{
		
		out.println("\nSend the secret key: \n-EOF-");
		out.flush();
		String key = "";

		try{
			key = r.readLine();
		}
		catch(IOException e){
			throw e;
		}

		return key;
	}

	public void fileEncryptionOrDecryption(Socket s, BufferedReader r, PrintWriter out,String choice) throws IOException,NullPointerException,
																			IllegalArgumentException,NoSuchAlgorithmException,
																			NoSuchPaddingException,InvalidKeyException,
																			UnsupportedOperationException,IllegalStateException,
																			IllegalBlockSizeException,BadPaddingException,
																			AEADBadTagException{
		String key = "";

		try{
			key = this.fetchKey(r,out);
		}
		catch(IOException e){
			throw e;
		}
		
		int num = 4096;
		int mode = Cipher.ENCRYPT_MODE;

		if(choice.equals("1")){
			num = 4096;
			mode = Cipher.ENCRYPT_MODE;
		}

		else if(choice.equals("2")){
			num = 4112;
			mode = Cipher.DECRYPT_MODE;
		}

		byte[] byteArray = new byte[num];
		InputStream input = null; 
		
		try{
			input = s.getInputStream();
		}
		catch(IOException e){
			throw e;
		}
				
		Key secretKey = null;

		try{
			secretKey = new SecretKeySpec(key.getBytes() , "AES");
		}
		catch(IllegalArgumentException e){
			throw e;
		}

		Cipher cipher = null;

		try{
			cipher = Cipher.getInstance("AES");
		}
		catch(NoSuchAlgorithmException e){
			throw e;
		}
		catch(NoSuchPaddingException e){
			throw e;
		}

		try{
			cipher.init(mode , secretKey);
		}
		catch(InvalidKeyException e){
			throw e;
		}
		catch(UnsupportedOperationException e){
			throw e;
		}

		OutputStream output = null; 

		try{
			output = s.getOutputStream();
		}
		catch(IOException e){
			throw e;
		}

		int count = 0;

		try{
			while((count = input.read(byteArray)) != -1){
						
				byte[] outputBytes = cipher.doFinal(byteArray ,0, count);					
				output.write(outputBytes,0,outputBytes.length);
				
				output.flush();	
				if(count < byteArray.length){
					break;
				}
			}
		}
		catch(AEADBadTagException e){
			throw e;
		}
		catch(IOException e){
			throw e;
		}
		catch(NullPointerException e){
			throw e;
		}
		catch(IllegalStateException e){
			throw e;
		}
		catch(IllegalBlockSizeException e){
			throw e;
		}
		catch(BadPaddingException e){
			throw e;
		}
		
		catch(UnsupportedOperationException e){
			throw e;
		}

		
	}

	////////////////////////////////////////

	public void fileEncryptionAndDecryption(Socket s , BufferedReader r , PrintWriter out)throws IOException,NullPointerException,
																			IllegalArgumentException,NoSuchAlgorithmException,
																			NoSuchPaddingException,InvalidKeyException,
																			UnsupportedOperationException,IllegalStateException,
																			IllegalBlockSizeException,BadPaddingException,
																			AEADBadTagException{
		

			out.println("\nWhat do you want to do?\n 1.FileEncryption\n 2.FileDecryption\n Enter your choice:\n-EOF-");
			out.flush();
			String choice = r.readLine();

			if(choice.equals("1") || choice.equals("2")){
				
				
				try{
					this.fileEncryptionOrDecryption(s,r,out,choice);
				}
				catch(AEADBadTagException e){
					throw e;
				}
				catch(IOException e){
					throw e;
				}
				catch(NullPointerException e){
					throw e;
				}
				catch(IllegalArgumentException e){
					throw e;
				}
				catch(IllegalStateException e){
					throw e;
				}
				catch(NoSuchAlgorithmException e){
					throw e;
				}
				catch(NoSuchPaddingException e){
					throw e;
				}
				catch(IllegalBlockSizeException e){
					throw e;
				}
				catch(BadPaddingException e){
					throw e;
				}
				catch(InvalidKeyException e){
					throw e;
				}
				
			}

	}
}