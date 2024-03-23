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


import commons.Participant;
import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

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


    public MainCtrl() {
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStarterPage(true);
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

    public void showStarterPage(boolean en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle("Starter Page");
        primaryStage.setScene(starterPage);
        starterPageCtrl.initialize(en);
        starterPage.setOnKeyPressed(e -> starterPageCtrl.keyPressed(e));
    }

    public void showAdminLogin(boolean en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(adminLogin);
        adminLoginCtrl.initialize(en);
        adminLogin.setOnKeyPressed(e -> adminLoginCtrl.keyPressed(e));
    }
    public void showAdminOverview(boolean en) {
        primaryStage.setTitle("Admin Overview");
        primaryStage.setScene(adminOverview);
        adminOverviewCtrl.initialize(en);
        adminOverview.setOnKeyPressed(e -> adminOverviewCtrl.keyPressed(e));
    }

    public void showAddExpense(Event event, boolean en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Add/Edit Expense");
        primaryStage.setScene(addExpense);
        addExpenseCtrl.initialize(event, en);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showEventOverview(Event event, boolean en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle("Create/Edit Event");
        primaryStage.setScene(eventOverview);
        eventOverviewCtrl.initializeEvent(event, en);
        eventOverview.setOnKeyPressed(e ->eventOverviewCtrl.keyPressed(e));
    }


    public void showInvitation(Event event, boolean en){
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(200);
        primaryStage.setTitle("Invitation");
        primaryStage.setScene(invitation);
        invitationCtrl.initialize(event, en);
        invitation.setOnKeyPressed(e -> invitationCtrl.keyPressed(e));
    }

    public void showContactDetailsAdd(Event event, boolean en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Add Participant");
        primaryStage.setScene(contactDetails);
        contactDetailCtrl.initialize(event, en);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showContactDetailsEdit(Participant participant, boolean en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Edit Participant");
        primaryStage.setScene(contactDetails);
        contactDetailCtrl.initialize(participant.getEvent(), en);
        contactDetailCtrl.setParticipant(participant);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));

    }

    public void showOpenDebts(Event event, boolean en) {
        //TODO when page exists :)
    }

    public void showChangeServer(boolean en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Server");
        primaryStage.setScene(changeServer);
        changeServerCtrl.initialize(en);
    }

    public StarterPageCtrl getStarterPageCtrl() {
        return starterPageCtrl;
    }
}