package br.com.fernanda.springbootinterview.service.impl;

import br.com.fernanda.springbootinterview.enums.GenderEnum;
import br.com.fernanda.springbootinterview.model.City;
import br.com.fernanda.springbootinterview.model.Client;
import br.com.fernanda.springbootinterview.repository.ClientRepository;
import br.com.fernanda.springbootinterview.service.ClientService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    void testFindById() {

        Client client = new Client (1L,"MARCOS", GenderEnum.M,
                LocalDate.of(1990, 05, 12),30, new City("RECIFE", "PE"));


        Mockito.when(clientService.findById(1L)).thenReturn(client);
        Assert.assertEquals("MARCOS", this.clientService.findById(Long.valueOf(1)).getName());
    }

    @Test
    void testFindByName() {
        Client client = new Client (Long.valueOf(2), "ALINE", GenderEnum.F,
                LocalDate.of(1995, 12, 05),
                25, new City("RECIFE", "PE"));

        Mockito.when(this.clientRepository.findByNameIgnoreCaseContaining(Mockito.anyString())).thenReturn(client);
        Assert.assertEquals(Integer.valueOf(25), this.clientService.findByName("aline").getAge());
    }


}