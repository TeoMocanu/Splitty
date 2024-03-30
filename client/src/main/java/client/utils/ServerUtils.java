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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commons.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServerUtils {

    private static String SERVER = "http://localhost:8080/";
    private static String webSocketServer = "ws://localhost:8080/websocket";
    private StompSession session;

    public String getServer() {
        return SERVER;
    }

    public void changeServer(String server) {
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
                .get(new GenericType<List<Event>>() {
                });
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

    public Event deleteEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/deleteEventById/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public List<Expense> getAllExpenses() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    public List<Expense> getAllExpensesFromEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllFromEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    public void addExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/addExpense") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    public void editExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/editExpense" + expense.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    public Expense getExpenseById(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getById" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(Expense.class);
    }

    public void deleteExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/deleteExpense" + expense.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
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


    public void addParticipant(Participant participant) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/addParticipant") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    //TODO check the path after api is implemented and redo the method
    public void editParticipant(Participant participant) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/editParticipant") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    public void deleteParticipant(Participant participant) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/deleteParticipant" + participant.getEvent().getId() + "/" + participant.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    public List<Participant> getAllParticipantsFromEvent(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/getAllParticipantsFromEvent/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Participant>>() {
                });
    }

    public List<Expense> getAllExpensesFromEvent(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllExpensesFromEvent/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    public List<Debt> getDebts(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/debt/event/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Debt>>() {
                });
    }

    //TODO implement
    public String sendInvitations(List<String> emails, String code) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/event/invitation") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(emails.add(code), APPLICATION_JSON), String.class);
    }

    public Event createEvent(String jsonString) {
        try {
            if(jsonString.charAt(0) != '"') {
                jsonString = "\"" + jsonString + "\"";
            }
            System.out.println(jsonString);
            ObjectMapper mapper = new ObjectMapper();
            String title = mapper.readTree(jsonString).path("title").asText();
            JsonNode participantsNode = mapper.readTree(jsonString).path("participants");
            List<Participant> participants = mapper.readerFor(new TypeReference<List<Participant>>() {
            }).readValue(participantsNode);
            JsonNode expensesNode = mapper.readTree(jsonString).path("expenses");
            List<Expense> expenses = mapper.readerFor(new TypeReference<List<Expense>>() {
            }).readValue(expensesNode);

            JsonNode typesNoe = mapper.readTree(jsonString).path("types");
            List<String> types = mapper.readerFor(new TypeReference<List<String>>() {
            }).readValue(typesNoe);

            Event createdEvent = new Event(title);
            createdEvent.setParticipants(participants);
            createdEvent.setExpenses(expenses);
            createdEvent.setTypes(types);
            return createdEvent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void sendMail(Invitation invitation) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/mail/sendMail") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(invitation, APPLICATION_JSON), Invitation.class);
    }
}