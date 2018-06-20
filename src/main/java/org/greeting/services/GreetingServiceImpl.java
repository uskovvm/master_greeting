package org.greeting.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.greeting.domain.Answer;
import org.greeting.domain.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
class GreetingServiceImpl implements GreetingService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Answer input) {
		em.persist(input);
	}

	@Override
	public List<Question> findByGreetingIdAndParticipantId(String greetingId, String participantId) {

		TypedQuery<Question> query = em.createQuery(
				"select q from Question q where q.greeting.id = ?1 and q.participant.id = ?2", Question.class);
		query.setParameter(1, new Long(greetingId));
		query.setParameter(2, new Long(participantId));

		return query.getResultList();
	}

	@Override
	public List<Answer> getAnswersForQuestions(List<Question> questions) {
		List<Answer> answers = new ArrayList<Answer>();
		TypedQuery<Answer> query = em.createQuery("select a from Answer a where a.question.id = ?1", Answer.class);
		for (Question question : questions) {
			query.setParameter(1, question.getId());
			Answer answer = query.getResultList().get(0);
			if (answer != null)
				answers.add(answer);
		}
		return answers;
	}

	@Override
	public List<Question> getAllTemplates() {
		List<Question> templates = new ArrayList<Question>();
		// ToDo т.к. шаблоны захардкожены.)
		return templates;
	}

	@Override
	public int deleteGreetings(String eventId) {
		Query query = em.createQuery("delete g from Greeting g where g.event.id = ?1");
		return query.setParameter(1, eventId).executeUpdate();
	}

}