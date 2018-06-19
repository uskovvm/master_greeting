package org.greeting.repositories;

import java.util.Collection;

import org.greeting.domain.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {

	Collection<Greeting> findById(String id);
}