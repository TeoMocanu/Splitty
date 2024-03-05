package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository e){
        this.eventRepository = e;
    }

    @GetMapping("/hey")
    @ResponseBody
    public String index() {
        return "now in event controller";
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

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

    @PostMapping("/addExpense/{event_id}")
    public ResponseEntity<Event> addExpense(@PathVariable("event_id") long id, @RequestBody Expense expense) {
        if(expense == null) {
            return ResponseEntity.badRequest().build();
        }

        if(!eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        //Check if the event exists
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isEmpty())
            return ResponseEntity.badRequest().build();
        Event event = eventOptional.get();
        // add the expense
        event.addExpense(expense);
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

    @PostMapping("/addParticipant/{event_id}")
    public ResponseEntity<Event> addParticipant(@PathVariable("event_id") long id, @RequestBody Participant participant) {
        if (participant == null || !eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        //Check if the event exists
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty())
            return ResponseEntity.badRequest().build();
        Event event = eventOptional.get();
        // add the participant
        event.addParticipant(participant);
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }
}
