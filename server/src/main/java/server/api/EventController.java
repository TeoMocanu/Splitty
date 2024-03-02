package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;

import java.util.List;

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
        Participant p1 = new Participant("John");
        Participant p2 = new Participant("Bob");
        Participant p3 = new Participant("Thijs");
        p1.setId(1);
        p2.setId(2);
        p3.setId(3);
        Expense e1 = new Expense(p1.getId(), "fries", 2.56f);
        Expense e2 = new Expense(p1.getId(), "salami", 0.86f);
        e1.setId(5);
        e2.setId(6);
        Event n = new Event(69L, "SomeParty", List.of(p1.getId(), p2.getId(), p3.getId()), List.of(e1.getId(), e2.getId()));
        eventRepository.save(n);
        return "now in event controller";
    }

    @GetMapping("/getAll")
    public List<Event> getEventAll(){
        return eventRepository.findAll();
    }

    @GetMapping("/get?id={id}")
    public Event getEventById(@PathVariable("id") Long id){
        return eventRepository.getReferenceById(id);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") Long id){
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
}
