package org.greeting.services.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.greeting.domain.Question;

public class GreetingUtils {

	public static int getRandomResult(Question question) {
		List<String> questionTexts = question.getQuestionTexts();
		return ThreadLocalRandom.current().nextInt(0, questionTexts.size());
	}

	public static int getImageNumber(Question question) {
		// TODO Не понятно откуда брать номер.)
		return 0;
	}

}
