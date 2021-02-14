package br.com.fernanda.springbootinterview.service;

import br.com.fernanda.springbootinterview.model.City;

import java.util.List;

public interface CityService {
    void save(City city);
    List<City> findByName(String name);
    List<City> findByState(String state);
}
