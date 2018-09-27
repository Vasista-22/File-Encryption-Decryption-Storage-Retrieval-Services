This is a Client-Server based model in JAVA, where the server provides 3 services to the client.

The Client connects to the server using TCP on port 2212.

Services provided by the Server:
	1. File Encryption - Decryption
	2. File Storage - Retrieval ( mini DropBox simulation )
	3. 21 Sticks game between Client and Server.


Command to run the server:
							java server.Server

Command to run the Client:
							java client.Client


*** Encryption - Decryption service :
									Here user (through the client) names the file in the local directory that is to be encrypted. This file is passes in blocks to the server which encrypts using AES encryption. A secret key must be provided by the client which is passed to the server for the purpose of encryption.
									Each encrypted block is returned back to the client which writes these encrypted contents into a new file.
									The name of the encrypted file also must be specified by the user.
									Similar sequence of steps are followed for File Decryption.

									NOTE: Here AES encryption is used. So the file to be decrypted must have been encrypted using AES. Also to facilitate a key-length of 16, the secret key is either repeatedly concatenated or trimmed to get a key-length of 16.


*** Storage - Retrieval service    :
									Firstly the user needs to login or register on the mini DropBox on server. User name check is done and a user is allocated a directory for himself on the server.

									Here user has the option to either:
																		1. Copy files to server.
																		2. Retrieve files from server.
																		3. List files in his directory on server.
																		4. Delete files in his directory on server.

									NOTE: If a directory in the local directory with the user name the user wishes to have, such a directory doesn't get created. This is to ensure the server does a pure one-to-one mapping.
									User name and password of each user is stored in passwords.txt in the current directory of the server.
									Only file upload, retrieval and delete are supported. The service is not yet extended to directory level.


*** 	21 Sticks game 			  :
									A simple 21 sticks game between client and server. User gets to choose who starts the game. Game rules are displayed during run time.


							-----     THANK YOU   -----
