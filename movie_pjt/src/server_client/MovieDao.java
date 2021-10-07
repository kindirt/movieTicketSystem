package server_client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface MovieDao {

	//회원가입시 DB에 추가 
	public int userInsert(Customer customer) throws ClassNotFoundException, SQLException;
	
	//로그인시 ID 확인 
	public List<Customer> userFindByName(String user_id) throws ClassNotFoundException, SQLException;

	
	//예매
	
	
	//회원가입 후 로그 추가 singUpLog.txt (파일 입출력)
	public String userSingUpLog(Customer customer) throws IOException; 
	
	//로그인 후 로그 추가 loginLog.txt (파일 입출력)
	public String userLoginLog(Customer customer) throws IOException; 
	
	//예매 및 결제 후 로그 추가 reservationLog.txt (파일 입출력)
	public List<Customer> reservationLog(); 
}
