package ua.com.cascade.core.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person {

    @GraphId
    public Long id;

    public String name;

    public String dob;

    public String address;

    public String type;

    public String passportNumber;

    public float declaredValuables;

    public float indirectlyOwnedValuables;

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public float getDeclaredValuables() {
        return declaredValuables;
    }

    public void setDeclaredValuables(float declaredValuables) {
        this.declaredValuables = declaredValuables;
    }

    public float getIndirectlyOwnedValuables() {
        return indirectlyOwnedValuables;
    }

    public void setIndirectlyOwnedValuables(float indirectlyOwnedValuables) {
        this.indirectlyOwnedValuables = indirectlyOwnedValuables;
    }

    public Person() {}
    public Person(String name) { this.name = name; }

    @RelatedTo(type="FAMILY", direction=Direction.BOTH)
    public @Fetch Set<Person> familyMembers;

    @RelatedTo(type="OWNS_CAR", direction=Direction.OUTGOING)
    public @Fetch Set<Car> cars;

    @RelatedTo(type="OWNS_REALESTATE", direction=Direction.OUTGOING)
    public @Fetch Set<Realestate> realestate;

    public void familyRelativeTo(Person person) {
        if (familyMembers == null) {
            familyMembers = new HashSet<Person>();
        }
        familyMembers.add(person);
    }

    public void ownsCar(Car car) {
        if (cars == null) {
            cars = new HashSet<Car>();
        }
        cars.add(car);
    }

    public void ownsRealestate(Realestate r) {
        if (realestate == null) {
            realestate = new HashSet<Realestate>();
        }
        realestate.add(r);
    }

    public String toString() {
        String results = name + "'s relatives include\n";
        if (familyMembers != null) {
            for (Person person : familyMembers) {
                results += "\t- " + person.name + "\n";
            }
        }
        return results;
    }

}
