package ua.com.cascade.core.repository;


import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import ua.com.cascade.core.model.Car;
import ua.com.cascade.core.model.Person;
import ua.com.cascade.core.model.Realestate;

public interface RealestateRepository extends GraphRepository<Realestate> {


}
