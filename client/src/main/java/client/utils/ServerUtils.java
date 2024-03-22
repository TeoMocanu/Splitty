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

import java.util.List;
import java.util.concurrent.ExecutionException;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commons.Event;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

import commons.Participant;
import commons.Expense;
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

    /*
    public void getQuotesTheHardWay() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/api/quotes").toURL();
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }*/

    public Event getEvent(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/getById/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Event>() {
                });
    }
    public List<Event> getAllEvents() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/getAll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get( new GenericType<List<Event>>() {});
    }
    public Event addEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/addEvent") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public Event editEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/editEvent/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public Event addExpense(Expense expense, Event event) {
        //event.addExpense(expense);
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/addExpense/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(expense, APPLICATION_JSON), Event.class);
    }

    public void addParticipant(Participant participant, Event event) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/addParticipant") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    //TODO check the path after api is implemented and redo the method
    public void editParticipant(Participant participant) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/editParticipant/" + participant.getEventId() + "/" + participant.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    //TODO implement
    public String sendInvitations(List<String> emails, String code) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/invitation") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(emails.add(code), APPLICATION_JSON), String.class);
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
        return mapper.writeValueAsString(allNode);
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
}