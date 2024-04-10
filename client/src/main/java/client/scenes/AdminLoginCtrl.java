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

    @FXML
    private TextField password;

    @Inject
    public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    public void initialize(){
        ServerUtils utils = new ServerUtils();
        int passwordLength = 10;
        String randomPassword = utils.generateRandomPassword(passwordLength);
        this.adminPassword = randomPassword;
        System.out.println("Admin Password: " + randomPassword);
    }
    public void languageSwitch(){
        String password = this.password.getText();

        mainCtrl.changeLanguage();
        mainCtrl.showAdminLogin();

        this.password.setText(password);
    }
/*
    public void language(){
        if(currentLanguage.equals("en")) en();
        else if(currentLanguage.equals("nl")) nl();
    }

    public void en() {
        languageButton.setText("EN");
        enterButton.setText("ENTER");
        backButton.setText("BACK");
        helpButton.setText("HELP");
    }

    public void nl() {
        languageButton.setText("NL");
        enterButton.setText("INVOEREN");
        backButton.setText("TERUG");
        helpButton.setText("HELP");
    }
*/
    public void cancel() {
        System.out.println("Exited admin login");
        clearFields();
        mainCtrl.showStarterPage();
    }

    public void checkPassword() {
        try {
            boolean bypassCredentials = true; // Change this to bypass credentials

            String inputPassword = password.getText();
            boolean passwordMatch = true;

            if (!bypassCredentials) {
                passwordMatch = inputPassword.equals(this.adminPassword);
            }

            if (passwordMatch) {
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
    public void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help & Shortcuts");
        alert.setHeaderText("Application Help and Keyboard Shortcuts");

        StringBuilder content = new StringBuilder();

        content.append("Login:\n")
                .append("- Open the server console to see the randomly generated admin password.\n")
                .append("- Use the password to log in.\n\n");

        content.append("Shortcuts:\n")
                .append("- ENTER: Log-in\n")
                .append("- ESC: Leave the scene\n");

        alert.setContentText(content.toString());

        alert.getDialogPane().setPrefSize(480, 320);
        alert.showAndWait();
    }
    public void backToStart(ActionEvent actionEvent) {
        clearFields();
        mainCtrl.showStarterPage();
    }
}
