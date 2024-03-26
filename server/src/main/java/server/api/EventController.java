package server.api;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.EventRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private final EventRepository eventRepository;


    public EventController(EventRepository e){
        this.eventRepository = e;
    }

    @GetMapping("/getAll")
    public List<Event> getEventAll(){
        return eventRepository.findAll();
    }

    @GetMapping("/get?id={id}")
    public Event getById(@PathVariable("id") Long id){
        return eventRepository.getReferenceById(id);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id){
        if(id <= 0 || !eventRepository.existsById(id)) //check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(eventRepository.findById(id).get());
    }

    @GetMapping("/getIdById/{id}")
    public ResponseEntity<Long> getEventIdById(@PathVariable("id") Long id){
        if(id <= 0 || !eventRepository.existsById(id)) {//check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(eventRepository.findById(id).get().getId());
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

    @PostMapping("/editEvent/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable("id") Long id, @RequestBody Event event) {
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

    @PostMapping("/updateEvent/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        if(id <= 0 || !eventRepository.existsById(id)) {//check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();
        }
        listeners.forEach((k, l) -> l.accept(event));
        return ResponseEntity.ok(eventRepository.findById(id).get());
    }

    @GetMapping("/deleteEventById/{event_id}")
    public ResponseEntity deleteById(@PathVariable("event_id") Long id){
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

    private Map<Object, Consumer<Event>> listeners = new HashMap<>();
    @GetMapping("/update")
    public DeferredResult<ResponseEntity<Event>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Event>>(5000L, noContent);
        var key = new Object();
        listeners.put(key, q -> { res.setResult(ResponseEntity.ok(q)); });
        res.onCompletion(() -> { listeners.remove(key); });
        return res;
    }
}
