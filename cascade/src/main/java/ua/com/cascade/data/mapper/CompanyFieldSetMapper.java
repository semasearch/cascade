package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.Company;

public class CompanyFieldSetMapper implements FieldSetMapper<Company> {
    public Company mapFieldSet(FieldSet fieldSet) {
        Company company = new Company();
        company.name = fieldSet.readString(1);
        company.rawOwners = fieldSet.readString(2);

        return company;
    }
}

