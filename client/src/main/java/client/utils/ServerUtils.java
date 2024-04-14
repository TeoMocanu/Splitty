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
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commons.*;
import commons.emails.Invitation;
import commons.emails.Reminder;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Service
public class ServerUtils {
    private static String server;
    private static String SERVER;
    private static String webSocketServer;
    private StompSession session;

    public ServerUtils() {
        init();
    }
    public void init() {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("client.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String host = properties.getProperty("server.host");
        String port = properties.getProperty("server.port");
        this.server = host+":"+port;
        System.out.println("Trying to connect to: " + server);
        this.SERVER = "http://" + server + "/";
        this.webSocketServer = "ws://" + server + "/websocket";
        this.session = connect(webSocketServer);
    }

    public String getServer() {
        return server;
    }

    public void changeServer(String server) {
        int port;
        if(server.indexOf("localhost:") == 0){
            port = Integer.parseInt(server.substring(10));
        }
        /*ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/config/port") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(port, APPLICATION_JSON), int.class);*/

        this.server = server;
        this.SERVER = "http://" + server + "/";
        this.webSocketServer = "ws://" + server + "/websocket";
        this.session = connect(webSocketServer);
    }

    public Event getEvent(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/getById/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Event>() {
                });
    }

    public List<Event> getAllEvents() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/getAll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Event>>(){ });
    }

    public Event addEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/addEvent") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public Event updateEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/updateEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public Event editEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/editEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public void deleteEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/deleteEventById/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<Event>(){ });
    }

    public List<Expense> getAllExpenses() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>(){ });
    }

    public List<Expense> getAllExpensesFromEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllExpensesFromEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>(){ });
    }

    public Expense addExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/addExpense") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    public Expense editExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/editExpense/" + expense.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    public Expense getExpenseById(Long eid, Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getById/" + eid + "/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Expense>(){ });
    }

    public void deleteExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/deleteExpense/" + expense.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<Expense>(){ });
    }

    public List<Participant> getAllParticipantsFromEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/getAllParticipantsFromEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Participant>>() {
                });
    }

    public Participant getParticipantById(Event event, Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/getParticipantById/" + event.getId() + "/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(Participant.class);
    }


    public Participant addParticipant(Participant participant) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/addParticipant") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    public Participant editParticipant(Participant participant) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/editParticipant") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    public void deleteParticipant(Participant participant) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/deleteParticipant/" + participant.getEvent().getId() + "/" + participant.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<Participant>(){ });
    }

    public List<Participant> getAllParticipantsFromEvent(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/getAllParticipantsFromEvent/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Participant>>(){ });
    }

    public List<Debt> getAllDebtsFromEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debts/getByEventId/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Debt>>(){ });
    }

    public List<Expense> getAllExpensesFromEventPaidBy(long id, long id1) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllExpensesFromEventPaidBy/" + id + "/" + id1) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    public List<Expense> getAllExpensesFromEventOwedBy(long id, long id1) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllExpensesFromEventOwedBy/" + id + "/" + id1) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    public Debt addDebt(Debt debt) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debts/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(debt, APPLICATION_JSON), Debt.class);
    }

    public List<Debt> getDebtById(Long eid, Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debts/getByIds/" + eid + "/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Debt>>(){ });
    }

    public Debt editDebt(Debt debt) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debts/edit") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(debt, APPLICATION_JSON), Debt.class);
    }

    public void deleteDebt(Debt debt) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debts/delete/" + debt.getEvent().getId() + "/" + debt.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<Debt>(){ });
    }

    public Event createEvent(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Event createdEvent = mapper.readValue(jsonString, Event.class);
            return createdEvent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String fetchAllServerInfo() throws JsonProcessingException {
        Client client = ClientBuilder.newClient(new ClientConfig());
        ObjectMapper mapper = new ObjectMapper();
        String healthJSON = client
                .target(SERVER)
                .path("actuator/health")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(String.class);
        JsonNode healthNode = mapper.readTree(healthJSON);

        String diskJson = client
                .target(SERVER)
                .path("actuator/metrics/disk.free")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        JsonNode diskNode = mapper.readTree(diskJson);

        String cpuJson = client
                .target(SERVER)
                .path("actuator/metrics/system.cpu.usage")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
        JsonNode cpuNode = mapper.readTree(cpuJson);


        ObjectNode allNode = mapper.createObjectNode();
        allNode.set("Server Health", healthNode);
        allNode.set("Disk Usage", diskNode);
        allNode.set("CPU Usage", cpuNode);

        String serverUP = "Server Status: " + allNode.path("Server Health").path("status").asText();
        String diskFree = "Disk Free: " + allNode.path("Disk Usage").
                path("measurements").get(0).path("value").asText();
        String databaseUP = "Database Status: " + allNode.path("Server Health")
                .path("components").path("db").path("status").asText();
        String databaseDetails = "Database Details: " + allNode.path("Server Health")
                .path("components").path("db").path("details")
                .path("database").asText();
        // print each node on a new line
        System.out.println(serverUP + "\n" + diskFree + "\n" + databaseUP + "\n" + databaseDetails);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allNode);
    }

    public String generateRandomPassword(int length) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/admin/generate-password")
                .queryParam("length", length)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(String.class);
    }

    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    public void registerForUpdates(Consumer<Event> consumer) {
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                var res = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("api/events/update") //
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(Response.class);
                if (res.getStatus() == 204) {
                    continue;
                }
                if (res.getStatus() == 404) {
                    consumer.accept(new Event(""));
                    continue;
                }
                var e = res.readEntity(Event.class);
                consumer.accept(e);
            }
        });
    }

    public void stop() {
        EXEC.shutdownNow();
    }

    public void sendInvitation(Invitation invitation) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/mail/sendInvitation") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(invitation, APPLICATION_JSON), Invitation.class);
    }

    public void sendReminder(Reminder reminder) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/mail/sendReminder") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(reminder, APPLICATION_JSON), Reminder.class);
    }

}