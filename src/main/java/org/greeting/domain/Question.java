package org.greeting.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.greeting.QuestionType;

@Entity
@Table(name="questions")
public class Question {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="greeting_id", nullable=true)
	private Greeting greeting;
    @ManyToOne
    @JoinColumn(name="participant_id", nullable=true)
	private Participant participant;
    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> questionTexts;
	private QuestionType type;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="question")
	private Set<Answer> answers= new HashSet<>();


	
	public Question() {
	}

	public Question(List<String> questionTexts) {
		this.questionTexts=questionTexts;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Greeting getGreeting() {
		return greeting;
	}
	public void setGreeting(Greeting greeting) {
		this.greeting = greeting;
	}


	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public List<String> getQuestionTexts() {
		return questionTexts;
	}

	public void setQuestionTexts(List<String> questionTexts) {
		this.questionTexts = questionTexts;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}


}
