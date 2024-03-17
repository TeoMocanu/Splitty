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

    public MainCtrl() {
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStarterPage();
        primaryStage.show();
    }

    //TODO implement
    public Event getEvent(){
        return null;
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

    public void invitation(Pair<InvitationCtrl, Parent> invitation) {
        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());
    }

    public void changeServer(Pair<ChangeServerCtrl, Parent> changeServer){
        this.changeServerCtrl = changeServer.getKey();
        this.changeServer = new Scene(changeServer.getValue());
    }

    public void showStarterPage() {
        primaryStage.setTitle("Starter Page");
        primaryStage.setScene(starterPage);
    }

    public void showAdminLogin() {
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(adminLogin);
        adminLogin.setOnKeyPressed(e -> adminLoginCtrl.keyPressed(e));
    }
    public void showAdminOverview() {
        primaryStage.setTitle("Admin Overview");
        primaryStage.setScene(adminOverview);
        adminOverview.setOnKeyPressed(e -> adminOverviewCtrl.keyPressed(e));
    }

    public void showAddExpense(Event event, boolean en) {
        primaryStage.setTitle("Add/Edit Expense");
        primaryStage.setScene(addExpense);
        addExpenseCtrl.initialize(en, event);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showContactDetails(Event event, boolean en){
        primaryStage.setTitle("Add/Edit Participant");
        primaryStage.setScene(contactDetails);
        contactDetailCtrl.initialize(event, en);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showAddInvitation(){
        primaryStage.setTitle("Invitation");
        primaryStage.setScene(invitation);
        invitation.setOnKeyPressed(e -> invitationCtrl.keyPressed(e));
    }

    public void showChangeServer(boolean en) {
        primaryStage.setTitle("Server");
        primaryStage.setScene(changeServer);
        changeServerCtrl.language(en);
    }

    public StarterPageCtrl getStarterPageCtrl() { return starterPageCtrl; }
}