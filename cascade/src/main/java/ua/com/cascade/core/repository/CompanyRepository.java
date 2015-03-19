package ua.com.cascade.core.repository;


import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import ua.com.cascade.core.model.Company;

public interface CompanyRepository extends GraphRepository<Company> {

    Company findByName(String name);

    Company findById(Long id);

}
