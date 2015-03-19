package ua.com.cascade.core.service;


import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.transaction.Neo4jTransactional;
import org.springframework.stereotype.Service;
import ua.com.cascade.core.model.Person;
import ua.com.cascade.core.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchTraversalService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @Neo4jTransactional
    public List<Person> traverse(String personFromName, String personFromDob, String personToName, String personToDob) {
        Person personFrom = personRepository.findByNameAndDob(personFromName, personFromDob);
        Person personTo = personRepository.findByNameAndDob(personToName, personToDob);

        System.out.println("Person one - " + personFrom.name + " ID " + personFrom.id);
        System.out.println("Person two - "  + personTo.name + " ID " + personTo.id);

        List<Long> ids = new ArrayList<Long>();

        Neo4jOperations neo = new Neo4jTemplate(graphDatabase);

        Node start = neo.getNode(personFrom.id);
        Node end = neo.getNode(personTo.id);

        PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
                PathExpanders.forDirection(Direction.BOTH), 15);
        Iterable<Path> paths = finder.findAllPaths(start, end);
        System.out.println("Path is " + paths);

        // we take only first path - in reality it could be more than one path
        for ( Node node : paths.iterator().next().nodes() ) {
            ids.add(node.getId());

            System.out.println("Adding ID to search path " + node.getProperty("name"));

        }

        List<Person> persons = new ArrayList<Person>();
        for (Long id : ids) {
            persons.add(personRepository.findById(id));
        }

        // Cypher example - 4 is hops number
        // START one=node(44715), two=node(17173) MATCH p = shortestPath(one-[*..4]-two) RETURN p;

        return persons;
    }

}
