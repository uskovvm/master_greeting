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
@Table(name="participants")
public class Participant {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="participant")
	private Set<Question> questions= new HashSet<>();

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}



}
