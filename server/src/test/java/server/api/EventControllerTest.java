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
package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.Event;
import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.implementations.EventServiceImplementation;
import server.implementations.ExpenseServiceImplementation;

import java.util.ArrayList;
import java.util.List;

public class EventControllerTest {

    @Mock
    private EventServiceImplementation service;

    @InjectMocks
    private EventController eventController;

    public int nextInt;
    private TestEventRepository repo;

    private EventServiceImplementation sut;
    private List<Event> eventList;
    private Event event1;
    private Event event2;

    @BeforeEach
    public void setup() {
        repo = new TestEventRepository();
        sut = new EventServiceImplementation(repo);
        event1 = new Event("event1");
        event2 = new Event("event2");
        eventList = List.of(event1, event2);

        service = mock(EventServiceImplementation.class);
        eventController = new EventController(service);
    }

    @Test
    void getAllEventsTest() {
        when(service.getEventAll()).thenReturn(eventList);
        List<Event> results = eventController.getEventAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(eventList, results);
        verify(service).getEventAll();
    }

    @Test
    void getEventByIdTest() {
        long eventId = 1L;

        when(service.getEventById(eventId)).thenReturn(ResponseEntity.ok(event1));
        ResponseEntity<Event> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event1, response.getBody());
        verify(service).getEventById(eventId);
    }

    @Test
    void addEventTest() {
        when(service.addEvent(event1)).thenReturn(ResponseEntity.ok(event1));
        ResponseEntity<Event> response = eventController.addEvent(event1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event1, response.getBody());
        verify(service).addEvent(event1);
    }

    @Test
    void editEventTest() {
        long eventId = 1L;
        event1.setTitle("new Title");

        when(service.editEvent(event1.getId(), event1)).thenReturn(ResponseEntity.ok(event1));
        ResponseEntity<Event> response = eventController.editEvent(event1.getId(), event1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event1, response.getBody());
        verify(service).editEvent(event1.getId(), event1);
    }

    @Test
    void updateEventTest() {
        long eventId = 1L;
        //event1.setTitle("new Title");

        when(service.updateEvent(event1.getId(), event1)).thenReturn(ResponseEntity.ok(event1));
        ResponseEntity<Event> response = eventController.updateEvent(event1.getId(), event1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event1, response.getBody());
        verify(service).updateEvent(event1.getId(), event1);
    }



    @Test
    void deleteEventTest() {
        long eventId = 1L;

        when(service.deleteById(eventId)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity<Void> response = eventController.deleteById(eventId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service).deleteById(eventId);
    }

    @Test
    public void cannotAddNullEvent() {
        var actual = sut.addEvent(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        sut.addEvent(getEvent("q1"));
        repo.calledMethods.contains("save");
    }

    private static Event getEvent(String q) {
        return new Event(q);
    }

    @Test
    public void getUpdatesTest() {
        //when(service.getUpdates()).thenReturn((ResponseEntity.noContent()));
    }

}