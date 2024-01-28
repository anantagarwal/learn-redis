package io.anant.learnredis.repository;

import io.anant.learnredis.documents.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CompanyRepository extends CrudRepository<Company, String> {
}
