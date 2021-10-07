package server_client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class MovieDaoImpl implements MovieDao {
	
	private static MovieDaoImpl instance = new MovieDaoImpl();
	
	private MovieDaoImpl() {}
	
	public static MovieDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int userInsert(Customer customer) throws ClassNotFoundException, SQLException {
		//customer_id, user_id, user_pw, name, phone)
		String sql = "insert into customer value(?,?,?,?,?)";
		
		try(Connection conn = DbConn.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setInt(1, 0);
			pst.setString(2, customer.getUser_id());
			pst.setString(3, customer.getUser_pw());
			pst.setString(4, customer.getName());
			pst.setString(5, customer.getPhone());
			
			return pst.executeUpdate();
		}
	}
	
	@Override
	public List<Customer> userFindByName(String user_id) throws ClassNotFoundException, SQLException {
		String sql = "select * from customer where user_id = ?";
		
		try(Connection conn = DbConn.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, user_id);
			
			try(ResultSet rs = pst.executeQuery()){
				List<Customer> userList = new ArrayList<Customer>();
				
				while(rs.next()) {
					userList.add(new Customer(rs.getInt("customer_id"), 
						rs.getString("user_id"), 
						rs.getString("user_pw"), 
						rs.getString("name"), 
						rs.getString("phone")));
				}
				return userList;
			}
		}
	}

	@Override
	public String userSingUpLog(Customer customer) throws IOException {
		LocalDateTime today = LocalDateTime.now();
		Date nowDate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH시 mm분 ss초");
		File pathFolder = new File("src/log/"+today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		if (!pathFolder.exists()) {
			pathFolder.mkdirs(); 
		}
		File logFile = new File(pathFolder, "singUpLog.txt"); 
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))){
			
			bw.append(customer.toString()+" "+sf.format(nowDate)+" \r");
			bw.flush();
			return "회원가입성공 로그 기록!";
		}
	}

	@Override
	public String userLoginLog(Customer customer) throws IOException {
		LocalDateTime today = LocalDateTime.now();
		Date nowDate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH시 mm분 ss초");
		File pathFolder = new File("src/log/"+today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		if (!pathFolder.exists()) {
			pathFolder.mkdirs();
		}
		File logFile = new File(pathFolder, "loginLog.txt"); 
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))){
			
			bw.append(customer.toString()+" "+sf.format(nowDate)+" \r");
			bw.flush();
			return "로그인성공 로그 기록!";
		}
	}

	@Override
	public List<Customer> reservationLog() {
		return null;
	}
}
