package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.Person;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
    public Person mapFieldSet(FieldSet fieldSet) {
        Person person = new Person();
        person.name = fieldSet.readString(1);
        person.dob = fieldSet.readString(2);
        person.address = fieldSet.readString(3);
        person.passportNumber = fieldSet.readString(4);
        return person;
    }
}

