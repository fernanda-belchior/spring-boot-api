package br.com.fernanda.springbootinterview.repository;

import br.com.fernanda.springbootinterview.model.City;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Test
    public void testFindByName() {
        City city = new City();
        city.setName("Re");
        city.setState("PE");
        this.cityRepository.save(city);

        List<City> list = this.cityRepository.findByNameIgnoreCaseContaining("Reci");
//        Assert.assertEquals("Recife", city2.getName());
    }

    @Test
    public void testFindByState() {
    }


}