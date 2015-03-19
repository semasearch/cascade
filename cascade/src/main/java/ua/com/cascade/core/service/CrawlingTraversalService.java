package ua.com.cascade.core.service;


import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.Evaluators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.transaction.Neo4jTransactional;
import org.springframework.stereotype.Service;
import ua.com.cascade.core.model.Person;
import ua.com.cascade.core.repository.PersonRepository;

@Service
public class CrawlingTraversalService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @Autowired
    Neo4jTemplate neo4jTemplate;

    private enum Rels implements RelationshipType
    {
        LIKES, OWNS_CAR, FAMILY, OWNS_REALESTATE
    }

    @Neo4jTransactional
    public void crawlAndCalculatePrices(String type, int depth) {
        System.out.println("Starting crawl");
        Iterable<Person> persons = personRepository.findByType(type);

        Neo4jOperations neo = neo4jTemplate;

        for (Person p : persons) {

            Node node = neo.getNode(p.id);
            System.out.println("Crawling path for " + p.name);

            float total = 0;
            for ( Path position : graphDatabase.traversalDescription()
                    .depthFirst()
                    .relationships(Rels.FAMILY)
                    .relationships(Rels.OWNS_CAR)
                    .relationships(Rels.OWNS_REALESTATE)
                    .evaluator(Evaluators.toDepth(depth))
                    .traverse(node) )
            {
                System.out.println(" Path xxx " + position);
                total += updatePricesInDepth(position);
            }

            System.out.println("Total ownership for " + p.name + " is " + total);

        }

        System.out.println("Crawling done");

    }



    public float updatePricesInDepth(Path path) {
        float total = 0;

        for (Node node : path.nodes()) {
            try {
                String price = null;
                price = node.getProperty("price").toString();

                if (price!=null) {
                    total += Float.parseFloat(price);
                    System.out.println("Price is: " + price);
                }
            } catch (org.neo4j.graphdb.NotFoundException e) {

            }


        }

        return total;
    }
}
