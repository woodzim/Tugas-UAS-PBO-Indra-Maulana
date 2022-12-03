/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import java.sql.SQLException;

/**
 *
 * @author RIZKY
 */
import Database.Koneksi;
public class Login_code extends Koneksi{
    public Login_code() throws ClassNotFoundException, SQLException {
        super();
    }

    public boolean authentication(String username, String password) throws SQLException {
        String sql = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'", username, password);
        this.setQuery(sql);
        this.fetch();
        
        while(this.value.next()) {
            if(this.value.getString("username") != null) {
                return true;
            }
        }
        
        return false;
    }

    public boolean validation(String username) {
        return true;
    }

    public void logout() {
        
    }
}
