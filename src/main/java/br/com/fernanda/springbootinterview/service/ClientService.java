package br.com.fernanda.springbootinterview.service;

import br.com.fernanda.springbootinterview.model.Client;

public interface ClientService {

    void save(Client client);
    void remove(Client client) ;
    Client findById(Long id) ;
    Client findByName(String name) ;
}
