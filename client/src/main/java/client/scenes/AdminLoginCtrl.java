package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AdminLoginCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String adminPassword;

    @FXML
    private Button backButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button enterButton;

    @FXML
    private Button languageButton;

    private String currentLanguage;

    @FXML
    private TextField password;
    @Inject
    public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    @FXML
    private void initialize(){
        currentLanguage = "EN";
        languageButton.setText("EN");
        ServerUtils utils = new ServerUtils();
        String randomPassword = utils.generateRandomPassword(16); // Generates a 16-byte password, encoded in Base64
        this.adminPassword=randomPassword;
        System.out.println("Admin Password: " + randomPassword);
    }
    public void language(){
        if(currentLanguage.equals("EN")){
            currentLanguage = "NL";
            nl();
        }
        else{
            currentLanguage = "EN";
            en();
        }
    }
    public void en(){
        languageButton.setText("EN");
        enterButton.setText("ENTER");
        backButton.setText("BACK");
        helpButton.setText("HELP");
    }
    public void nl(){
        languageButton.setText("NL");
        enterButton.setText("INVOEREN");
        backButton.setText("TERUG");
        helpButton.setText("HELP");
    }
    public void cancel() {
        System.out.println("Exited admin login");
        clearFields();
        mainCtrl.showStarterPage();
    }
    public void checkPassword() {
        try {
            String inputPassword = password.getText();
            boolean passwordMatch = false;
            if(inputPassword.equals(this.adminPassword)) {
                passwordMatch=true;
            }
            if(passwordMatch) {
                System.out.println("Welcome, admin");
                mainCtrl.showAdminOverview();

            }
            else {
                System.out.println("Admin credentials are wrong, restart the session to try again");
                mainCtrl.showStarterPage();
            }
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
    }
    private void clearFields() {
        password.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                checkPassword();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    public void backToStart(ActionEvent actionEvent) {
        clearFields();
        mainCtrl.showStarterPage();
    }
}
