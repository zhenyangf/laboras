package fms.fxController;

import fms.model.CompanyUser;
import fms.model.EmployeeUser;
import fms.model.FinanceManagementSystem;
import fms.model.User;
import fms.hibernate.hibernateControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginPage implements Initializable {
    @FXML
    public Button signUp;
    @FXML
    public Button SignIn;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    @FXML
    public PasswordField signUpPswField;
    @FXML
    public TextField signupField;
    @FXML
    public TextField contactField;
    @FXML
    public ToggleButton companyToggle;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public Label surnameLabel;

    public FinanceManagementSystem getFms() {
        return fms;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    private FinanceManagementSystem fms;

    public void login(ActionEvent actionEvent){

        User user = fms.getSystemUsers().stream().filter(potentialUser -> {
            return potentialUser.getUserID().equals(loginField.getText())
                    && potentialUser.getPassword().equals(pswField.getText());
        }).findFirst().orElse(null);


        if (user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your username or password is incorrect.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

        }
        else {
            try {
                loadMainWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public void onCompanyToggle() throws IOException {
        boolean isCompany = companyToggle.isSelected();
        companyToggle.setText(isCompany ? "Company" : "User");

        //nameLabel.setText(isCompany ? "Company name" : "Name");
        //contactLabel.setText(isCompany ? "Responsible person name" : "Contact info");

        surnameLabel.setVisible(!isCompany);
        surnameField.setVisible(!isCompany);
    }
    public void loadMainWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainFinanceManagementWindow.fxml"));
        Parent root = loader.load();

        MainFinanceManagementWindow mainFinanceManagementWindow = loader.getController();
        mainFinanceManagementWindow.setFms(fms);

        Stage stage = (Stage) SignIn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void register(ActionEvent actionEvent) throws IOException, Exception {
        String userName = signupField.getText();
        String password = signUpPswField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String contactInfo = contactField.getText();
        boolean isCompany = companyToggle.isSelected();

        boolean isAtleastOneEmpty = false;

        if (userName.equals("")) {
            isAtleastOneEmpty = true;

        }
        else if (password.equals("")) {
            isAtleastOneEmpty = true;

        } else if (name.equals("")) {
            isAtleastOneEmpty = true;
        } else if (contactInfo.equals("")) {
            isAtleastOneEmpty = true;
        }
        else if(!isCompany && surname.equals("")){
                isAtleastOneEmpty = true;
            }


        if (isAtleastOneEmpty) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("All fields must be filled");
            alert.setContentText("Please fill all of the needed information");

            alert.showAndWait();
            return;
        }

        for(User user : fms.getSystemUsers()){
            if(user.getUserID().equals(userName)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed to create user with that ID");
                alert.setHeaderText("Your selected user ID is unavailable");
                alert.setContentText("Please try something else");

                alert.showAndWait();
                return;
            }

        }

        User user;
        if (isCompany) {
            user = new CompanyUser(userName, password, name,contactInfo, fms);
        } else {
            user = new EmployeeUser(userName, password,name,surname, contactInfo, fms);
        }
        fms.getSystemUsers().add(user);
        hibernateControl.users.create(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
