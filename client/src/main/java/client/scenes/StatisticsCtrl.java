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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class StatisticsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private List<Expense> expenseList;
    private List<String> colors = List.of("yellow", "blue", "green", "red", "grey", "aqua", "crimson", "orange", "chocolate");
    private List<String> types;

    @FXML
    private Label eventTitle;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label eventStatistics;
    @FXML
    private Button abortButton;
    @FXML
    private Label costLabel;
    @FXML
    private PieChart chart;

    @Inject
    public StatisticsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void initialize(Event event) {
        this.event = event;
        types = event.getTypes();

        expenseList = server.getAllExpensesFromEvent(event);
        if(expenseList == null) expenseList = new ArrayList<>(0);
        double total = 0.00;
        for(Expense e : expenseList) total += e.getAmount();
        eventTitle.setText(event.getTitle());
        costLabel.setText(total + " \u20ac");
        initChart();
    }

    private void initChart() {
        chart.getData().setAll();
        Map<String, Double> counter = new HashMap<>();
        for(Expense e : expenseList) {
            counter.put(e.getType(), counter.getOrDefault(e.getType(), 0.0) + e.getAmount());
        }
        for(Map.Entry<String, Double> entry : counter.entrySet()) {
            chart.getData().add(new PieChart.Data(entry.getKey() + "\n " + entry.getValue() + "\u20ac", entry.getValue()));
        }
        chart.setLabelsVisible(true);
        chart.setLegendVisible(false);

        for(PieChart.Data data : chart.getData()){
            String type = data.getName().split("\n")[0];
            int index = types.indexOf(type);
            if(index == -1) index = colors.size()-1;
            String color = colors.get(index);
            data.getNode().setStyle("-fx-pie-color: " + color + ";");
            //data.getNode().setStyle("-fx-text-fill: #ffffff;");
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