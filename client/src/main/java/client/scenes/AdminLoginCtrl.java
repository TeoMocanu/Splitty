package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AdminLoginCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String adminPassword;

    @FXML
    private TextField password;
    @Inject
    public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    @FXML
    private void initialize(){
        ServerUtils utils = new ServerUtils();
        String randomPassword = utils.generateRandomPassword(16); // Generates a 16-byte password, encoded in Base64
        this.adminPassword=randomPassword;
        System.out.println("Admin Password: " + randomPassword);
    }
    public void cancel() {
        System.out.println("Existed admin login");
        clearFields();
        mainCtrl.showOverview();
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
                System.out.println("Admin credentials are wrong, restart the app to try again");
                mainCtrl.showOverview();
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
}
