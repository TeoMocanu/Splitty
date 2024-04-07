package server.implementations;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.EventRepository;
import server.services.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class EventServiceImplementation implements EventService {

    @Autowired
    private EventRepository eventRepository;

    private Map<Object, Consumer<Event>> listeners = new HashMap<>();

    public EventServiceImplementation(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getEventAll() {
        return eventRepository.findAll();
    }

    @Override
    public ResponseEntity<Event> getEventById(Long id) {
        if(id <= 0 || !eventRepository.existsById(id)) //check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(eventRepository.findById(id).get());
    }

    @Override
    public ResponseEntity<Event> addEvent(Event event) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

    @Override
    public ResponseEntity<Event> editEvent(Long id, Event event) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        if(id <= 0 || !eventRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        listeners.forEach((k, l) -> l.accept(event));
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

    @Override
    public ResponseEntity<Event> updateEvent(Long id, Event event) {
        if(id <= 0 || !eventRepository.existsById(id)) {//check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();
        }
        listeners.forEach((k, l) -> l.accept(event));
        return ResponseEntity.ok(eventRepository.findById(id).get());
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        if(!eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        //Check if the event exists
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isEmpty())
            return ResponseEntity.badRequest().build();
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public DeferredResult<ResponseEntity<Event>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Event>>(5000L, noContent);
        var key = new Object();
        listeners.put(key, q -> { res.setResult(ResponseEntity.ok(q)); });
        res.onCompletion(() -> { listeners.remove(key); });
        return res;
    }
}
