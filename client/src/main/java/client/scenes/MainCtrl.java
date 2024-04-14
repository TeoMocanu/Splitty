/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;


import client.MyFXML;
import client.utils.LanguageUtils;
import commons.Expense;
import commons.Participant;
import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
public class MainCtrl {

    Stage primaryStage;
    private StarterPageCtrl starterPageCtrl;
    private Scene starterPage;
    private InvitationCtrl invitationCtrl;
    private Scene invitation;
    private AddExpenseCtrl addExpenseCtrl;
    private Scene addExpense;
    private AdminLoginCtrl adminLoginCtrl;
    private Scene adminLogin;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private ContactDetailCtrl contactDetailCtrl;
    private Scene contactDetails;
    private ChangeServerCtrl changeServerCtrl;
    private Scene changeServer;
    private EventOverviewCtrl eventOverviewCtrl;
    private Scene eventOverview;
    private EditTitleCtrl editTitleCtrl;
    private Scene editTitle;
    private OpenDebtsCtrl openDebtsCtrl;
    private Scene openDebts;
    private StatisticsCtrl statisticsCtrl;
    private Scene statistics;
    private MyFXML myFXML;

    public MainCtrl() {
    }

    public void load(Stage primaryStage, MyFXML myFXML){
        this.myFXML = myFXML;
        this.primaryStage = primaryStage;

        var addExpense = myFXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpense.fxml");
        var starterPage = myFXML.load(StarterPageCtrl.class, "client", "scenes", "StarterPage.fxml");
        var adminOverview = myFXML.load(AdminOverviewCtrl.class, "client", "scenes", "AdminOverview.fxml");
        var adminLogin = myFXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var contactDetails = myFXML.load(ContactDetailCtrl.class, "client", "scenes", "ContactDetail.fxml");
        var changeServer = myFXML.load(ChangeServerCtrl.class, "client", "scenes", "ChangeServer.fxml");
        var eventOverview = myFXML.load(EventOverviewCtrl.class, "client", "scenes", "EventOverview.fxml");
        var invitation = myFXML.load(InvitationCtrl.class, "client", "scenes", "Invitation.fxml");
        var editTitle = myFXML.load(EditTitleCtrl.class, "client", "scenes", "EditTitle.fxml");
        var openDebts = myFXML.load(OpenDebtsCtrl.class, "client", "scenes", "OpenDebts.fxml");
        var statistics = myFXML.load(StatisticsCtrl.class, "client", "scenes", "Statistics.fxml");

        this.starterPage(starterPage);
        this.adminLogin(adminLogin);
        this.adminOverview(adminOverview);
        this.addExpense(addExpense);
        this.contactDetails(contactDetails);
        this.changeServer(changeServer);
        this.eventOverview(eventOverview);
        this.invitation(invitation);
        this.editTitle(editTitle);
        this.settleDebts(openDebts);
        this.statistics(statistics);

        primaryStage.setOnCloseRequest(e -> {
            starterPage.getKey().stop();
        });
    }

    public void reload(){;
        var addExpense = myFXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpense.fxml");
        var starterPage = myFXML.load(StarterPageCtrl.class, "client", "scenes", "StarterPage.fxml");
        var adminOverview = myFXML.load(AdminOverviewCtrl.class, "client", "scenes", "AdminOverview.fxml");
        var adminLogin = myFXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var contactDetails = myFXML.load(ContactDetailCtrl.class, "client", "scenes", "ContactDetail.fxml");
        var changeServer = myFXML.load(ChangeServerCtrl.class, "client", "scenes", "ChangeServer.fxml");
        var eventOverview = myFXML.load(EventOverviewCtrl.class, "client", "scenes", "EventOverview.fxml");
        var invitation = myFXML.load(InvitationCtrl.class, "client", "scenes", "Invitation.fxml");
        var editTitle = myFXML.load(EditTitleCtrl.class, "client", "scenes", "EditTitle.fxml");
        var openDebts = myFXML.load(OpenDebtsCtrl.class, "client", "scenes", "OpenDebts.fxml");
        var statistics = myFXML.load(StatisticsCtrl.class, "client", "scenes", "Statistics.fxml");

        this.starterPage(starterPage);
        this.adminLogin(adminLogin);
        this.adminOverview(adminOverview);
        this.addExpense(addExpense);
        this.contactDetails(contactDetails);
        this.changeServer(changeServer);
        this.eventOverview(eventOverview);
        this.invitation(invitation);
        this.editTitle(editTitle);
        this.settleDebts(openDebts);
        this.statistics(statistics);

        primaryStage.setOnCloseRequest(e -> {
            starterPage.getKey().stop();
        });
    }

    public void changeLanguage(){
        LanguageUtils.switchLanguage();
        reload();
    }

    public void initialize(Stage primaryStage) {
        showStarterPage();
        primaryStage.show();
    }

    public void starterPage(Pair<StarterPageCtrl, Parent> starterPage){
        this.starterPageCtrl = starterPage.getKey();
        this.starterPage = new Scene(starterPage.getValue());
    }

    public void adminLogin(Pair<AdminLoginCtrl, Parent> adminLogin){
        this.adminLoginCtrl = adminLogin.getKey();
        this.adminLogin = new Scene(adminLogin.getValue());
    }
    public void adminOverview(Pair<AdminOverviewCtrl, Parent> adminOverview){
        this.adminOverviewCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());
    }

    public void contactDetails(Pair<ContactDetailCtrl, Parent> contactDetails){
        this.contactDetailCtrl = contactDetails.getKey();
        this.contactDetails = new Scene(contactDetails.getValue());
    }

    public void addExpense(Pair<AddExpenseCtrl, Parent> addExpense){
        this.addExpenseCtrl = addExpense.getKey();
        this.addExpense = new Scene(addExpense.getValue());
    }

    public void eventOverview(Pair<EventOverviewCtrl, Parent> eventOverview){
        this.eventOverviewCtrl = eventOverview.getKey();
        this.eventOverview = new Scene(eventOverview.getValue());
    }

    public void invitation(Pair<InvitationCtrl, Parent> invitation) {
        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());
    }

    public void changeServer(Pair<ChangeServerCtrl, Parent> changeServer){
        this.changeServerCtrl = changeServer.getKey();
        this.changeServer = new Scene(changeServer.getValue());
    }

    public void editTitle(Pair<EditTitleCtrl, Parent> editTitle){
        this.editTitleCtrl = editTitle.getKey();
        this.editTitle = new Scene(editTitle.getValue());
    }

    public void settleDebts(Pair<OpenDebtsCtrl, Parent> openDebts){
        this.openDebtsCtrl = openDebts.getKey();
        this.openDebts = new Scene(openDebts.getValue());
    }

    public void statistics(Pair<StatisticsCtrl, Parent> statistics){
        this.statisticsCtrl = statistics.getKey();
        this.statistics = new Scene(statistics.getValue());
    }

    public void showStarterPage() {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle(getString("starterPage"));
        primaryStage.setScene(starterPage);
        starterPageCtrl.initialize();
        starterPage.setOnKeyPressed(e -> starterPageCtrl.keyPressed(e));
        primaryStage.setHeight(550.0);
        primaryStage.setWidth(438.0);
        //scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("high-contrast-theme.css").toExternalForm());;
    }

    public void showAdminLogin() {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("adminLogin"));
        primaryStage.setScene(adminLogin);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        adminLoginCtrl.initialize();
        adminLogin.setOnKeyPressed(e -> adminLoginCtrl.keyPressed(e));
        adminLoginCtrl.setupShortcuts(adminLogin);
    }
    public void showAdminOverview() {
        primaryStage.setTitle(getString("adminOverview"));
        primaryStage.setScene(adminOverview);
        adminOverviewCtrl.initialize();
        adminOverview.setOnKeyPressed(e -> adminOverviewCtrl.keyPressed(e));
        // Call the method to load the shortcuts when the scene is shown
        adminOverviewCtrl.setupShortcuts(adminOverview);
    }

    public void showAddExpense(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("addExpense"));
        primaryStage.setScene(addExpense);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        addExpenseCtrl.initialize(event);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showEditExpense(Event event, Expense expense) {
        primaryStage.setTitle(getString("editExpense"));
        primaryStage.setScene(addExpense);
        addExpenseCtrl.initialize(event, expense);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showEventOverview(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle(getString("createEditEvent"));
        primaryStage.setScene(eventOverview);
        primaryStage.sizeToScene();
        eventOverviewCtrl.initialize(event);
        eventOverview.setOnKeyPressed(e ->eventOverviewCtrl.keyPressed(e));
    }


    public void showInvitation(Event event){
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(220);
        primaryStage.setTitle(getString("invitation"));
        primaryStage.setScene(invitation);
        primaryStage.sizeToScene();
        invitationCtrl.initialize(event);
        invitation.setOnKeyPressed(e -> invitationCtrl.keyPressed(e));
    }

    public void showContactDetailsAdd(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("addParticipant"));
        primaryStage.setScene(contactDetails);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        contactDetailCtrl.initialize(event);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showContactDetailsEdit(Participant participant) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("editParticipant"));
        primaryStage.setScene(contactDetails);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        contactDetailCtrl.initialize(participant.getEvent(), participant);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showOpenDebts(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setTitle(getString("settleDebts"));
        primaryStage.setScene(openDebts);
        primaryStage.sizeToScene();
        primaryStage.setMinWidth(openDebts.getWidth());
        primaryStage.setMinHeight(openDebts.getHeight());
        openDebtsCtrl.initialize(event);
        openDebts.setOnKeyPressed(e ->openDebtsCtrl.keyPressed(e));
    }

    public void showStatistics(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setTitle(getString("statistics"));
        primaryStage.setScene(statistics);
        statisticsCtrl.initialize(event);
        statistics.setOnKeyPressed(e ->statisticsCtrl.keyPressed(e));
    }

    public void showChangeServer() {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("changeServer"));
        primaryStage.setScene(changeServer);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        changeServerCtrl.initialize();
    }

    public void showEditTitle(Event event) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle(getString("editTitle"));
        primaryStage.setScene(editTitle);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        editTitleCtrl.initialize(event);
        editTitle.setOnKeyPressed(e ->editTitleCtrl.keyPressed(e));
    }

    public StarterPageCtrl getStarterPageCtrl() {
        return starterPageCtrl;
    }

    public String getString(String key) {
        return LanguageUtils.get(key);
    }

    public void setPrimaryStageTitle(String title) {
        primaryStage.setTitle(title);
    }
}