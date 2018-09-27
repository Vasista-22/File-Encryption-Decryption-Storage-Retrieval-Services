package server.services;

import java.io.*;
import java.net.*;
import java.security.*;

public class ServerDropBox{

	private int access;
	private int validFlag;
	private String userName;
	private String password;

	public ServerDropBox(){
		access = 0;
		validFlag = 0;
		userName = "";
		password = "";
	}

	public void tryLogin(BufferedReader r , PrintWriter out) throws IOException , FileNotFoundException{

		out.println(this.validFlag + "\n-EOF-");
		out.flush();
		
		/*   --- Verifying user name   --- */

			while(this.validFlag == 0){
				out.println("\nPlease enter your user name \n-EOF-");
				out.flush();

				try{
					this.userName = r.readLine();
				}
				 catch(IOException e){
				 	throw e;
				 }

				File user = new File("./" + this.userName);

				if(user.exists()){
					out.println("\nWelcome "+ this.userName +"\n-EOF-");
					out.flush();

					this.validFlag = 1;
				}

				else{
					out.println("\nNo such user exists with "+ this.userName+" as user name. Please try again.\n-EOF-");
					out.flush();
				}

				out.println(this.validFlag + "\n-EOF-");
				out.flush();
			}

		/* ----      taking password   --- */

		out.println("\nEnter your password\n-EOF-");
		out.flush();
		this.password = r.readLine();
		System.out.println("Received password = " + this.password);

		
		/*     --- Retrieving password for verification   --- */

			BufferedReader pass = null;
			try{
				pass = new BufferedReader(new FileReader ("passwords.txt"));
			}
			 catch(FileNotFoundException e){
			 	throw e;
			 }

			String getUser = "";
			String toCheck = "";
			
			try{									
				while(true){
					getUser = pass.readLine();    // matching username

					if(getUser.equals(this.userName)){
						toCheck = pass.readLine();    // extracting original password
						break;
					}
				}
			}
			 catch(IOException e){
			 	throw e;
			 }

	        pass.close();      // closing the file

        /*      --- verifying password  ------- */

			if(toCheck.equals(this.password)){
				this.access = 1;					// updating access

				out.println("access granted\n-EOF-");
				out.flush();
			}

			else{
				out.println("Wrong password. Access Denied\n-EOF-");
				out.flush();
			}
						
		out.println(this.access);
		out.flush();

	}

	///////////////////////////////

	public void registration(BufferedReader r , PrintWriter out) throws IOException, NullPointerException, SecurityException{

		out.println(this.validFlag + "\n-EOF-");
		out.flush();

		File user = null;

		/* ---- getting a valid user name from user. If the given name already exists, re-prompts the user --- */

			while(this.validFlag == 0){

				out.println("\nPlease enter the user name you wish to have\n-EOF-");
				out.flush();

				try{
					this.userName = r.readLine();
				}
				 catch(IOException e){
				 	throw e;
				 }

				try{
					user = new File("./" + this.userName);
				}
				 catch(NullPointerException e){
				 	throw e;
				 }

				if(user.exists()){
					out.println("\nSorry , such a user name already exists. Please choose an alternate user name\n-EOF-");
					out.flush();
				}

				else{						
					out.println("\nThat was a valid user name\n-EOF-");
					out.flush();
					this.validFlag = 1;
				}
									
				out.println(this.validFlag + "\n-EOF-");
				out.flush();
			}


		/*   ---   Get the chosen password    --- */

			out.println("\nPlease choose a password\n-EOF-");
			out.flush();

			try{
				this.password = r.readLine();
			}
			 catch(IOException e){
			 	throw e;
			 }


		/*   -----   Store the user's details in a file called passwords.txt   ---- */

			BufferedWriter create = null;
			try{ 
				create = new BufferedWriter(new FileWriter("passwords.txt" , true));
			}
			 catch(IOException e){
			 	throw e;
			 }

			String u = this.userName+"\n";
			String p = this.password+"\n";

			create.write(u, 0 ,u.length());
			create.write(p ,0 ,p.length());
			create.close();

			try{
				user = new File("./" + this.userName);
			}
			 catch(NullPointerException e){
			 	throw e;
			 }

			try{
				user.mkdir();
			}
			 catch(SecurityException e){
			 	throw e;
			 }

		/*  ----   grant access        ---- */

		out.println("\n You have been sucessfully registered to DropBox\n-EOF-");
		this.access = 1;
		out.println(this.access);
		out.flush();
	}


	//////////////////////////////

	public void copyFile(Socket s , BufferedReader r , PrintWriter out) throws IOException, SecurityException, FileNotFoundException, NullPointerException{

		out.println("\nEnter the file name you wish to have on DropBox\n-EOF-");
		out.flush();

		String fileName = "";
		

		/*  ----- get the name of the file   --- */
			try{
				fileName = r.readLine();
			}
			 catch(IOException e){
			 	throw e;
			 }

		/*    ---  create the file   ---- */
			BufferedOutputStream fos = null;
			try{
				fos = new BufferedOutputStream(new FileOutputStream("./"+ this.userName+"/"+fileName));
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
			input= s.getInputStream();
		}
		catch(IOException e){
			throw e;
		}
					
		int count = 0;

		/* ---- Copy contents from input stream to the file   --- */

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
			}
			 catch(IOException e){
			 	throw e;
			 }
			 catch(NullPointerException e){
			  	throw e;
			 }

		try{
			fos.close();
		}
		 catch(IOException e){
		 	throw e;
		 }

		System.out.println("Copying done  :)");
	}


	//////////////////////////////////

	public void retrieveFile(Socket s , BufferedReader r , PrintWriter out) throws IOException, FileNotFoundException, SecurityException{

		out.println("\nEnter the name of the file you wish to retrieve\n-EOF-");
		out.flush();
		String fileName = "";

		try{
			fileName= r.readLine();
		}
		 catch(IOException e){
		 	throw e;
		 }

		BufferedInputStream fis = null;

		try{
			fis= new BufferedInputStream(new FileInputStream ("./" + this.userName + "/" +fileName));
		}
		 catch(FileNotFoundException e){
		 	out.println("missing");
		 	out.flush();
		 	throw e;
		 }
		 catch(SecurityException e){
		 	out.println("missing");
		 	out.flush();
		 	throw e;
		 }

		out.println("found");
		out.flush();
		byte[] byteArray = new byte[4096];
		OutputStream output  = null;

		try{
			output = s.getOutputStream();
		}
		 catch(IOException e){
		 	throw e;
		 }

		try{
			int count = 0;
			while((count = fis.read(byteArray)) != -1){

				System.out.println(count);
				output.write(byteArray , 0, count);
				output.flush();

				if(count < byteArray.length){
					break;
				}
			}

			fis.close();
			r.readLine();
		}
		 catch(IOException e){
		 	throw e;
		 }

		System.out.println("Retrieval done from Server side :)\n");
	}

	////////////////////////////////////


	public void deleteFile(Socket s, BufferedReader r , PrintWriter out) throws IOException,NullPointerException,SecurityException{

		out.println("\nEnter the name of the file you wish to delete\n-EOF-");
		out.flush();

		String toDelete = null;

		try{
			toDelete = r.readLine();
		}
		 catch(IOException e){
		 	throw e;
		 }

		File fileName = null;

		try{
			fileName = new File("./" + this.userName + "/" + toDelete);
		}
		 catch(NullPointerException e){
		 	throw e;
		 }

		try{
			if(fileName.exists()){
				fileName.delete();
				out.println("\nSucessfully deleted\n-EOF-");
				out.flush();
			}

			else{
				out.println("\nNo such file exists\n-EOF-");
				out.flush();
			}
		}
		 catch(SecurityException e){
		 	throw e;
		 }

	}

	////////////////////////////////////

	public void listFiles(Socket s, BufferedReader r, PrintWriter out, File user) throws SecurityException{

		String files[] = null;

		try{
			files = user.list();
		}
		 catch(SecurityException e){
		 	throw e;
		 }

		for(String file : files){
			out.println(file + "\n");
			out.flush();
		}

		out.println("\n-EOF-");
		out.flush();
		System.out.println("Listed all files");
	}

	///////////////////////////////////

	public void dropBox(Socket s , BufferedReader r , PrintWriter out) throws IOException,FileNotFoundException,NullPointerException,SecurityException{


			BufferedWriter create = null;
			try{ 
				create = new BufferedWriter(new FileWriter("passwords.txt" , true));
			}
			 catch(IOException e){
			 	throw e;
			}

			create.close();
		/* ----  Welcome  prompt  ---- */

				out.println("\nWelcome to DropBox. Here Each user will be allocated a folder."+
							"\nYour userName is your folder name."+
							"\nYour folder's contents would be password protected"+
							"\nIf you wish to proceed into dropBox, Enter 1, any other input would take you back to Services menu"+
							"\n-EOF-");
				out.flush();

		/* ------  check if user wants to continue   --- */

				String proceed = "";
				try{
					proceed = r.readLine();
				}
				 catch(IOException e){
					throw e;
		   		 }

		/*   ------    If user wishes to continue       ---- */

			if(proceed.equals("1")){

					out.println("\n 1.Existing user Login\n 2.New user Registration\n-EOF-");
					out.flush();
					String permission = "";

					try{
						permission = r.readLine();
					}
					 catch(IOException e){
						 throw e;
					 }

					
					if(permission.equals("1")){  	// existing user
						
						try{
							this.tryLogin(r,out);
						}
						 catch(FileNotFoundException e){
						 	throw e;
						 }
						 catch(IOException e){
						 	throw e;
						 }
					} 


					else if(permission.equals("2")){		// new user
						
						try{
							this.registration(r,out);
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

			/*   ------  Post registration or login  ------ */

					File user = null;

					try{
						user = new File("./" + this.userName);
					}
					 catch(NullPointerException e){
					 	throw e;
					 }

					String choice = "";

					while(!choice.equals("5") && this.validFlag == 1 && this.access == 1 ){


					/*  ---    Exposing API to the user    ---- */

						out.println("\nWhat do you wish to do?"+
									"\n 1. Copy file from local machine" +
									"\n 2. Retreive file to local machine" +
									"\n 3. Delete a file from your dropbox" +
									"\n 4. List Files in your account" +
									"\n 5. Logout\n-EOF-");
						out.flush();

						try{
							choice = r.readLine();
						}
						 catch(IOException e){
						 	throw e;
						 }

						if(choice.equals("5")){
							out.println("Logging out . Thank you :)\n-EOF-");
							out.flush();
							break;
						}

						else if(choice.equals("1")){
							
							try{
								this.copyFile(s, r, out);
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
							 catch(NullPointerException e){
							 	throw e;
							 } 
							 
						}


						else if(choice.equals("2")){

							try{
								this.retrieveFile(s,r,out);
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

						else if(choice.equals("3")){		// delete a file

							try{
								this.deleteFile(s,r,out);
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

						else if(choice.equals("4")){		// list all files
							
							try{
								this.listFiles(s,r,out,user);
							}
							 catch(SecurityException e){
							 	throw e;
							 }
						}
					}
				}

			
	} // end of function
}
