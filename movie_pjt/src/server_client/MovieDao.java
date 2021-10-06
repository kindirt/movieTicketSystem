package server_client;

import java.sql.SQLException;
import java.util.List;

public interface MovieDao {

	public List<MovieDto> userFindByName(String user_id) throws ClassNotFoundException, SQLException;
	
}
