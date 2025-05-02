package util;

import exceptions.DBExcpetion;

import java.sql.*;

public class connection {
    private static final String URL = "jdbc:mysql://localhost:3306/lava_rapido";
    private static final String USER = "root";
    private static final String PASSWORD = "Gfmm18022006@";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DBExcpetion("Não foi possível conectar ao banco de dados!");
        }
    }
}
