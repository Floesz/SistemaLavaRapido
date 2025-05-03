package util;

import exceptions.DBExcpetion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class connection {
    private static Properties props = new Properties();

    static {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new DBExcpetion("Não foi possível carregar config.properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        String url      = props.getProperty("db.url");
        String user     = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DBExcpetion("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }
}
