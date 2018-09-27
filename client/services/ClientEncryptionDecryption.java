package client.services;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import java.util.Arrays;
import java.nio.file.Files;


public class ClientEncryptionDecryption{

	public String keyFix(String s){

		String goodKey = "";
		
		if(s.length() != 16){
			
			while(goodKey.length() < 16){
				goodKey = goodKey.concat(s);
			}

			if(goodKey.length() > 16){
				goodKey = goodKey.substring(0,16);
			}

			return goodKey;
		}

		else{
			return s;
		}
	}

	/////////////////////////////////////////////


	public void printReceivedOutput(BufferedReader r) throws IOException{

		String resp = "";

		try{
			while(!(resp = r.readLine()).equals("-EOF-")){			
				System.out.println(resp);
			}
		}
		 catch(IOException e){
		 	throw e;
		 }
	}

	/////////////////////////////////////////////////

	public void clientFileEncryption(Socket s , BufferedReader r , PrintWriter out , BufferedReader in) throws IOException,NullPointerException,SecurityException,FileNotFoundException{

			String request = "";
			String service = "Encryption and Decryption";

			try{
				this.printReceivedOutput(r);
			}
			catch(IOException e){
				throw e;
			}

			try{
				request = in.readLine(); // encryption or decryption
			}
			catch(IOException e){
				throw e;
			}
			
			out.println(request);
			out.flush();

			if(request.equals("1") || request.equals("2")){

				int num1=4096,num2=4112;
				

				try{
					this.printReceivedOutput(r);
				}
				catch(IOException e){
					throw e;
				}

				String key = "";
				try{
					key = in.readLine(); // key
				}
				catch(IOException e){
					throw e;
				}

				key = this.keyFix(key);
				out.println(key);
				out.flush();
				System.out.println("Your key is " + key);
				String fileName = "";
				String cryptedName = "";

				if(request.equals("1")){
					num1 = 4096;
					num2 = 4112;
					System.out.println("\nEnter the name of the file to be encrypted : ");
					
					try{
						fileName = in.readLine();
					}
					catch(IOException e){
						throw e;
					}

					System.out.println("\nEnter the name of file after encryption : ");
					

					try{
						cryptedName = in.readLine(); // name of the encrypted file
					}
					catch(IOException e){
						throw e;
					}
				}

				else if(request.equals("2")){
					num1 = 4112;
					num2 = 4096;
					System.out.println("\nEnter the name of the file to be decrypted : ");
					
					try{
						fileName = in.readLine();
					}
					catch(IOException e){
						throw e;
					}

					System.out.println("\nEnter the name of file after decryption : ");
					

					try{
						cryptedName = in.readLine(); // name of the encrypted file
					}
					catch(IOException e){
						throw e;
					}
				}

				
				/*  --      sending the file       -- */
				
				File tocrypt = null;

				try{
					tocrypt = new File(fileName);
				}
				catch(NullPointerException e){
					throw e;
				}
				
				byte[] byteArray = new byte[num1];
				
				BufferedInputStream bin = null;

				try{
					bin = new BufferedInputStream(new FileInputStream(tocrypt));
				}
				catch(FileNotFoundException e){
					throw e;
				}
				catch(SecurityException e){
					throw e;
				}

				BufferedOutputStream fos = null;
				try{
					fos = new BufferedOutputStream(new FileOutputStream(cryptedName));
				}
				catch(FileNotFoundException e){
					throw e;
				}
				catch(SecurityException e){
					throw e;
				}

				OutputStream output = null;
				try{
					output = s.getOutputStream();
				}
				catch(IOException e){
					throw e;
				}
				InputStream input = null;
				try{
					input = s.getInputStream();
				}
				catch(IOException e){
					throw e;
				}
				
				if(num1 == 4096){
					System.out.println("\n  Your request for encryption is being processed \n");
				}

				else{
					System.out.println("\n  Your request for decryption is being processed \n");
				}

				int count = 0;

				try{
					while(true){

						count = bin.read(byteArray , 0 , byteArray.length);
						if(count == -1){
							break;
						}
							
						output.write(byteArray,0,count);
						
						output.flush();	
						byte[] cryptedData = new byte[num2];
						
						int got = input.read(cryptedData);

						fos.write(cryptedData, 0 , got);

						if(count < byteArray.length){
							break;
						}
						
					}	
					
					fos.close();
				}
				catch(IOException e){
					throw e;
				}
				catch(NullPointerException e){
					throw e;
				}


				if(num1 == 4096){
					System.out.println("\n  Encryption done :). File is in the current directory with the name you specified. \n");
				}

				else{
					System.out.println("\n  Decryption done :) File is in the current directory with the name you specified. \n");
				}

				
			}

			
	}
}