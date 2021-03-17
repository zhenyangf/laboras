package fms;

import fms.model.FinanceManagementSystem;
import fms.fxController.LoginPage;
import fms.hibernate.hibernateControl;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {

 public static FinanceManagementSystem fms = new FinanceManagementSystem();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FinanceManagementSystem fms;
        List<FinanceManagementSystem> fmsList = hibernateControl.fms.getfmsList();
        if (fmsList.size() == 0) {
            fms = new FinanceManagementSystem();
            hibernateControl.fms.create(fms);
        } else {
            fms = fmsList.get(0);
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
        Parent root = loader.load();
        LoginPage loginPage = loader.getController();
        loginPage.setFms(fms);
        primaryStage.setTitle("Fms");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
