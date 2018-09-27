package client.services;

import java.io.*;
import java.net.*;
import java.security.*;
	
public class ClientDropBox{

	private int access;
	private int validFlag;
	private String userName;
	private String password;
	
	public ClientDropBox(){

		access = 0;
		validFlag = 0;
		userName = "";
		password = "";
	}

	////////////////////////////////////////////////////////////

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

	/////////////////////////////////////////////////////////////

	public void tryLogin(BufferedReader r , PrintWriter out, BufferedReader inp) throws IOException , NumberFormatException{

		/* ------ Receiving the validFlag( initially set to zero )  ------ */
		String resp = "";

		try{
			while(!(resp = r.readLine()).equals("-EOF-")){			
				this.validFlag = Integer.parseInt(resp);
			}
		}
		 catch(IOException e){
		 	throw e;
		 }
		 catch(NumberFormatException e){
		 	throw e;
		 }


		/* --------- Get user name. If invalid re-prompt -------- */

			while( this.validFlag == 0){

			/* ---  prompt for userName --- */

				try{
					this.printReceivedOutput(r);
				}
				 catch(IOException e){
				 	throw e;
				}
			
			/* ------ take and pass user name  -------- */

				try{
					this.userName = inp.readLine();
				}
				 catch(IOException e){
				 	throw e;
				 }
				
				out.println(this.userName);
				out.flush();
							
			/* ------ Success or Failure  -------- */

				try{
					this.printReceivedOutput(r);     
				}
				 catch(IOException e){
				 	throw e;
				}

			/* ----------  Get Valid Flag   ------------ */

				try{
					while(!(resp = r.readLine()).equals("-EOF-")){		
						this.validFlag = Integer.parseInt(resp);
					}
				}
				 catch(IOException e){
				 	throw e;
				 }		
				 catch(NumberFormatException e){
				 	throw e;
				 }
			}

		/*     ------       Request for Password      -------- */

		try{
			this.printReceivedOutput(r);
		}
		catch(IOException e){
			throw e;
		}


		/* ---------  Take and Send Password   ---------- */
		try{
			this.password = inp.readLine();
		}
		 catch(IOException e){
		 	throw e;
		 }
		
		out.println(this.password);
		out.flush();

		/* ---------  Authentication result and set access      ------*/
		try{
			this.printReceivedOutput(r);
			this.access = Integer.parseInt(r.readLine());
		}
		catch(IOException e){
			throw e;
		}
		catch(NumberFormatException e){
			throw e;
		}

	}

	//////////////////////////////////////////////////

	public void registration(BufferedReader r, PrintWriter out, BufferedReader inp) throws IOException,NumberFormatException{

		String resp = "";

		/* ------------ fetch validFlag  --------------- */

		try{
			while(!(resp = r.readLine()).equals("-EOF-")){			
				this.validFlag = Integer.parseInt(resp);
			}
		}
		catch(IOException e){
			throw e;
		}
		catch(NumberFormatException e){
			throw e;
		}


		while(this.validFlag == 0){
						
			/* ------------ prompt to choose user name  ------------- */
			try{
				this.printReceivedOutput(r);
			}
			catch(IOException e){
				throw e;
			}
			
			try{
				this.userName = inp.readLine();
			}
			catch(IOException e){
				throw e;
			}

			out.println(userName);
			out.flush();

			/* --------  response for userName (Success or failure)   ------- */
 			try{
				this.printReceivedOutput(r);
			}
			catch(IOException e){
				throw e;
			}

			/* ------------ fetch validFlag  -------- */
			try{
				while(!(resp = r.readLine()).equals("-EOF-")){			
					this.validFlag = Integer.parseInt(resp);
				}
			}
			catch(IOException e){
				throw e;
			}
			catch(NumberFormatException e){
				throw e;
			}
		}

		/* ------------  request for password  ---------- */
		try{
			this.printReceivedOutput(r);
		}
		catch(IOException e){
			throw e;
		}
			
		try{
			this.password = inp.readLine();
		}
		catch(IOException e){
			throw e;
		}

		out.println(this.password);
		out.flush();

		/*  -------------  Successful registration  ---------- */
		try{
			this.printReceivedOutput(r);
			this.access = Integer.parseInt(r.readLine());
		}
		catch(IOException e){
			throw e;
		}
		catch(NumberFormatException e){
			throw e;
		}
					
	}

	/////////////////////////////////////////////////

	public void copyFile(Socket s, BufferedReader r, PrintWriter out, BufferedReader inp) throws IOException,SecurityException,FileNotFoundException{

		/*  ----     asks for the name of file to be copied  ---- */
		try{
			this.printReceivedOutput(r);
		}
		 catch(IOException e){
			throw e;
		}

		String fileName = "";

		try{
			fileName = inp.readLine();
		}
		catch(IOException e){
			throw e;
		}

		out.println(fileName);
		out.flush();

		System.out.println("\nName of local file you wish to copy: \n");
		String toCopy = "";

		try{
			toCopy = inp.readLine();
		}
		catch(IOException e){
			throw e;
		}

		BufferedInputStream fis = null;

		try{
			fis = new BufferedInputStream(new FileInputStream(toCopy));
		}
		catch(FileNotFoundException e){
			throw e;
		}
		catch(SecurityException e){
			throw e;
		}

		byte[] byteArray = new byte[4096];
		System.out.println("before read");
		OutputStream output = null;

		try{
			output = s.getOutputStream();
		}
		catch(IOException e){
			throw e;
		}
						
		int count = 0;

		try{
			while((count = fis.read(byteArray)) != -1){
								
				System.out.println(count);
				System.out.println("after read " + byteArray.length);
								
				output.write(byteArray,0,count);
				output.flush();
				System.out.println("after write");

				if(count < byteArray.length){
					break;
				}
			}

			fis.close();
		}
		catch(IOException e){
			throw e;
		}
		System.out.println("Copying done  :)");
	}

	//////////////////////////////////////////////////


	public void retrieveFile(Socket s, BufferedReader r, PrintWriter out, BufferedReader inp) throws IOException,FileNotFoundException,NullPointerException,SecurityException{


		/*  ----     asks for the name of file to be retrieved  ---- */
		try{
			this.printReceivedOutput(r);
		}
		 catch(IOException e){
			throw e;
		}

		String fileName = "";
		
		try{
			fileName = inp.readLine();
		}
		catch(IOException e){
			throw e;
		}

		out.println(fileName);
		out.flush();

		String isFound = "";

		try{
			isFound = r.readLine();
		}
		catch(IOException e){
			throw e;
		}

		if(isFound.equals("found")){

			System.out.println("\nHow do you wish to name the local copy of that file?");
			String downloaded = "";

			try{
				downloaded = inp.readLine();
			}
			catch(IOException e){
				throw e;
			}

			BufferedOutputStream fos = null; 
			
			try{
				fos = new BufferedOutputStream(new FileOutputStream(downloaded));
			}
			catch(FileNotFoundException e){
				throw e;
			}
			catch(SecurityException e){
				throw e;
			}

			byte[] byteArray = new byte[4096];
			System.out.println("before read");
			InputStream input = null;

			try{
				input = s.getInputStream();
			}
			catch(IOException e){
				throw e;
			}
							
			int count = 0;

			try{
				while((count = input.read(byteArray)) != -1){
									
					System.out.println(count);
					System.out.println("after read " + byteArray.length);
									
					fos.write(byteArray,0,count);
					System.out.println("after write");

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
			out.println("Retrieval done");
			out.flush();
			System.out.println("Retreival done  :). File is in local directory with the specified name.");
		}

		else{
			System.out.println("File not found in server");
		}
	}

	/////////////////////////////////////////////////

	public void deleteFile(BufferedReader r, PrintWriter out, BufferedReader inp) throws IOException{

		/*   ------------   get the name of the file to be deleted  ------- */	
		try{
			this.printReceivedOutput(r);
		}
		 catch(IOException e){
			throw e;
		}

		String toDelete = "";

		try{
			toDelete = inp.readLine();
		}
		catch(IOException e){
			throw e;
		}

		out.println(toDelete);
		out.flush();

		/* ---------   response after delete  -----------  */	
		try{
			this.printReceivedOutput(r);
		}
		 catch(IOException e){
			throw e;
		}
	}

	/////////////////////////////////////////////////

	public void clientDropBox(Socket s , BufferedReader r , PrintWriter out , BufferedReader inp) throws IOException,SecurityException,NumberFormatException,NullPointerException,FileNotFoundException{

				String proceed = "";
			
			/*   ---------  Prints DropBox introduction   ----- */
				try{
					this.printReceivedOutput(r);
				}
				 catch(IOException e){
				 	throw e;
				 }

				try{
					proceed = inp.readLine();
				}
				 catch(IOException e){
				 	throw e;
				 }

				out.println(proceed);
				out.flush();
				

			/* --------      If user wishes to proceed using DropBox ------------- */

			if(proceed.equals("1")){

				/*  --------  Login info --------------- */

				try{
					this.printReceivedOutput(r);
				}
				 catch(IOException e){
				 	throw e;
				 }
				
				String login = "";

				try{
					login = inp.readLine();
				}
				 catch(IOException e){
				 	throw e;
				 }
				out.println(login);
				out.flush();
				
				if(login.equals("1")){  	// existing user
					
					try{
						this.tryLogin(r,out,inp);
					}
					 catch(IOException e){
					 	throw e;
					 }
					 catch(NumberFormatException e){
					 	throw e;
					 }

				}		// end of login

				else if(login.equals("2")){		// new user
					
					try{
						this.registration(r,out,inp);
					}
					catch(IOException e){
						throw e;
					}
					catch(NumberFormatException e){
						throw e;
					}

				} // end of registration

				String choice = "";

				while(!choice.equals("5") && this.validFlag == 1 && this.access == 1){

					/* --- prompt for options in DropBox  --- */
					try{
						this.printReceivedOutput(r);
					}
					 catch(IOException e){
					 	throw e;
					 }

					/*  ---------  take user's choice   -------- */
					try{
						choice = inp.readLine();
					}
					catch(IOException e){
						throw e;
					}

					out.println(choice);
					out.flush();

					if(choice.equals("5")){
						
						/* -- prints logout message  --- */
						try{
							this.printReceivedOutput(r);
						}
						 catch(IOException e){
				 			throw e;
				 		}
						break;
					}

					else if(choice.equals("1")){
						
						try{
							this.copyFile(s,r,out,inp);
						}
						catch(FileNotFoundException e){
							throw e;
						}
						catch(IOException e){
							throw e;
						}
						catch(SecurityException e){
							throw e;
						}

					}


					else if(choice.equals("2")){

						try{
							this.retrieveFile(s,r,out,inp);
						}
						catch(FileNotFoundException e){
							throw e;
						}
						catch(IOException e){
							throw e;
						}
						catch(NullPointerException e){
							throw e;
						}
						catch(SecurityException e){
							throw e;
						}
						
					}

					else if(choice.equals("3")){		// delete a file

						try{
							this.deleteFile(r,out,inp);
						}
						catch(IOException e){
							throw e;
						}
					}

					else if(choice.equals("4")){		// list all files
						
						System.out.println("Existing files in your DropBox are : ");

						/*  --------  response for List files -------- */
						try{
							this.printReceivedOutput(r);
						}
					 	catch(IOException e){
					 		throw e;
						 }
					}
				}
			}

	}

}