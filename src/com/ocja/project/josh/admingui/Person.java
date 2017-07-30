/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocja.project.josh.admingui;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Josh
 */
public class Person {
   
        private int id;
        private String name;
        private double balance;
        private String userName;
        private String password;
 
        public Person(int id, String name, double balance, String userName, String password) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            this.userName = userName;
            this.password = password;
        }
 
        public int getID() {
            return id;
        }
 
        public void setID(int id) {
            this.id = id;
        }
 
        public String getName() {
            return name;
        }
 
        public void setName(String name) {
            this.name = name;
        }
 
        public double getBalance() {
            return balance;
        }
 
        public void setBalance(double balance) {
            this.balance = balance;
        }
        
        public String getUserName(){
            return userName;
        }
        
        public void setUserName(String userName){
            this.userName = userName;
        }
        
        public String getPassword(){
            return password;
        }
        public void setPassword(String password){
            this.password = password;
        }
    }

