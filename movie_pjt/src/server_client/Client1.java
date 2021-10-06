package server_client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 {
	public static void main(String[] args) {
		try (Socket socket = new Socket("127.0.0.1",7777)){
			
			//�깒�뜑�� 由ъ떆踰� 留뚮뱾�븣 socket�쓣 �꽔�뼱二쇨퀬
			System.out.println("�겢�씪�씠�뼵�듃 �떎�뻾");
			ClientReceiver clientReceiver = new ClientReceiver(socket);
			ClientSender clientSender = new ClientSender(socket);
			
			clientReceiver.start();
			clientSender.start();
			
			clientSender.join();
			System.out.println("�봽濡쒓렇�옩�씠 醫낅즺�맗�땲�떎.");
			//�뒪�젅�뱶瑜� �떎�뻾 
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Commit test!
}
