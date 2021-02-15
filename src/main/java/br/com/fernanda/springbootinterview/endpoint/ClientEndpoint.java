package br.com.fernanda.springbootinterview.endpoint;

import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.fernanda.springbootinterview.enums.GenderEnum;
import br.com.fernanda.springbootinterview.dto.ClientDTO;
import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientEndpoint {

    private static String MESSAGE_CLIENT_REGISTERED = "The client cannot be registered ";
    private static String MESSAGE_CLIENT_NOT_FOUND = "Client not found";


    @Autowired
    private ClientService clientService;

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@Valid @PathVariable("name") String name) {
        if(!this.verifyIfClientExistsByName(name)){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }


        Client client = clientService.findByName(name);
        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> edit(@Valid @PathVariable("id") long id) {
        this.verifyIfClientExistsById(id);
        Client client = clientService.findById(id);
        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(@Valid @RequestBody ClientDTO clientDTO) {
        this.verifyIfClientExistsByName(clientDTO.getName());
        Client client = clientService.findByName(clientDTO.getName());
        this.clientService.remove(client);
        return new ResponseEntity(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save( @Valid @RequestBody ClientDTO clientDTO) {
        Client client = new Client();

        if(!verifyIfClientExistsByName(clientDTO.getName())){
            if(DateUtil.isValidDate(clientDTO.getBirthDate())){
               client = this.clientMapper(clientDTO, client);
               this.clientService.save(client);
            }else{
                throw new InvalidArgumentException("Invalid birth date");
            }
        }
        else{
            throw new ResourceAlreadyRegisteredException(MESSAGE_CLIENT_REGISTERED);
        }

        clientDTO.setId(client.getId());
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PutMapping("/updateName")
    public ResponseEntity<?> update(@Valid @RequestBody ClientDTO clientDTO) {
        this.verifyIfClientExistsById(clientDTO.getId());

        Client client = this.clientService.findById(clientDTO.getId());
        client.setName(clientDTO.getName().toUpperCase());
        this.clientService.save(client);

        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }


    private boolean verifyIfClientExistsByName(String name){
        if (this.clientService.findByName(name.toUpperCase()) == null){
            return false;
        }
        return true;
    }

    private void verifyIfClientExistsById(Long id){
        if (this.clientService.findById(id) == null)
            throw new ResourceNotFoundException("Client not found for ID: "+id);
    }

    private Client clientMapper(ClientDTO clientDTO, Client client) {
        client.setName(clientDTO.getName().toUpperCase());

        if (clientDTO.getGender() == 'M') {
            client.setGender(GenderEnum.M);
        } else if (clientDTO.getGender() == 'F') {
            client.setGender(GenderEnum.F);
        }else{
            throw new InvalidArgumentException("Gender must be 'F' or 'M' in upper case.");
        }

       client.setCity(clientDTO.getCity());
       client.setBirthDate(DateUtil.convertStringToLocalDate(clientDTO.getBirthDate()));
       client.setAge(clientDTO.getAge());
       client.setId(clientDTO.getId());
       
       return client;
       
    }

    private ClientDTO clientDTOMapper(Client client, ClientDTO clientDTO){
        clientDTO.setName(client.getName());
        clientDTO.setGender(client.getGender().getValue());
        clientDTO.setCity(client.getCity());
        clientDTO.setBirthDate(DateUtil.convertLocalDateToString(client.getBirthDate()));
        clientDTO.setAge(client.getAge());
        clientDTO.setId(client.getId());

        return clientDTO;

    }
}
