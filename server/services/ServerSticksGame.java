package server.services;

import java.io.*;
import java.util.Random;
import java.net.*;

public class ServerSticksGame{

	public void sticksGame(Socket s , BufferedReader r , PrintWriter out) throws IOException{

		String choice = "3";
		
		try{
			while(!choice.equals("1") && !choice.equals("2")){
				out.println("Welcome to the 21 Sticks Game. \n 1. Rules\n 2. Start Game\n\t\tEnter Your Choice : \n-EOF-");
				out.flush();
				choice = r.readLine();

				if(!choice.equals("1") && !choice.equals("2")){
					out.println("\nThat was an invalid choice. Please try again.\n-EOF-");
				}
			}
			String proceed = "2";

			if(choice.equals("1")){
				out.println("\n There are 21 sticks as the name of the game suggests." +
							"\n The game is between the client and the server." +
							"\n User gets to choose who starts the game." +
							"\n At a time the user or server must pick a minimum of 1stick and a maximum of 4 sticks from the pool"+
							"\n The participant who picks the last stick from the pool loses the game." +
							"\n All the best." +
							"\n Enter 1 to proceed to play the game or any other input to go back to main menu of all services." +
							"\n-EOF-");
				out.flush();

				proceed = r.readLine();
			}

			if(proceed.equals("1") || choice.equals("2")){

				String start = "3";
				int sticks = 21;

				while(!start.equals("1") && !start.equals("2")){
					out.println("\n 1. You'll start the game by picking sticks" +
								"\n 2. Server starts the game" +
								"\n-EOF-");
					out.flush();
					start = r.readLine();
				}

				if(start.equals("2")){

					int num = 0;
					Random spick = new Random();

					while(num == 0){
						num = spick.nextInt(5);
					}
					sticks -= num;
					out.println("\nServer picked " + num + " sticks. Sticks left = "+ sticks + "\n-EOF-");
					out.flush();
				}

				out.println(sticks);

				while(sticks > 0){

					int picked = 0;
					do{
						out.println("\n Enter the number of sticks you wish to draw : \n-EOF-");
						out.flush();
						try{
							picked = Integer.parseInt(r.readLine());
								
							if(picked<=0 || picked>4 || (sticks-picked) < 0 ){
								out.println("\nInvalid input. Refer to the rules carefully\n-EOF-");
								out.flush();
								out.println("invalid");
								out.flush();
							}

							else{
								out.println("\nThat was a valid input\n-EOF-");
								out.flush();
							}
						}
						catch(NumberFormatException e){
							out.println("Invalid input. NumberFormat Exception occured. Try again.\n-EOF-");
							out.flush();
							out.println("invalid");
							out.flush();						
						}

					}while(picked<=0 || picked>4 || (sticks-picked) < 0);

					out.println("valid");
					out.flush();

					sticks -= picked;
					out.println("\n You've picked " + picked + "sticks. Now "+ sticks +" sticks are left");
					out.flush();

					if(sticks == 0){
						out.println("\n All sticks exhausted. GAME OVER . Server wins. Better Luck Next Time\n-EOF-");
						out.flush();
					}

					else{
						int num = 1;

						if(sticks > 5){
							while( (num + picked)%5 != 0 ){
								num++;
							}
						}

						else if(sticks == 1){
							num = sticks;
						}

						else if(sticks > 1 && sticks <=5 ){
							num = sticks - 1;
						}

						sticks -= num;
						if(sticks > 0){
							out.println("\n Server picked "+ num + " sticks. Sticks left = "+ sticks + "\n-EOF-");
							out.flush();
						}

						else{
							out.println("\n Server picked "+ num + " sticks. Sticks left = "+ sticks + " You've WON the game\n-EOF-");
							out.flush();
						}
					}

					out.println(sticks);

				}		// end of while loop

			} // end of proceed if
		}

		catch(IOException e){
			throw e;
		}

	}
}