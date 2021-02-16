package br.com.fernanda.springbootinterview.api;

import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.service.CityService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CityAPITest {

    @MockBean
    private CityService cityService;

    private CityAPI cityAPI;


    @BeforeEach
    void setUp(){
        cityAPI = new CityAPI();
        this.cityAPI.setCityService(this.cityService);
    }

    @Test
    void testFindByName() {
        City city = new City("City", "PE");
        City city2 = new City("City", "PB" );
        List<City> list = Arrays.asList(city, city2);

        Mockito.when(this.cityService.findByName(Mockito.anyString())).thenReturn(list);
        Assert.assertEquals(200, this.cityAPI.findByName("City").getStatusCode().value());
    }

    @Test
    void testFindByState() {
        City city = new City("City1", "PE");
        City city2 = new City("City2", "PE" );
        List<City> list = Arrays.asList(city, city2);

        Mockito.when(this.cityService.findByState(Mockito.anyString())).thenReturn(list);
        Assert.assertEquals(200, this.cityAPI.findByState("PE").getStatusCode().value());
    }

    @Test
    void testSave(){
        City city = new City("City1", "PE");
        ResponseEntity<City> responseEntity = (ResponseEntity<City>) this.cityAPI.save(city);
        Assert.assertEquals(201, responseEntity.getStatusCode().value());
    }



}