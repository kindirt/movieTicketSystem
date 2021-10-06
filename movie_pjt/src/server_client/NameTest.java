package server_client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NameTest {
	public static void main(String[] args) {
		MovieDao dao = MovieDaoImpl.getInstance();
		
		try {
			List<MovieDto> userList = new ArrayList<MovieDto>();
			
			userList = dao.userFindByName("아");
			
			for(MovieDto user : userList) {
				System.out.println(user);
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
