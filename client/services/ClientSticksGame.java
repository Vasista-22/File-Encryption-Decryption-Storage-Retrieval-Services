package client.services;

import java.io.*;
import java.net.*;

public class ClientSticksGame{	

	public void clientSticksGame(Socket s , BufferedReader r , PrintWriter out , BufferedReader inp) throws IOException{

		String resp = "";
		String request = "";
		String proceed = "";

		try{
			while(!request.equals("1") && !request.equals("2")){  // game menu
				
				while(!(resp = r.readLine()).equals("-EOF-")){			
						System.out.println(resp);
				}

				request = inp.readLine();
				out.println(request);
				out.flush();

				if(!request.equals("1") && !request.equals("2")){
					while(!(resp = r.readLine()).equals("-EOF-")){			
						System.out.println(resp);
					}
				}
			}	// end of game menu

			if(request.equals("1")){       // rules

				while(!(resp = r.readLine()).equals("-EOF-")){			
						System.out.println(resp);
				}

				proceed = inp.readLine();
				out.println(proceed);
				out.flush();       
			}							// end of rules display

			if(request.equals("2") || proceed.equals("1")){

				String start = "3";
				while(!start.equals("1") && !start.equals("2")){
					
					while(!(resp = r.readLine()).equals("-EOF-")){			
						System.out.println(resp);
					}
					start = inp.readLine();
					out.println(start);
					out.flush();
				}

				if(start.equals("2")){
					
					while(!(resp = r.readLine()).equals("-EOF-")){			
						System.out.println(resp);
					}
				}

				while(Integer.parseInt(r.readLine()) > 0 ){

					do{
						while(!(resp = r.readLine()).equals("-EOF-")){			
							System.out.println(resp);
						}

						request = inp.readLine();
						out.println(request);

						out.flush();

						while(!(resp = r.readLine()).equals("-EOF-")){			
							System.out.println(resp);
						}

					}while(r.readLine().equals("invalid"));

					while(!(resp = r.readLine()).equals("-EOF-")){			
							System.out.println(resp);
					}
				}

			}
		}
		catch(IOException e){
			throw e;
		}

	}
}