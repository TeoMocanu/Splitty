package server.services;

import commons.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public interface EventService {

    List<Event> getEventAll();

    ResponseEntity<Event> getEventById(Long id);

    ResponseEntity<Event> addEvent(Event event);

    ResponseEntity<Event> editEvent(Long id, Event event);

    ResponseEntity<Event> updateEvent(Long id, Event event);

    ResponseEntity deleteById(Long id);

    DeferredResult<ResponseEntity<Event>> getUpdates();
}
