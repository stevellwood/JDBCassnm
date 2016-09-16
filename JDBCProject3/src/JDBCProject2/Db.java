package JDBCProject2;
import java.sql.*;

public class Db {
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;
	public String url = null;
	public String usr = null;
	public String pwd = null;
	
	public ResultSet getSqlResultSet(String sql, Object... parms ) throws SQLException {
		if(!isConnected())
			return null;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int idx = 0; idx < parms.length; idx++)
			pstmt.setObject(idx+1, parms[idx]);
		rs = pstmt.executeQuery();
		return rs;
	}
	public ResultSet getSqlResultSet(PreparedStatement pstmt) throws SQLException {
		if(!isConnected())
			return null;
		rs = pstmt.executeQuery();
		return rs;
	}
	public ResultSet getSqlResultSet(String sql) throws SQLException {
		if(!isConnected())
			return null;
		pstmt = conn.prepareStatement(sql);
		rs = getSqlResultSet(pstmt);
		return rs;
	}
	public void connect() throws SQLException {
		if(strIsNullOrEmpty(this.url) || strIsNullOrEmpty(this.usr) || strIsNullOrEmpty(this.pwd))
			throw new SQLException("Either URL, USR, or PWD is null or empty!");
		conn = DriverManager.getConnection(this.url, this.usr, this.pwd);
	}
	public Db(String url, String usr, String pwd) throws SQLException {
		this.url = url;
		this.usr = usr;
		this.pwd = pwd;
		connect();
	}
	public Db() {
	}
	public void close() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private boolean isConnected() {
		if(this.conn == null)
			return false;
		return true;
	}
	private boolean strIsNullOrEmpty(String str) {
		return str == null | str.length() == 0;
	}
}
