package br.com.fernanda.springbootinterview.service.impl;

import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.repository.ClientRepository;
import br.com.fernanda.springbootinterview.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void save(Client client) {clientRepository.save(client);}
    @Override
    public void remove(Client client) {clientRepository.delete(client);}
    @Override
    public Client findById(Long id) {
        return clientRepository.getOne(id);
    }
    @Override
    public Client findByName(String name) {return clientRepository.findByNameIgnoreCaseContaining(name);}

}
