package br.com.fernanda.springbootinterview.api;

import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Setter
@RestController
@RequestMapping("/city")
@Api(value = "City API")
public class CityAPI {

    private static final String MESSAGE_CITY_REGISTERED = "City already registered";
    private static final String MESSAGE_CITY_NOT_FOUND = "City not found";
    private static final String MESSAGE_INVALID_STATE = "State is not exist. Plese type in the format = 'LL'";

    private static List<String> stateList = Arrays.asList(
            "AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MS",
            "MT","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO");


    @Autowired
    private CityService cityService;

    @GetMapping("/findByName/{name}")
    @ApiOperation(value = "** FIND CITY BY NAME **")
    public ResponseEntity<?> findByName( @PathVariable("name") String name) {
        List<City> list = this.cityService.findByName(name.toUpperCase());
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException(MESSAGE_CITY_NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByState/{state}")
    @ApiOperation(value = "** FIND CITY BY STATE **")
    public ResponseEntity<?> findByState( @PathVariable("state") String state) {
        List<City> list = this.cityService.findByState(state.toUpperCase());
        if (list == null || list.isEmpty()){
            throw new ResourceNotFoundException(MESSAGE_CITY_NOT_FOUND);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation(value = "** SAVE CITY **")
    public ResponseEntity<?> save(@Valid @RequestBody City city) {
        List<City> list = this.cityService.findByName(city.getName());
        if(!stateList.contains(city.getState().toUpperCase())){
            throw new InvalidArgumentException(MESSAGE_INVALID_STATE);
        }

        if (list != null && !list.isEmpty()) {
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
        return new ResponseEntity<>(city, HttpStatus.CREATED);

    }

}
