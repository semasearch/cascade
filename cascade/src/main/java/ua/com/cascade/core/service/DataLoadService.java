package ua.com.cascade.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.transaction.Neo4jTransactional;
import org.springframework.stereotype.Service;
import ua.com.cascade.core.model.*;
import ua.com.cascade.core.repository.CarRepository;
import ua.com.cascade.core.repository.CompanyRepository;
import ua.com.cascade.core.repository.PersonRepository;
import ua.com.cascade.core.repository.RealestateRepository;
import ua.com.cascade.data.GenericEntityLoader;
import ua.com.cascade.data.mapper.*;

import java.io.File;
import java.util.Collection;

@Service
public class DataLoadService {

    public static final String PASSPORT_FILE = ".." + File.separator + "sample.data" + File.separator + "passport.csv";
    public static final String CARS_FILE = ".." + File.separator + "sample.data" + File.separator + "cars.csv";
    public static final String JUDGES_FILE = ".." + File.separator + "sample.data" + File.separator + "judges.csv";
    public static final String FAMILY_RELATIONSHIPS_FILE = ".." + File.separator + "sample.data" + File.separator + "family_relationships.csv";
    public static final String COMPANIES_FILE = ".." + File.separator + "sample.data" + File.separator + "companies.csv";
    public static final String REALESTATE_FILE = ".." + File.separator + "sample.data" + File.separator + "realestate.csv";

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RealestateRepository realestateRepository;

    @Autowired
    Neo4jTemplate neo4jTemplate;

    @Neo4jTransactional
    public void loadDataFromFiles() {

        GenericEntityLoader<Person> personLoader = new GenericEntityLoader<Person>();
        Collection<Person> persons = personLoader.loadEntities(new File(PASSPORT_FILE), new PersonFieldSetMapper(), 1, "|");

        for (Person p : persons) {
            personRepository.save(p);
        }

        System.out.println("Loaded " + persons.size() + " persons");

        GenericEntityLoader<Car> carsLoader = new GenericEntityLoader<Car>();
        Collection<Car> cars = carsLoader.loadEntities(new File(CARS_FILE), new CarsFieldSetMapper(), 1, "|");

        int carMappings = 0;
        for (Car c : cars) {
            carRepository.save(c);

            Person person = personRepository.findByNameAndDob(c.ownerName, c.ownerDob);
            if (person != null) {
                //System.out.println("Found car for: " + person.name);
                person.ownsCar(c);
                personRepository.save(person);

                carMappings++;
            }
        }
        System.out.println("Loaded " + cars.size() + " cars and mapped to " + carMappings + " persons");

        GenericEntityLoader<FamilyRelationship> familyRelationshipLoader = new GenericEntityLoader<FamilyRelationship>();
        Collection<FamilyRelationship> familyRelationships = familyRelationshipLoader.loadEntities(new File(FAMILY_RELATIONSHIPS_FILE), new FamilyRelationshipFieldSetMapper(), 1, "|");

        for (FamilyRelationship f : familyRelationships) {
            Person p1 = personRepository.findByNameAndDob(f.firstPersonName, f.firstPersonDob);
            Person p2 = personRepository.findByNameAndDob(f.secondPersonName, f.secondPersonDob);

            //System.out.println("Person " + p1 + " related to " + p2);

            p1.familyRelativeTo(p2);

            personRepository.save(p1);
        }

        Collection<Person> judges = personLoader.loadEntities(new File(JUDGES_FILE), new JudgesFieldSetMapper(), 1, "|");

        System.out.println("Marked " + judges.size() + " judges");

        for (Person judge : judges) {
            Person person = personRepository.findByNameAndDob(judge.name, judge.dob);
            if (person != null) {
                //System.out.println("Judge - " + person.name);
                person.type = "judge";

                personRepository.save(person);
            }
        }

        GenericEntityLoader<Company> companiesLoader = new GenericEntityLoader<Company>();
        Collection<Company> companies = companiesLoader.loadEntities(new File(COMPANIES_FILE), new CompanyFieldSetMapper(), 1, "|");

        for (Company company : companies) {
            String[] companyOwners = company.rawOwners.split("\\+");
            for (String cOwner : companyOwners) {
                String[] details = cOwner.split(",");
                String ownerName = details[0];
                String ownerDob = details[1];

                //System.out.println("Adding company owner: " + ownerName + " with DOB: " +ownerDob);
                company.addOwer(personRepository.findByNameAndDob(ownerName, ownerDob));
            }
            companyRepository.save(company);
        }
        System.out.println("Loaded " + companies.size() + " companies");

        GenericEntityLoader<Realestate> realestateLoader = new GenericEntityLoader<Realestate>();
        Collection<Realestate> realestate = realestateLoader.loadEntities(new File(REALESTATE_FILE), new RealestateFieldSetMapper(), 1, "|");

        int realestateMappings = 0;
        for (Realestate r : realestate) {
            realestateRepository.save(r);
            Person p = personRepository.findByNameAndDob(r.realestateOwnerName, r.realestateOwnerDob);
            if (p != null) {
                p.ownsRealestate(r);
                personRepository.save(p);

                realestateMappings++;
            }
        }

        System.out.println("Loaded " + realestate.size() + " realestate entities and mapped to " + realestateMappings + " persons");
    }

    @Neo4jTransactional
    public void clearAllData() {
        System.out.println("Clearing all records using query");
        Neo4jOperations neo = neo4jTemplate;

        neo.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r", null);
    }
}
