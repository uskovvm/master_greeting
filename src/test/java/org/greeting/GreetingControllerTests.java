/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.greeting;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.greeting.domain.Event;
import org.greeting.domain.Greeting;
import org.greeting.domain.Question;
import org.greeting.repositories.EventRepository;
import org.greeting.repositories.GreetingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class GreetingControllerTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private Event event;
	private List<Greeting> greetingList = new ArrayList<>();

	@Autowired
	private GreetingRepository greetingRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.greetingRepository.deleteAllInBatch();
		this.eventRepository.deleteAllInBatch();

		// this.event = eventRepository.save(new Event(new Long(1), "event
		// description"));
		// this.greetingList.add(greetingRepository.save(new Greeting(event,
		// "Instruction")));
	}

	@Test
	public void createEvent() throws Exception {
		Event theEvent = new Event("Event description");
		String eventJson = json(theEvent);

		this.mockMvc.perform(post("/event").contentType(contentType).content(eventJson)).andExpect(status().isOk());
	}

	@Test
	public void createGreeting() throws Exception {
		Event theEvent = eventRepository.save(new Event("Event description"));

		Greeting greeting = new Greeting("Greeting Instruction");
		String greetingJson = json(greeting);

		this.mockMvc.perform(post("/" + theEvent.getId() + "/greeting").contentType(contentType).content(greetingJson))
				.andExpect(status().isOk());
	}

	@Test
	public void createQuestion() throws Exception {
		Greeting greeting = greetingRepository.save(new Greeting("Greeting Instruction"));
		Question question = new Question("question");
		String questionJson = json(question);

		this.mockMvc.perform(post("/" + greeting.getId() + "/question").contentType(contentType).content(questionJson))
				.andExpect(status().isOk());
	}

	@Test
	public void findByGreetingIdAndParticipantId() throws Exception {
		//  /greeting/{greetingId}/{participantId}
		mockMvc.perform(get("/greeting/1/1"))
				.andExpect(status().isOk());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
