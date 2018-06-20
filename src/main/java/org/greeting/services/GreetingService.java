package org.greeting.services;

import java.util.List;

import org.greeting.domain.Answer;
import org.greeting.domain.Question;

public interface GreetingService {

	List<Question> findByGreetingIdAndParticipantId(String greetingId, String participantId);
	List<Answer> getAnswersForQuestions(List<Question> questions);
	List<Question> getAllTemplates();
	int deleteGreetings(String eventId);
	void save(Answer input);
}
