/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mysql3;

/**
 *
 * @author andre
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    private static final String URL = "jdbc:mysql://localhost:3306/registros";
    private static final String USER = "root";
    private static final String PASSWORD = "Alexander131719";

    public static Connection conectar() throws SQLException { // Debe ser público y estático
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

