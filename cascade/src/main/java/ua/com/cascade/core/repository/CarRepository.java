package ua.com.cascade.core.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import ua.com.cascade.core.model.Car;

public interface CarRepository extends GraphRepository<Car> {

    Car findByCarNumber(String carNumber);




}
