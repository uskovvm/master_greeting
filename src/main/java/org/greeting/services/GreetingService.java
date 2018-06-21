package org.greeting.services;

import java.util.List;

import org.greeting.domain.Answer;
import org.greeting.domain.Question;

public interface GreetingService {

	List<Answer> getAnswersForQuestions(List<Question> questions);
	List<Question> getAllTemplates();
	int deleteGreetings(String eventId);
	void save(Answer input);
	List<Question> getQuestionsByGreetingIdAndParticipantId(String greetingId, String participantId);
	Answer getQuestionByGreetingIdAndQuestionType(String greetingId, String questionType);
}
