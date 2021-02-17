package br.com.fernanda.springbootinterview.api;

import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.service.CityService;
import br.com.fernanda.springbootinterview.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.fernanda.springbootinterview.enums.GenderEnum;
import br.com.fernanda.springbootinterview.dto.ClientDTO;
import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import lombok.Setter;


@Setter
@RestController
@RequestMapping("/client")
@Api(value = "Client API")
public class ClientAPI {

    private static final String MESSAGE_CLIENT_REGISTERED = "Client already registered ";
    private static final String MESSAGE_CLIENT_NOT_FOUND = "Client not found";
    private static final String MESSAGE_INVALID_GENDER = "Gender must be 'F' or 'M' in upper case.";
    private static final String MESSAGE_INVALID_BIRTH_DATE = "Invalid birth date. " +
            "Please enter a valid date";

    @Autowired
    private ClientService clientService;

    @Autowired
    private CityService cityService;

    @GetMapping("/findByName/{name}")
    @ApiOperation(value = "** FIND CLIENT BY NAME **")
    public ResponseEntity<?> findByName( @PathVariable("name") String name) {
        Client client = this.clientService.findByName(name);
        if (client == null){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }
        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "** FIND CLIENT BY ID **")
    public ResponseEntity<?> findById( @PathVariable("id") long id) {
        Client client = this.clientService.findById(id);
        if (client == null){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "** REMOVE CLIENT **")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable("id") long id) {

        Client client = this.clientService.findById(id);
        if (client == null){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }
        this.clientService.remove(client);
        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation(value = "** SAVE CLIENT **")
    public ResponseEntity<?> save( @Valid @RequestBody ClientDTO clientDTO) {
        this.cityService.validateCity(clientDTO.getCity());

        Client client = this.clientService.findByName(clientDTO.getName());

        if (client == null){
            if(DateUtil.isValidDate(clientDTO.getBirthDate())){
               client = new Client();
               client = this.clientMapper(clientDTO, client);
               client.setId(null);
               this.clientService.save(client);
            }else{
                throw new InvalidArgumentException(MESSAGE_INVALID_BIRTH_DATE);
            }
        }else{
            throw new ResourceAlreadyRegisteredException(MESSAGE_CLIENT_REGISTERED);
        }

        Client client2 = this.clientService.findByName(clientDTO.getName());
        clientDTO = this.clientDTOMapper(client2, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @PutMapping("/updateName/{id}")
    @ApiOperation(value = "** UPDATE CLIENT NAME **")
    @Transactional
    public ResponseEntity<?> updateName( @PathVariable("id") long id, @RequestBody String name) {

        Client client = this.clientService.findById(id);

        if (client == null){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }

        client.setName(name);
        this.clientService.save(client);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }


    private Client clientMapper(ClientDTO clientDTO, Client client) {

        client.setName(clientDTO.getName().toUpperCase());

        if (clientDTO.getGender() == 'M') {
            client.setGender(GenderEnum.M);
        } else if (clientDTO.getGender() == 'F') {
            client.setGender(GenderEnum.F);
        }else{
            throw new InvalidArgumentException(MESSAGE_INVALID_GENDER);
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
