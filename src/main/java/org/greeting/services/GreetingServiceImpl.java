package org.greeting.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.greeting.domain.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
class GreetingServiceImpl implements GreetingService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Question> findByGreetingIdAndParticipantId(String greetingId, String participantId) {

		TypedQuery<Question> query = em.createQuery("select q from Question q where q.greeting.id = ?1 and q.participant.id = ?2", Question.class);
		query.setParameter(1, new Long(greetingId));
		query.setParameter(2, new Long(participantId));

		return query.getResultList();
	}
}