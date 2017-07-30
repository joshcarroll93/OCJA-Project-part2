package com.ocja.project.josh.admingui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AdminGUI extends Application {
    
    public TableView<Person> table = new TableView();
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    private final ObservableList<String> comboData = FXCollections.observableArrayList();
    private ComboBox setBalanceComboBox;
    private ComboBox deleteCustomerComboBox;
    private int id;
 
    public static void main(String[] args) {
        launch(args);
    } 
    @Override
    public void start(Stage primaryStage) {
        loginWindow();  
    } 
    public void loginWindow(){
        Stage loginWindowStage = new Stage();
        Scene scene = new Scene(new Group());
        loginWindowStage.setTitle("Login");
        loginWindowStage.setWidth(300);
        loginWindowStage.setHeight(200);
        
        Label statusLabel = new Label();
        statusLabel.setText("Welcome");
        statusLabel.setLayoutX(110);
        statusLabel.setLayoutY(15);
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        Button loginButton = new Button("Login");
        loginButton.setLayoutX(115);
        loginButton.setLayoutY(125);
        loginButton.setOnAction((event) -> {
            if(userNameField.getText().equals("Admin") && passwordField.getText().equals("Admin")){
                statusLabel.setText("Login Success");
                loginWindowStage.close();
                openProgram();
            }else{
                statusLabel.setText("Login Failed");
            }
            userNameField.clear();
            passwordField.clear();
        });
        VBox loginVbox = new VBox(15);
        loginVbox.setLayoutX(70);
        loginVbox.setLayoutY(45);
        
        loginVbox.getChildren().addAll(userNameField, passwordField);
        
        ((Group) scene.getRoot()).getChildren().addAll(statusLabel, loginVbox, loginButton);
        loginWindowStage.setScene(scene);
        loginWindowStage.show();
    }
    public void openProgram(){
        DatabaseQuerys dbQuerys = new DatabaseQuerys();
        
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Project Admin UI");
        stage.setWidth(900);
        stage.setHeight(600);
        
        final Label label = new Label("Customer Database");
        label.setFont(new Font("Veranda", 20));
 
        table.setEditable(true);
 
        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(125);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("ID"));
        
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(125);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Name"));
        
        TableColumn balanceCol = new TableColumn("Balance");
        balanceCol.setMinWidth(125);
        balanceCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Balance"));
        
        TableColumn userNameCol = new TableColumn("UserName");
        userNameCol.setMinWidth(125);
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("UserName"));
        
        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(125);
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("Password"));
        
        dbQuerys.populateTable(data);
        
        table.setItems(data);
        table.getColumns().addAll(idCol, nameCol, balanceCol, userNameCol, passwordCol);
        
        Button refresh = new Button("Refresh");
        refresh.setOnAction((event) -> {
           data.clear();
           dbQuerys.populateTable(data);
        
        });
        final VBox vboxTable = new VBox(10);
        vboxTable.setPadding(new Insets(20, 0, 0, 20));
        vboxTable.getChildren().addAll(label, table, refresh);
        
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField ();
        nameTextField.setPromptText("Enter Name");
        
        Label balanceLabel = new Label("Balance:");
        TextField balanceTextField = new TextField();
        balanceTextField.setPromptText("Enter Balance");
        
        Label userNameLabel = new Label("Username:");
        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter Username");
        
        Label PasswordLabel = new Label("Password");
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Minimun 6 characters");
        
        Button submit = new Button("Submit");
        submit.setOnAction((event) -> {
            System.out.println("submit pressed");
            
            int nameLength = nameTextField.getText().toString().length();
            int balanceLength = balanceTextField.getText().toString().length();
            int uNameLength = userNameTextField.getText().toString().length();
            int passWordLength = passwordTextField.getText().toString().length();
            if(nameLength > 0 && balanceLength > 0 && uNameLength > 0 && passWordLength > 5){
                
                System.out.println("before add");
                dbQuerys.addNewPerson(id, nameTextField, balanceTextField, userNameTextField, passwordTextField);
               
                System.out.println("after add");
                
                nameTextField.clear();
                balanceTextField.clear();
                userNameTextField.clear();
                passwordTextField.clear();
            }
        });
        Label newBalanceLabel = new Label("New Balance");
        TextField newBalanceTextField = new TextField();
        newBalanceTextField.setPromptText("Enter topUp Amount");
        
        Button submitBalance = new Button("Submit");
        submitBalance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
               // double parseNewAmount = Double.parseDouble(nb);
               // System.out.println(parseNewAmount);
                    dbQuerys.updateBalance(newBalanceTextField, setBalanceComboBox);
                    newBalanceTextField.clear();
            }
        });
        
        Label deleteUserLabel = new Label("Delete");
        Button deleteConfirmButton = new Button("Confirm Delete");
        deleteConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                   dbQuerys.deleteUser(deleteCustomerComboBox);
            }
        });
        
        setBalanceComboBox = new ComboBox(comboData);
        setBalanceComboBox.setPromptText("Customers");
        
        deleteCustomerComboBox = new ComboBox(comboData);
        deleteCustomerComboBox.setPromptText("Customers");
        
        VBox vboxAddCustomer = new VBox(10);
        vboxAddCustomer.getChildren().addAll(nameLabel, nameTextField, balanceLabel, balanceTextField, userNameLabel, userNameTextField, PasswordLabel, passwordTextField, submit);
        
        VBox vboxTopUpCustomer = new VBox(10);
        vboxTopUpCustomer.getChildren().addAll(newBalanceLabel, newBalanceTextField, setBalanceComboBox, submitBalance);
        
        VBox vboxDeleteCustomer = new VBox(10);
        vboxDeleteCustomer.getChildren().addAll(deleteUserLabel,deleteCustomerComboBox ,deleteConfirmButton);
        
         //stack pane start//
        VBox vboxOptionsStack = new VBox(10);
        vboxOptionsStack.setPadding(new Insets(20, 0, 0, 20));
        vboxOptionsStack.setLayoutX(675);
        vboxOptionsStack.setLayoutY(50);
        
        Button addCustomerButton = new Button("New Account");
        Button topUpButton = new Button("TopUp Account");
        Button deleteCustomerButton = new Button("Delete Account");
        
        final Pane cardsPane = new StackPane();
       
        final Group card1 = new Group(new Text("Add Customer"));
        card1.getChildren().add(vboxAddCustomer);
        
        final Group card2 = new Group(new Text("Top Up Customer"));
        card2.getChildren().add(vboxTopUpCustomer);
        
        final Group card3 = new Group(new Text("Delete Customer"));
        card3.getChildren().addAll(vboxDeleteCustomer);
        
        
        addCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cardsPane.getChildren().clear();
                cardsPane.getChildren().add(card1);
            }
        });

        topUpButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cardsPane.getChildren().clear();
                cardsPane.getChildren().add(card2);
                comboData.clear();
                dbQuerys.populateComboBox(comboData);
            }
        });
        deleteCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                cardsPane.getChildren().clear();
                cardsPane.getChildren().add(card3);
                comboData.clear();
                dbQuerys.populateComboBox(comboData);
            }
        });
        
        vboxOptionsStack.getChildren().addAll(addCustomerButton, topUpButton, deleteCustomerButton, cardsPane);         
        ((Group) scene.getRoot()).getChildren().addAll(vboxTable,vboxOptionsStack);
        stage.setScene(scene);
        stage.show();   
    }
}


