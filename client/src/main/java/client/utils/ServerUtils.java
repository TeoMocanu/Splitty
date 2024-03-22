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

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import commons.Event;
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

    public String getServer(){
        return SERVER;
    }

    public void changeServer(String server) {
        this.SERVER = "http://"+ server + "/";
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

    public Event getEvent(Long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/getById/"+id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Event>(){});
    }

    public Event addEvent(Event event){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/addEvent") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    public Event editEvent(Event event){
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
                .target(SERVER).path("api/participants/editParticipant/" + participant.getEventId() + "/" + participant.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }

    public List<Participant> getAllParticipantsFromEvent(Long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/participants/getAllParticipantsFromEvent/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Participant>>() {
                });
    }

    public List<Expense> getAllExpensesFromEvent(Long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/expenses/getAllExpensesFromEvent/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Expense>>() {
                });
    }

    //TODO implement
    public String sendInvitations(List<String> emails, String code) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/invitation") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(emails.add(code), APPLICATION_JSON), String.class);
    }

    public Map<String, Object> fetchServerInfo() {
        // Perform the GET request and expect a response of type Map<String, Object>
        return ClientBuilder.newClient(new ClientConfig()) // Create a new client with a configuration.
                .target(SERVER) // Target the server base URL defined by the SERVER constant.
                .path("custom/info") // Specify the path to the server info endpoint.
                .request(MediaType.APPLICATION_JSON) // Create a request indicating you're sending JSON.
                .accept(MediaType.APPLICATION_JSON) // Specify that you expect to receive JSON in response.
                .get(new GenericType<Map<String, Object>>(){}); // Perform the GET request expecting a Map in return.
    }

    /**
     * Generates a secure random password.
     * @param length The desired length of the generated password.
     * @return A Base64 encoded secure random password.
     */
    public String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private StompSession connect(String url) {
        var client =  new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            return stomp.connect(url, new StompSessionHandlerAdapter(){}).get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }
}