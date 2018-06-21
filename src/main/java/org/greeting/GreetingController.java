package org.greeting;

import java.util.List;

import org.greeting.domain.Answer;
import org.greeting.domain.Event;
import org.greeting.domain.Greeting;
import org.greeting.domain.Question;
import org.greeting.repositories.EventRepository;
import org.greeting.repositories.GreetingRepository;
import org.greeting.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Autowired
	private GreetingRepository greetingRepository;
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private GreetingService greetingService;

	//Ёндпоинт дл€ получени€ всех вопросов участника
	@RequestMapping("/greeting/{greetingId}/{participantId}")
	public List<Question> greeting(@PathVariable String greetingId, @PathVariable String participantId) {

		List<Question> questions = greetingService.getQuestionsByGreetingIdAndParticipantId(greetingId, participantId);
		return questions;
	}

	//Ёндпоинт дл€ добавлени€ Greeting
	@PostMapping("/{eventId}/greeting")
	public Greeting addGreeting(@PathVariable String eventId, @RequestBody Greeting input) {
		Event event = this.eventRepository.findById(new Long(eventId)).get();
		input.setEvent(event);
		this.eventRepository.save(event);
		return input;
	}

	//Ёндпоинт дл€ получени€ вопроса заданного типа
	//Answer содержит ссылку на вопрос
	@RequestMapping("/greeting/{greetingId}/{questionType}")
	public Answer getQuestion(@PathVariable String greetingId, @PathVariable String questionType) {
		return greetingService.getQuestionByGreetingIdAndQuestionType(greetingId, questionType);
	}
	
	//Ёндпоинт дл€ добавлени€ вопроса
	@PostMapping("/{greetingId}/question")
	public Question addQuestion(@PathVariable String greetingId, @RequestBody Question input) {
		Greeting greeting = this.greetingRepository.findById(new Long(greetingId)).get();
		input.setGreeting(greeting);
		this.greetingRepository.save(greeting);
		return input;
	}

	//Ёндпоинт дл€ добавлени€ ответа участником
	@PostMapping("/greeting/answer")
	public void addAnswer(@RequestBody Answer input) {
		greetingService.save(input);
		// ToDo публикаци€ в RabbitMQ
	}

	//Ёндпоинт дл€ запроса ответов на заданный список вопросов
	@GetMapping("/greeting/answers")
	public List<Answer> getAnswers(@RequestBody List<Question> input) {
		return greetingService.getAnswersForQuestions(input);
	}

	//Ёндпоинт дл€ получени€ шаблона
	@GetMapping("/greeting/template")
	public List<Question> getAllTemplates() {
		return greetingService.getAllTemplates();
	}

	
	//Ёндпоинт дл€ удалени€ всех объектов Greeting, имеющих заданный eventId 
	@DeleteMapping("/greeting/{eventId}/delete")
	public void deleteGreetings(@PathVariable String eventId) {
		int deleted = greetingService.deleteGreetings(eventId);
	}

}
