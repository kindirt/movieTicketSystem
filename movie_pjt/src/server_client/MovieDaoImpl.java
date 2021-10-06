package server_client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
	
	private static MovieDaoImpl instance = new MovieDaoImpl();
	
	private MovieDaoImpl() {}
	
	public static MovieDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<MovieDto> userFindByName(String user_id) throws ClassNotFoundException, SQLException {
		String sql = "select * from customer where user_id like ?";
		
		try(Connection conn = DbConn.getConn();
			PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, "%"+user_id+"%");
			
			try(ResultSet rs = pst.executeQuery()){
				
				List<MovieDto> userList = new ArrayList<MovieDto>();
				while(rs.next()) {
					
					userList.add(new MovieDto(rs.getInt("customer_id"), 
							rs.getString("user_id"), 
							rs.getString("user_pw"), 
							rs.getString("name"), 
							rs.getString("phone")));
				}
				
				return userList;
			}
			
		}
	}
}
