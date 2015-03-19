package ua.com.cascade.core.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import ua.com.cascade.core.model.Person;


public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String name);

    Person findById(Long id);

    Iterable<Person> findByFamilyMembersName(String name);

    Iterable<Person> findByType(String type);

    Person findByNameAndDob(String name, String dob);


}
