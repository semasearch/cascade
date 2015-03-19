package ua.com.cascade.core.model;


import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Company {

    @GraphId
    Long id;

    public String name;

    @RelatedTo(type="OWN", direction= Direction.BOTH)
    public @Fetch
    Set<Person> owners;


    public String rawOwners;


    public void addOwer(Person person) {
        if (owners == null) {
            owners = new HashSet<Person>();
        }
        owners.add(person);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawOwners() {
        return rawOwners;
    }

    public void setRawOwners(String rawOwners) {
        this.rawOwners = rawOwners;
    }
}
