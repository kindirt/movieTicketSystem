package server_client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 {
	public static void main(String[] args) {
		try (Socket socket = new Socket("127.0.0.1",7777)){
			
			//占쎄퉺占쎈쐭占쏙옙 �뵳�딅뻻甕곤옙 筌띾슢諭억옙釉� socket占쎌뱽 占쎄퐫占쎈선雅뚯눊��
			System.out.println("占쎄깻占쎌뵬占쎌뵠占쎈섧占쎈뱜 占쎈뼄占쎈뻬");
			ClientReceiver clientReceiver = new ClientReceiver(socket);
			ClientSender clientSender = new ClientSender(socket);
			
			clientReceiver.start();
			clientSender.start();
			
			clientSender.join();
			System.out.println("占쎈늄嚥≪뮄�젃占쎌삪占쎌뵠 �넫�굝利븝옙留쀯옙�빍占쎈뼄.");
			//占쎈뮞占쎌쟿占쎈굡�몴占� 占쎈뼄占쎈뻬 
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Commit test!
	// hello World! 한글 테스트
}
