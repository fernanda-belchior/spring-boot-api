package br.com.fernanda.springbootinterview.repository;

import br.com.fernanda.springbootinterview.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByNameIgnoreCaseContaining(String name);
    List<City> findByStateIgnoreCaseContaining(String state);

}
