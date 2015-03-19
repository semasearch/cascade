package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.FamilyRelationship;

public class FamilyRelationshipFieldSetMapper implements FieldSetMapper<FamilyRelationship> {
    public FamilyRelationship mapFieldSet(FieldSet fieldSet) {
        FamilyRelationship f = new FamilyRelationship();
        f.firstPersonName = fieldSet.readString(1);
        f.firstPersonDob = fieldSet.readString(2);
        f.relationshipType = fieldSet.readString(3);
        f.secondPersonName= fieldSet.readString(4);
        f.secondPersonDob= fieldSet.readString(5);
        return f;
    }
}

