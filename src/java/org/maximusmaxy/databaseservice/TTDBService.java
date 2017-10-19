/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.maximusmaxy.databaseservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Max
 */
@WebService(serviceName = "TTDBService")
public class TTDBService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getCollection")
    public String getCollection(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Getting Driver!");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Getting Connection!");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Triple_Triad_Database", "root", "");
            System.out.println("Getting Statement");
            statement = connection.prepareStatement("select collection from users "
                    + "where username = ? "
                    + "and password = ?;");
            System.out.println("setting prepared statement");
            statement.setString(1, username);
            statement.setString(2, password);
            System.out.println("executing query!");
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("returning null");
                return null;
            } else {
                System.out.println("success!");
                return resultSet.getString("collection");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }
}
