package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private final EventRepository eventRepository;

    private final ExpenseController expenseController;

    public EventController(EventRepository e){
        this.eventRepository = e;
        expenseController = null;
    }

//    @GetMapping("/hey")
//    @ResponseBody
//    public String index() {
//        return "now in event controller";
//    }

    @GetMapping("/getAll")
    public List<Event> getEventAll(){
        return eventRepository.findAll();
    }

//    @GetMapping("/getAllExpenses")
//    public List<Expense> getExpenseAll() {
//        List <Expense> out = new ArrayList<>();
//        List<Event> events = eventRepository.findAll();
//        for(Event event : events){
//            List<Expense> list = event.getExpenses();
//            out.addAll(list);
//        }
//        return out;
//    }

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
        if(id <= 0 || !eventRepository.existsById(id)) //check if id given exists or not. If not give back a bad request response.
            return ResponseEntity.badRequest().build();

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
        Event added = eventRepository.save(event);
        return ResponseEntity.ok(added);
    }

//    @PostMapping("/addExpense/{event_id}")
//    public ResponseEntity<Event> addExpense(@PathVariable("event_id") long id, @RequestBody Expense expense) {
//        if(expense == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        if(!eventRepository.existsById(id)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        //Check if the event exists
//        Optional<Event> eventOptional = eventRepository.findById(id);
//        if(eventOptional.isEmpty())
//            return ResponseEntity.badRequest().build();
//        Event event = eventOptional.get();
//        // add the expense
//        event.addExpense(expense);
//        //eventRepository.addExpenseToEvent(id, expense.getId());
//        Event added = eventRepository.save(event);
//        return ResponseEntity.ok(added);
//    }

//    @PostMapping("/addParticipant/{event_id}")
//    public ResponseEntity<Event> addParticipant(@PathVariable("event_id") long id, @RequestBody Participant participant) {
//        if (participant == null || !eventRepository.existsById(id)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        //Check if the event exists
//        Optional<Event> eventOptional = eventRepository.findById(id);
//        if (eventOptional.isEmpty())
//            return ResponseEntity.badRequest().build();
//        Event event = eventOptional.get();
//        // add the participant
//        event.addParticipant(participant);
//        Event added = eventRepository.save(event);
//        return ResponseEntity.ok(added);
//    }

//    @PostMapping("/getExpenses/{event_id}")
//    public ResponseEntity<Expense> getExpensesById(@PathVariable("event_id") Long id) {
//        if(!eventRepository.existsById(id)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        Optional<Event> eventOptional = eventRepository.findById(id);
//        if(eventOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Event event = eventOptional.get();
//        List<Expense> expenses = event.getExpenses();
//
//        return ResponseEntity.ok((Expense) expenses);
//    }

    // TODO send invitations to email list
    @PostMapping("/invitation")
    public void sendInvitations(@RequestBody List<String> emails) {
        String code = emails.getLast();
        String text = "Join my splitty event, using code " + code + " in the app.";
        for(String e : emails) {
            // send text to email
        }
        return;
    }

    @GetMapping("/deleteEvent/{id}")
    public ResponseEntity deleteEvent(@PathVariable("id") Long id){
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
}
