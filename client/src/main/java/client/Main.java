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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        var addExpense = FXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpense.fxml");
        var starterPage = FXML.load(StarterPageCtrl.class, "client", "scenes", "StarterPage.fxml");
        var contactDetail = FXML.load(ContactDetailCtrl.class, "client", "scenes", "ContactDetail.fxml");
        var adminOverview = FXML.load(AdminOverviewCtrl.class, "client", "scenes", "AdminOverview.fxml");
        var adminLogin = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var contactDetails = FXML.load(ContactDetailCtrl.class, "client", "scenes", "ContactDetail.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        mainCtrl.starterPage(starterPage);
        mainCtrl.adminLogin(adminLogin);
        mainCtrl.adminOverview(adminOverview);
        mainCtrl.addExpense(addExpense);
        mainCtrl.contactDetails(contactDetails);

        mainCtrl.initialize(primaryStage);
    }
}