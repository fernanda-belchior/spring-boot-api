package br.com.fernanda.springbootinterview.api;

import br.com.fernanda.springbootinterview.dto.ClientDTO;
import br.com.fernanda.springbootinterview.enums.GenderEnum;
import br.com.fernanda.springbootinterview.exception.InvalidArgumentException;
import br.com.fernanda.springbootinterview.exception.ResourceAlreadyRegisteredException;
import br.com.fernanda.springbootinterview.exception.ResourceNotFoundException;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.service.ClientService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientAPITest {


    @MockBean
    private ClientService clientService;

    @Autowired
    private TestRestTemplate restTemplate;

    private ClientAPI clientAPI;


    @BeforeEach
    void setUp(){
        clientAPI = new ClientAPI();
        this.clientAPI.setClientService(this.clientService);
    }

    @Test
    void findByName() throws Exception {
        Client client = new Client(1L,"ALINE", GenderEnum.F,
                LocalDate.of(1995, 12, 05),
                25, new City("RECIFE", "PE"));

        this.clientService.save(client);
        Mockito.when(this.clientService.findByName(Mockito.anyString())).thenReturn(client);
        ResponseEntity<ClientDTO> response = (ResponseEntity<ClientDTO>) this.clientAPI.findByName("ALINE");
        Assert.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testFindById() {
        Client client = new Client(2L,"MARCOS", GenderEnum.M,
                LocalDate.of(1990, 05, 12),
                30, new City("RECIFE", "PE"));

        this.clientService.save(client);
        Mockito.when(clientService.findById(2L)).thenReturn(client);
        ResponseEntity<ClientDTO> response = (ResponseEntity<ClientDTO>) this.clientAPI.findById(2L);
        Assert.assertEquals(200, response.getStatusCode().value());
    }


    @Test
    void testSave() {
        ClientDTO clientDTO = new ClientDTO(2L,"MARCOS", 'M',
                "1980-03-18",30, new City("RECIFE", "PE"));

        ResponseEntity<ClientDTO> responseEntity = (ResponseEntity<ClientDTO>) this.clientAPI.save(clientDTO);
        Assert.assertEquals(201, responseEntity.getStatusCode().value());
    }


    @Test
    void testRemove() {
        ClientDTO clientDTO = new ClientDTO(2L,"MARCOS", 'M',
                "1980-03-18",30, new City("RECIFE", "PE"));
        this.clientAPI.save(clientDTO);
        ResponseEntity<ClientDTO> response = (ResponseEntity<ClientDTO>) this.clientAPI.remove(clientDTO);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void testResourceNotFound() {
        try{
            this.clientAPI.findById(10L);
        }
        catch(ResourceNotFoundException e){
            Assertions.assertThatExceptionOfType(ResourceNotFoundException.class);
        }
    }

    @Test
    void testInvalidArgument() {

        ClientDTO clientDTO = new ClientDTO(2L,"MARCOS", 'L',
                "",30, new City("RECIFE", "PE"));

        try{
            this.clientAPI.save(clientDTO);
        }
        catch(InvalidArgumentException e){
            Assertions.assertThatExceptionOfType(InvalidArgumentException.class);
        }

    }

    @Test
    void ResourceAlreadyRegistered(){
        ClientDTO clientDTO = new ClientDTO(2L,"MARCOS", 'M',
                "1990-03-03",30, new City("RECIFE", "PE"));

        try{
            this.clientAPI.save(clientDTO);
            this.clientAPI.save(clientDTO);
        }
        catch(ResourceAlreadyRegisteredException e){
            Assertions.assertThatExceptionOfType(ResourceAlreadyRegisteredException.class);
        }

    }
}