package br.com.fernanda.springbootinterview.service.impl;

import br.com.fernanda.springbootinterview.repository.CityRepository;
import br.com.fernanda.springbootinterview.service.CityService;
import br.com.fernanda.springbootinterview.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CityServiceImplTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Test
    void testFindByName() {
        City city = new City("City", "PE");
        City city2 = new City("City", "PB" );
        List<City> list = Arrays.asList(city, city2);

        Mockito.when(this.cityRepository.findByNameIgnoreCaseContaining(Mockito.anyString())).thenReturn(list);
        Assert.assertFalse(this.cityService.findByName("city").isEmpty());
    }

    @Test
    void testFindByState() {
        City city = new City("City1", "PE");
        City city2 = new City("City2", "PE" );
        List<City> list = Arrays.asList(city, city2);

        Mockito.when(this.cityRepository.findByStateIgnoreCaseContaining(Mockito.anyString())).thenReturn(list);
        Assert.assertFalse(this.cityService.findByState("PE").isEmpty());

    }
}