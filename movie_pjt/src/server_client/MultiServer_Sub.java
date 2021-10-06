package server_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultiServer_Sub {
	private ServerSocket serverSocket;
	private List<MultiServer_Sub.User> userList; //여기에 회원가입한 유저를 넣어야겠지?
	
	
	public MultiServer_Sub(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		userList = new ArrayList<MultiServer_Sub.User>();
	}
	
	//서버의 기능 구현
	public void runServer() throws IOException {
		//접속을 지속적으로 대기하는 코드
		while(true) {
			System.out.println("접속 대기중...");
			Socket socket = serverSocket.accept();
			System.out.println("접속 : "+socket.getInetAddress()+"-"+socket.getPort());
			
			//접속된 소켓을 넣어서 user객체를 생성
			User user = new User(socket);
			
			//유저를 리스트로 담음
			userList.add(user);

			//스레드로 실행
			user.start();
		}		
	}
	//접속자에 대한 정보를 저장하는 클래스
	class User extends Thread { //접속을 하는 기능
		private String id;
		private String pw;
		private String an_id;
		private String an_pw;
		private Socket socket;
		private BufferedWriter bw;
		
		public User(Socket socket) throws IOException {
			this.socket = socket;
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		}
		@Override
		public void run() {
			//socket과 연결된 아이가 쓴 글을 읽을 수 있도록 기능을 구현한다.
//			BufferedReader br = null;
			System.out.println("--- 서버 스레드 실행 ---");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
			String an = "";
				while(!an.equals("로그인성공")) {
				
					//접속시 초기 셋팅과 초기 알림
					bw.write("아이디를 입력해주세요. \n");
					bw.flush();
					
					this.id = br.readLine();
					System.out.println(id);
					
					bw.write("비밀번호를 입력해주세요.\n");
					bw.flush();
					
					this.pw = br.readLine();
					System.out.println(pw);
					
					bw.write("로그인 중...\n");
					bw.flush();
					
					MovieDao dao = MovieDaoImpl.getInstance();
					try {
						List<MovieDto> userList = new ArrayList<MovieDto>();
						
						userList = dao.userFindByName(id);
						for(MovieDto user : userList) {
							if(id.equals(user.getUser_id())) {
								an_id = user.getUser_id();
							}
							if(pw.equals(user.getUser_pw())) {
								an_pw = user.getUser_pw();
							}
						}
						if(id.equals(an_id)) {
							if(pw.equals(an_pw)) {
								bw.write("로그인 성공\n");
								bw.flush();
								an="로그인성공";
								System.out.println(an_id+an);
							} else {
								bw.write("로그인 실패 비밀번호를 확인해주세요.\n");
								bw.flush();
							}
						} else {
							bw.write("로그인 실패 아이디를 확인해주세요.\n");
							bw.flush();
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
				
				//예매 
				
//				for(User user : userList) {
//					
//					if(this != user) {
//						user.bw.write(id+"님 로그인에 성공하셨습니다.");
//						user.bw.newLine();
//						user.bw.flush();
//					}
//				}
				
				//사용자가 입력했을 때 메시지를 보낵
				String msg = null;
				while(true) {
					//연결되어 있는 사람의 메세지를 읽고
					msg = br.readLine();
					if(msg == null) {
						break;
					}
//					//모든 사람에게 뿌려준다
////					for(User user : userList) {
////						if(this != user) {//자기 자신이 아니라면!
////							user.bw.write("["+id+"] : "+msg);
////							user.bw.newLine();
////							user.bw.flush();
////						}
////					}
				}
				
			}catch (Exception e) {
				//예외가 발생했다면 해당되는 소켓과 연결이 끊긴것임..
				userList.remove(this);
				try {
					for(User user : userList) {
						user.bw.write(id+"님이 방을 나갔습니다.");
						user.bw.newLine();
						user.bw.flush();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
