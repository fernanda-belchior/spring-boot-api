package br.com.fernanda.springbootinterview.service.impl;

import br.com.fernanda.springbootinterview.repository.CityRepository;
import br.com.fernanda.springbootinterview.service.CityService;
import br.com.fernanda.springbootinterview.model.City;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.junit.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
class CityServiceImplTest {

    @Mock
    CityService cityService;

    @Mock
    CityRepository cityRepository;

//    @Test
//    void testSave() {
//        City city = new City();
//        city.setState("PE");
//        city.setName("Recife");
//
//        Mockito.when(this.cityRepository.save(city)).thenReturn(city);
//        Assert.assertEquals("Recife", this.cityService.save(city).getName());
//    }

    @Test
    void testFindByName() {
    }

    @Test
    void testFindByState() {
    }
}