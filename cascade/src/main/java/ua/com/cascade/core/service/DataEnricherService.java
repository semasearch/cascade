package ua.com.cascade.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.transaction.Neo4jTransactional;
import org.springframework.stereotype.Service;
import ua.com.cascade.core.model.Car;
import ua.com.cascade.core.model.Realestate;
import ua.com.cascade.core.repository.CarRepository;
import ua.com.cascade.core.repository.RealestateRepository;

@Service
public class DataEnricherService {


    @Autowired
    CarRepository carRepository;

    @Autowired
    RealestateRepository realestateRepository;

    @Autowired
    DataLookupService dataLookupService;

    @Neo4jTransactional
    public void enrichCars() {
        Iterable<Car> cars = carRepository.findAll();

        for(Car c : cars) {
            c.price = dataLookupService.calculateCarPrice(c);

            carRepository.save(c);
        }
    }

    @Neo4jTransactional
    public void enrichRealestate() {
        Iterable<Realestate> realestate = realestateRepository.findAll();

        for(Realestate r : realestate) {
            r.price = dataLookupService.calculateRealestatePrice(r);

            realestateRepository.save(r);
        }
    }

}
