package br.com.fernanda.springbootinterview.endpoint;

import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegistered;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientEndpoint {

    private static Logger logger;
    private static String message = "Client already registered";


    @Autowired
    private ClientService clientService;

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        if(!this.verifyIfClientExistsByName(name)){
            throw new ResourceNotFoundException("Client not registred");
        }

        Client client = clientService.findByName(name);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") long id) {
        this.verifyIfClientExistsById(id);
        Client client = clientService.findById(id);
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        this.verifyIfClientExistsById(id);
        Client client = clientService.findById(id);
        this.clientService.remove(client);
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Validated @RequestBody Client client) {
        if(!verifyIfClientExistsByName(client.getName()) && client.getId()==null){
            this.clientService.save(client);
        }
        else{
            throw new ResourceAlreadyRegistered(message);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> save(@PathParam("id")Long id, @RequestBody String name) {
        this.verifyIfClientExistsById(id);
        Client client = this.clientService.findById(id);
        client.setName(name);
        this.clientService.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


    private boolean verifyIfClientExistsByName(String name){
        if (this.clientService.findByName(name) == null){
            return false;
        }
        return true;
    }

    private void verifyIfClientExistsById(Long id){
        if (this.clientService.findById(id) == null)
            throw new ResourceNotFoundException("Client not found for ID: "+id);
    }
}
