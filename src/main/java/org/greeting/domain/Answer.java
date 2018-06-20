package org.greeting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="answers")
public class Answer {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="greeting_id", nullable=true)
	private Greeting greeting;
	@ManyToOne
    @JoinColumn(name="question_id", nullable=true)
	private Question question;
    @ManyToOne
    @JoinColumn(name="participant_id", nullable=true)
	private Participant participant;
    private String text;
    private int imageNumber;

	
	public Answer() {
	}

	public Answer(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

}
