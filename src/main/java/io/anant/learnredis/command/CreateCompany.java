package io.anant.learnredis.command;

import io.anant.learnredis.documents.Company;
import io.anant.learnredis.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(3)
@Slf4j
@AllArgsConstructor
public class CreateCompany implements CommandLineRunner {

    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        if (companyRepository.count() ==0 && false) {
            companyRepository.deleteAll();
            Company redis = Company.of("1","Redis", "https://redis.com", 526, 2011);
            redis.setTags(Set.of("fast", "scalable", "reliable"));

            Company microsoft = Company.of("2","Microsoft", "https://microsoft.com", 182268, 1975);
            microsoft.setTags(Set.of("innovative", "reliable"));

            companyRepository.save(redis);
            companyRepository.save(microsoft);

            log.info("Saved company info as a json datatype");
        }

    }
}
