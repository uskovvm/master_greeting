package org.greeting;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.greeting.domain.Event;
import org.greeting.domain.Greeting;
import org.greeting.domain.Question;
import org.greeting.repositories.EventRepository;
import org.greeting.repositories.GreetingRepository;
import org.greeting.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private GreetingRepository greetingRepository;
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private GreetingService greetingService;


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	Event event = this.eventRepository.save(new Event(new Long("1"),"EventDescr"));
    	event = this.eventRepository.findById(new Long("1")).get();
    	Greeting greeting = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    	greeting = greetingRepository.save(new Greeting(event, name));
    	
    	return greeting;
    }
    
    @RequestMapping("/greeting/{greetingId}/{participantId}")
    public List<Question> greeting(@PathVariable String greetingId,@PathVariable String participantId) {

    	List<Question> greetings = greetingService.findByGreetingIdAndParticipantId(greetingId, participantId);
    	return greetings;
    }

    @PostMapping("/event")
	public Event addEvent(@RequestBody  Event input) {
		Event event = this.eventRepository.save(input);
    	return event;
	}
    
    @PostMapping("/{eventId}/greeting")
	public Greeting addGreeting(@PathVariable String eventId, @RequestBody Greeting input) {
		Event event = this.eventRepository.findById(new Long(eventId)).get();
		input.setEvent(event);
		this.eventRepository.save(event);
    	return input;
	}

    @PostMapping("/{greetingId}/question")
	public Question addQuestion(@PathVariable String greetingId, @RequestBody Question input) {
		Greeting greeting = this.greetingRepository.findById(new Long(greetingId)).get();
		input.setGreeting(greeting);
		this.greetingRepository.save(greeting);
    	return input;
	}
}
