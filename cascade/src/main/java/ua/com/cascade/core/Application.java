package ua.com.cascade.core;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cascade.core.service.CrawlingTraversalService;
import ua.com.cascade.core.service.DataEnricherService;
import ua.com.cascade.core.service.DataLoadService;
import ua.com.cascade.core.service.SearchTraversalService;
import ua.com.cascade.core.model.Car;
import ua.com.cascade.core.model.Person;
import ua.com.cascade.core.repository.CarRepository;
import ua.com.cascade.core.repository.PersonRepository;

import java.io.File;
import java.io.IOException;


@ComponentScan(basePackages = {"ua.com.cascade"})
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "ua/com/cascade/core")
public class Application extends Neo4jConfiguration implements CommandLineRunner {


    public Application() {
        setBasePackage("ua/com/cascade/core");
    }

    @Autowired
    DataLoadService dataLoadService;


    @Autowired
    DataEnricherService dataEnricherService;

    @Bean
    GraphDatabaseService graphDatabaseService() {
        //return new org.springframework.data.neo4j.rest.SpringRestGraphDatabase("http://localhost:7474/db/data");
        //return new RestGraphDatabase("http://localhost:7474/db/data");
        return new GraphDatabaseFactory().newEmbeddedDatabase("d:\\accessingdataneo4j.db");
    }

    @Autowired
    GraphDatabase graphDatabase;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SearchTraversalService searchService;


    @Autowired
    CrawlingTraversalService crawlingTraversalService;

    public void run(String... args) throws Exception {
        //dataLoadService.clearAllData();


        //Person ppp = personRepository.findByPropertyValue("name", "XXX2 YYY2 ZZZ2");

        //Person ppp = personRepository.findByPropertyValue("XXX2 YYY2 ZZZ2", "15.02.1926");
        //System.out.println("P address - " + ppp.address);

        dataLoadService.loadDataFromFiles();

        //searchService.traverse("Стефурак Петро Сергійович", "26.02.1955", "Рава Олеся Кирилівна", "06.02.1990");
        searchService.traverse("Паліш Василь Максимович", "26.02.1970", "Стефурак Галина Петрівна", "02.0.1981");

        dataEnricherService.enrichCars();
//        for (Car c : carRepository.findAll()) {
//            System.out.println(c.price);
//        }

        dataEnricherService.enrichRealestate();

        crawlingTraversalService.crawlAndCalculatePrices("judge", 1);

        dataLoadService.clearAllData();
    }


    public void initWithSampleData() {
        // create 3 people
        Person pYaremaSerhiy = new Person("Ярема Сергій");
        Person pYaremaOksana = new Person("Ярема Оксана");
        Person pYaremaVasyl = new Person("Ярема Василь");

        // create 2 cars
        Car c1 = new Car("AA1111");
        Car c2 = new Car("BB1111");

        Transaction tx = graphDatabase.beginTx();
        try {
            personRepository.save(pYaremaSerhiy);
            personRepository.save(pYaremaOksana);
            personRepository.save(pYaremaVasyl);

            carRepository.save(c1);
            carRepository.save(c2);


            pYaremaSerhiy = personRepository.findByName(pYaremaSerhiy.name);
            pYaremaSerhiy.familyRelativeTo(pYaremaOksana);
            pYaremaSerhiy.familyRelativeTo(pYaremaVasyl);
            personRepository.save(pYaremaSerhiy);


            pYaremaOksana.ownsCar(c1);
            personRepository.save(pYaremaOksana);

            pYaremaVasyl.ownsCar(c2);
            personRepository.save(pYaremaVasyl);

            tx.success();
        } finally {
            // might die in a longer chain of calls - premature transaction closure
            //tx.close();
        }
    }

    public static void main(String[] args) throws Exception {

        try {
            FileUtils.deleteRecursively(new File("d:\\accessingdataneo4j.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SpringApplication.run(Application.class, args);
    }

}
