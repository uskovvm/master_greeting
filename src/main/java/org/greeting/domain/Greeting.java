package org.greeting.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="greetings")
public class Greeting {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
	@ManyToOne
    @JoinColumn(name="event_id", nullable=true)
	private Event event;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="greeting")
	private Set<Question> questions= new HashSet<>();

	private String instruction;


	public Greeting() {
	}

	public Greeting(long id, String instruction) {
		this.id = id;
		this.instruction = instruction;
	}

	public Greeting(Event event) {
		this.event = event;
	}

	public Greeting(Event event, String instruction) {
		this.event = event;
		this.instruction = instruction;
	}

	public Greeting(String instruction) {
		this.instruction = instruction;
	}

	public long getId() {
        return id;
    }

    public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
