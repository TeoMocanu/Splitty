package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AdminLoginCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String adminPassword;

    @FXML
    private ImageView flagView;

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

        flagView.setImage(mainCtrl.getFlag());
    }
    public void languageSwitch(){
        String password = this.password.getText();

        mainCtrl.changeLanguage();
        mainCtrl.showAdminLogin();

        this.password.setText(password);

        flagView.setImage(mainCtrl.getFlag());
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

    public void backToStart(ActionEvent actionEvent) {
        clearFields();
        mainCtrl.showStarterPage();
    }
}
