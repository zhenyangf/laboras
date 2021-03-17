package fms.fxController;


import javafx.fxml.Initializable;
import fms.model.FinanceManagementSystem;


import java.net.URL;
import java.util.ResourceBundle;


public class CategoryManagement implements Initializable {

private FinanceManagementSystem fms;
public void setFms(FinanceManagementSystem fms){
    this.fms = fms;
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
