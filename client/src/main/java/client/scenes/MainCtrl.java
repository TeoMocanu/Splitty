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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addQuoteCtrl;
    private Scene addQuote;
    private AddExpenseCtrl addExpenseCtrl;
    private Scene addExpense;

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;

        showOverview();
        primaryStage.show();
    }

    public void addQuote(Pair<AddQuoteCtrl, Parent> addQuote){
        this.addQuoteCtrl = addQuote.getKey();
        this.addQuote = new Scene(addQuote.getValue());
    }

    public void addOverview(Pair<QuoteOverviewCtrl, Parent> overview){
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
    }

    public void addExpense(Pair<AddExpenseCtrl, Parent> addExpense){
        this.addExpenseCtrl = addExpense.getKey();
        this.addExpense = new Scene(addExpense.getValue());
    }

    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    public void showAddQuote() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(addQuote);
        addQuote.setOnKeyPressed(e -> addQuoteCtrl.keyPressed(e));
    }

    public void showAddExpense() {
        primaryStage.setTitle("Add/Edit Expense");
        primaryStage.setScene(addExpense);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }
}