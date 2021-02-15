package br.com.fernanda.springbootinterview.endpoint;

import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityEndpoint {

    private static String MESSAGE_CITY_REGISTERED = "City already registered";
    private static String MESSAGE_CITY_NOT_FOUND = "City not found";
    private static List<String> stateList = Arrays.asList(
            "AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MS",
            "MT","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO");

    @Autowired
    private CityService cityService;

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@Valid @PathVariable("name") String name) {
        List<City> list = this.cityService.findByName(name.toUpperCase());
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException(MESSAGE_CITY_NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByState/{state}")
    public ResponseEntity<?> findByState(@Valid @PathVariable("state") String state) {
        List<City> list = this.cityService.findByState(state.toUpperCase());
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException(MESSAGE_CITY_NOT_FOUND);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody City city) {
        List<City> list = this.cityService.findByName(city.getName());
        if(!stateList.contains(city.getState().toUpperCase())){
            throw new InvalidArgumentException("State is not exist. Plese type the state with two letter acronym");
        }

        if (list != null || !list.isEmpty()) {
            for (City city2 : list) {
                if (city2.getName().equalsIgnoreCase(city.getName())
                        && city2.getState().equalsIgnoreCase(city.getState())) {
                    throw new ResourceAlreadyRegisteredException(MESSAGE_CITY_REGISTERED);
                }
            }
        }
        city.setName(city.getName().toUpperCase());
        city.setState(city.getState().toUpperCase());
        this.cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);

    }

}
