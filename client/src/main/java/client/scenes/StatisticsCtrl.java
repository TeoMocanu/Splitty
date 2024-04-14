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

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.*;

public class StatisticsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private List<Expense> expenseList;

    @FXML
    private Label title;
    @FXML
    private Label eventTitle;
    @FXML
    private Button abortButton;
    @FXML
    private Label totalCostTitle;
    @FXML
    private Label chartLabel;
    @FXML
    private PieChart chart;

    @Inject
    public StatisticsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void initialize(Event event) {
        this.event = event;

        expenseList = server.getAllExpensesFromEvent(event);
        if(expenseList == null) expenseList = new ArrayList<>(0);
        double total = 0.00;
        for(Expense e : expenseList) total += e.getAmount();
        eventTitle.setText(event.getTitle());
        totalCostTitle.setText("Total cost:   " + total + " \u20ac");
        initChart();
    }

    private void initChart() {
        chart.getData().setAll();
        //ObservableList<PieChart.Data> types = FXCollections.observableArrayList();
        Map<String, Double> counter = new HashMap<>();
        for(Expense e : expenseList) {
            counter.put(e.getType(), counter.getOrDefault(e.getType(), 0.0) + e.getAmount());
        }
        for(Map.Entry<String, Double> entry : counter.entrySet()) {
            chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        //chart.setData(types);
        chart.setLabelsVisible(true);

        for (PieChart.Data data : chart.getData()) {
            Text text = new Text(data.getName() + " (" + data.getPieValue() + ")");
            data.getNode().setUserData(text);
        }
    }

    public void abort() {
        mainCtrl.showEventOverview(event);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER: case ESCAPE:
                abort();
                break;
            default:
                break;
        }
    }

}