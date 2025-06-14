package com.alfa.experience.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {

    private static final String URL = "jdbc:mysql://localhost:3306/alfaexperience_db?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager
                    .getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("[DAO Connection] " + e.getMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }


}
