package Database;

import java.sql.*;

public abstract class Koneksi {
    private final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/tokopulsa_hp";
    private final String USER = "root";
    private final String PASS = "";

    private Connection conn;
    public Statement stmt;
    protected ResultSet value;
    private String query;

    public Koneksi() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    } 
    
    public void setQuery(String sql) {
        this.query = sql;
    }

    public String getQuery() {
        return this.query;
    }

    public void setClose() throws SQLException {
        this.stmt.close();
        conn.close();
    }

    public void execute() throws SQLException {
        stmt.execute(this.query);
    }

    public void fetch() throws SQLException {
        this.value = stmt.executeQuery(this.query);
    }
}