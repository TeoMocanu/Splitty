package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.Map;

public class AdminOverviewCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String currentLanguage;

    @FXML
    private TableView<TableRowData> tableView;

    @FXML
    public Button serverInfoButton;

    @FXML
    public Button languageButton;

    @FXML
    private Button backButton;
    /**
     * A class to hold the data for each row in the TableView
     * This could be a separate class, but for simplicity, I've included it here.
     */
    public class TableRowData {
        private final SimpleStringProperty column1;
        private final SimpleStringProperty column2;

        public TableRowData(String column1, String column2) {
            this.column1 = new SimpleStringProperty(column1);
            this.column2 = new SimpleStringProperty(column2);
        }

        public String getColumn1() {
            return column1.get();
        }

        public void setColumn1(String value) {
            column1.set(value);
        }

        public String getColumn2() {
            return column2.get();
        }

        public void setColumn2(String value) {
            column2.set(value);
        }
    }



    public void initialize() {
        this.currentLanguage = "EN";

        ObservableList<TableRowData> data = FXCollections.observableArrayList();

        // TODO connect to the event database and set the rows based on that
        data.add(new TableRowData("Row 1 Col 1", "Row 1 Col 2"));
        data.add(new TableRowData("Row 2 Col 1", "Row 2 Col 2"));

        tableView.setItems(data);
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
        serverInfoButton.setText("Server Info");
        backButton.setText("EXIT");

    }
    public void nl(){
        languageButton.setText("NL");
        serverInfoButton.setText("Server Informatie");
        backButton.setText("AFSLUITEN");
    }

    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }
    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }
    public void ok() {
        try {
            // TODO: Add admin functionality, like seeing server instances


        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }
    private void clearFields() {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                exitAdminOverview();
                break;
            default:
                break;
        }
    }

    public void showServerInfo() {
        try {
            Map<String, Object> serverInfo = server.fetchServerInfo();
            System.out.println("Server Info: " + serverInfo);
        } catch (Exception e) {
            System.out.println("Failed to fetch server info: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exitAdminOverview() {
        mainCtrl.showStarterPage();
    }
}
