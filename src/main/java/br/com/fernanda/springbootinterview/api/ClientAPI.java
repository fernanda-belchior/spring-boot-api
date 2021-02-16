package br.com.fernanda.springbootinterview.api;

import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.model.City;
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
import javax.validation.Valid;
import lombok.Setter;


@Setter
@RestController
@RequestMapping("/client")
@Api(value = "Client API")
public class ClientAPI {

    private static final String MESSAGE_CLIENT_REGISTERED = "The client cannot be registered ";
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
        Client client = clientService.findByName(name.toUpperCase());
        if(client == null ){
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
        }
        ClientDTO clientDTO = new ClientDTO();
        clientDTO = this.clientDTOMapper(client, clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "** FIND CLIENT BY ID **")
    public ResponseEntity<?> findById( @PathVariable("id") long id) {
        this.verifyIfClientExistsById(id);
        Client client = clientService.findById(id);
        ClientDTO clientDTO =  this.clientDTOMapper(client, new ClientDTO());
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "** REMOVE CLIENT **")
    public ResponseEntity<?> remove( @RequestBody ClientDTO clientDTO) {
        this.verifyIfClientExistsByName(clientDTO.getName());
        Client client = clientService.findByName(clientDTO.getName());
        this.clientService.remove(client);
        return new ResponseEntity(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation(value = "** SAVE CLIENT **")
    public ResponseEntity<?> save( @Valid @RequestBody ClientDTO clientDTO) {
        Client client = new Client();
        City city = this.cityService.validateCity(clientDTO.getCity());

        if(!verifyIfClientExistsByName(clientDTO.getName())){
            if(DateUtil.isValidDate(clientDTO.getBirthDate())){
               client = this.clientMapper(clientDTO, client);
               client.setCity(city);
               client.setId(null);
               this.clientService.save(client);
            }else{
                throw new InvalidArgumentException(MESSAGE_INVALID_BIRTH_DATE);
            }
        }
        else{
            throw new ResourceAlreadyRegisteredException(MESSAGE_CLIENT_REGISTERED);
        }

        clientDTO.setId(client.getId());
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @PutMapping("/updateName")
    @ApiOperation(value = "** UPDATE CLIENT NAME **")
    public ResponseEntity<?> updateName( @RequestBody ClientDTO clientDTO) {
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
            throw new ResourceNotFoundException(MESSAGE_CLIENT_NOT_FOUND);
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
