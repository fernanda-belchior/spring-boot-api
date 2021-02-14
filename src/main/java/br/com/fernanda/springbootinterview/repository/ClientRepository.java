package br.com.fernanda.springbootinterview.repository;

import br.com.fernanda.springbootinterview.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByNameIgnoreCaseContaining(String name);}
