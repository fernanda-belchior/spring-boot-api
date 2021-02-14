package br.com.fernanda.springbootinterview.endpoint;

import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegistered;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityEndpoint {

    private static Logger logger;
    private static String MESSAGE = "City already registered";

    @Autowired
    private CityService cityService;

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        List<City> list = this.cityService.findByName(name);
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException("");
        }
        logger.info("teste");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByState/{state}")
    public ResponseEntity<?> findByState(@PathVariable("state") String state) {
        List<City> list = this.cityService.findByState(state);
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException("");
        }

        logger.info("teste");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Validated @RequestBody City city) {
        List<City> list = this.cityService.findByName(city.getName());

        if (list != null || !list.isEmpty()) {
            for (City city2 : list) {
                if (city2.getName().equals(city.getName())
                        && city2.getState().equals(city.getState())) {
                    throw new ResourceAlreadyRegistered(MESSAGE);
                }
            }
        }
        this.cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);

    }

}
