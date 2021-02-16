package br.com.fernanda.springbootinterview.service.impl;

import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.repository.CityRepository;
import br.com.fernanda.springbootinterview.service.CityService;
import br.com.fernanda.springbootinterview.util.StateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public void save(City city) {cityRepository.save(city);}

    @Override
    public List<City> findByName(String name) {
        return cityRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public List<City> findByState(String state) {return cityRepository.findByStateIgnoreCaseContaining(state);}

    @Override
    public City validateCity(City city){


        if(city.getName()==null || city.getName().isEmpty()){
            throw new InvalidArgumentException("City shouldn't be blank.");
        }

        if(city.getState()==null || city.getState().isEmpty()){
            throw new InvalidArgumentException("State shouldn't be blank.");
        }

        if(!StateUtil.getStateList().contains(city.getState().toUpperCase())){
            throw new InvalidArgumentException("State is not exist. Plese enter" +
                    " a valid state in the format = 'LL'.");
        }

        List<City> list = this.findByName(city.getName());

        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException("City not yet registered.");
        }

        for (City city2 : list) {
            if (city2.getName().equalsIgnoreCase(city.getName())
                        && city2.getState().equalsIgnoreCase(city.getState())) {
                    return city2;
                }
        }

        throw new ResourceNotFoundException("City not yet registered.");

    }

}
