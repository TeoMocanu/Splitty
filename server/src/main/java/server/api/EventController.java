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
@RequestMapping("/api")
public class EventController {
    private final EventRepository eventRepository;

    private final ExpenseController expenseController;

    public EventController(EventRepository e){
        this.eventRepository = e;
        expenseController = null;
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

    @PostMapping("/editEvent/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        if(id <= 0 || !eventRepository.existsById(id)){
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

    @PostMapping("/{id}/getExpenses")
    public ResponseEntity<Expense> getExpensesById(@PathVariable("id") Long id) {
        if(!eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = eventOptional.get();
        List<Expense> expenses = event.getExpenses();

        return ResponseEntity.ok((Expense) expenses);
    }

    // TODO send invitations to email list
    @PostMapping("/invitation")
    public void sendInvitations(@RequestBody List<String> emails) {
        String code = emails.getLast();
        String text = "Join my splitty event, using code " + code + " in the app.";
        for(String e : emails){
            // send text to email
        }
        return;
    }
}
