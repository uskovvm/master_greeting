package org.greeting.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="events")
public class Event {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String description;	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="event")
	private Set<Greeting> greetings= new HashSet<>();


	public Event() {
	}

	public Event(Long id, String description) {
		this.id = id;
		this.setDescription(description);
	}

	public Event(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Greeting> getGreetings() {
		return greetings;
	}

	public void setGreetings(Set<Greeting> greetings) {
		this.greetings = greetings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
