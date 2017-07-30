/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocja.project.josh.admingui;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Josh
 */
public class DatabaseQuerys {
    
//    private String host = "jdbc:mysql://localhost:3306/world";
//    private String uName = "root";
//    private String uPass = "P4ssw0rddb";
    
    String instanceConnectionName = "secure-bonbon-160709:europe-west1:cashless-project";
    String databaseName = "CashlessProjectInfo";
    String username = "joshc93";
    String password = "P4ssw0rddb";

    String jdbcUrl = String.format(
        "jdbc:mysql://google/%s?cloudSqlInstance=%s&"
        + "socketFactory=com.google.cloud.sql.mysql.SocketFactory",
            databaseName,instanceConnectionName);
    
    Connection connection ;
    Statement statement;
    ResultSet resultSet; 
    double d;
    
    public void populateTable(ObservableList data){
        String query = "SELECT * FROM CashlessProjectInfo.customers";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                Person customer = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                data.add(customer);    
            }
	} catch (SQLException sqle) {
		sqle.printStackTrace();
	} catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
   }
   public void addNewPerson(int id, TextField nameTextField, TextField balanceTextField,TextField userNameTextField, TextField passwordTextField){
        String insertIntoCustomersValues = "insert into CashlessProjectInfo.customers Values(";
        String comma = ",";
        String endInsertCommand = ");";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            statement.executeUpdate(insertIntoCustomersValues + id +comma + "'" +nameTextField.getText().toString() + "'" + comma +  balanceTextField.getText().toString() + comma + "'" + userNameTextField.getText().toString()  + "'" +comma + "'" +passwordTextField.getText().toString() + "'" +endInsertCommand);
            
         } catch (SQLException sqle) {
             sqle.printStackTrace();
         } catch(ClassNotFoundException cnfe){
             cnfe.printStackTrace();
         }
   }
   public void updateBalance(TextField newBalanceTextField, ComboBox setBalanceComboBox){
        String updateCustomersSetBalance = "update CashlessProjectInfo.customers set balance = ";
       
        String nb = newBalanceTextField.getText().toString();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            
            getBalanceFromDatabase(setBalanceComboBox);
            
            double newBalance = Double.parseDouble(nb) + d;
            System.out.println(updateCustomersSetBalance + newBalance + " where name = '" + setBalanceComboBox.getValue().toString() + "';");

            statement.executeUpdate(updateCustomersSetBalance + newBalance + " where name = '" + setBalanceComboBox.getValue().toString() + "';");
            
        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } catch(ClassNotFoundException cnfe){
             cnfe.printStackTrace();
        }
   }
   public double getBalanceFromDatabase(ComboBox setBalanceComboBox){
        String selectOldBalance = "SELECT BALANCE FROM CashlessProjectInfo.customers";
       try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectOldBalance + " where name = '" + setBalanceComboBox.getValue().toString() + "';");
            
            if(resultSet.next()){
               d = resultSet.getDouble("BALANCE");
            }else{
                throw new SQLException("No Balance Found");
            }
       }catch(SQLException sqle){
           sqle.printStackTrace();
       } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
       return d;
   }
   public void populateComboBox(ObservableList comboData){
	try {
            String nameQuery = "Select name from CashlessProjectInfo.customers";
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(nameQuery);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                comboData.add(resultSet.getString("Name"));
            }
	} catch (SQLException sqle) {
            sqle.printStackTrace();
        }
   }
   public void deleteUser(ComboBox deleteCustomerComboBox){
    
           String deleteFrom = "DELETE FROM CashlessProjectInfo.customers WHERE NAME = '";
           String endStatement = "';";
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            statement.executeUpdate(deleteFrom + deleteCustomerComboBox.getValue().toString() +endStatement);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
   }
}
