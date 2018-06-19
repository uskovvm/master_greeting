package org.greeting.services;

import java.util.List;

import org.greeting.domain.Greeting;
import org.greeting.domain.Question;

public interface GreetingService {

	List<Question> findByGreetingIdAndParticipantId(String greetingId, String participantId);

}
