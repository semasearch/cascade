package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.Person;
import ua.com.cascade.core.model.Realestate;

public class RealestateFieldSetMapper implements FieldSetMapper<Realestate> {
    public Realestate mapFieldSet(FieldSet fieldSet) {
        Realestate realestate = new Realestate();
        realestate.realestateOwnerName =  fieldSet.readString(1);
        realestate.realestateOwnerDob =  fieldSet.readString(2);
        realestate.address =  fieldSet.readString(3);
        realestate.area =  fieldSet.readString(4);

        return realestate;
    }
}

