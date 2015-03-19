package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.Person;

public class JudgesFieldSetMapper implements FieldSetMapper<Person> {
    public Person mapFieldSet(FieldSet fieldSet) {
        Person person = new Person();
        person.name = fieldSet.readString(1);
        person.dob = fieldSet.readString(2);
        return person;
    }
}

