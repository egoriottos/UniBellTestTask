package org.example.unibelltesttask;

import org.example.unibelltesttask.controller.ClientController;
import org.example.unibelltesttask.dto.command.CreateClientCommand;
import org.example.unibelltesttask.dto.command.CreateClientWithContactCommand;
import org.example.unibelltesttask.dto.request.ClientRequest;
import org.example.unibelltesttask.dto.request.ClientWithContactsRequest;
import org.example.unibelltesttask.dto.response.ClientResponse;
import org.example.unibelltesttask.dto.response.ContactResponse;
import org.example.unibelltesttask.dto.response.ContactTypeResponse;
import org.example.unibelltesttask.entity.Client;
import org.example.unibelltesttask.entity.Contact;
import org.example.unibelltesttask.service.interfaces.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ModelMapper modelMapper;


    @Test
    public void testCreateClient() {
        MockitoAnnotations.initMocks(this);

        CreateClientCommand command = new CreateClientCommand();
        command.setName("Egor");

        Client client = Client.builder()
                .id(1L)
                .name("Egor")
                .contacts(null)
                .build();

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setName(client.getName());

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setName("Egor");
        clientResponse.setContacts(null);

        when(modelMapper.map(clientRequest, CreateClientCommand.class)).thenReturn(command);
        when(clientService.createClient(command)).thenReturn(client);
        when(modelMapper.map(client, ClientResponse.class)).thenReturn(clientResponse);
        ResponseEntity<ClientResponse> responseEntity = clientController.createClient(clientRequest);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(clientResponse, responseEntity.getBody());
    }

    @Test
    public void testCreateClientWithContacts() {
        MockitoAnnotations.initMocks(this);

        CreateClientWithContactCommand command = new CreateClientWithContactCommand();
        command.setName("Egor");
        command.setEmails(List.of("123@mail.ru", "1234@mail.ru"));
        command.setNumbers(List.of("88005553535", "7964812939865"));

        Contact contact = Contact.builder()
                .numbers(command.getNumbers())
                .emails(command.getEmails())
                .build();

        Client client = Client.builder()
                .id(1L)
                .name("Egor")
                .contacts(List.of(contact))
                .build();

        ClientWithContactsRequest clientRequest = new ClientWithContactsRequest();
        clientRequest.setName("Egor");
        clientRequest.setNumbers(List.of("88005553535", "7964812939865"));
        clientRequest.setEmails(List.of("123@mail.ru", "1234@mail.ru"));

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setName("Egor");
        clientResponse.setContacts(List.of(contact));

        when(modelMapper.map(clientRequest, CreateClientWithContactCommand.class)).thenReturn(command);
        when(clientService.createClientWithContacts(command)).thenReturn(client);
        when(modelMapper.map(client, ClientResponse.class)).thenReturn(clientResponse);
        ResponseEntity<ClientResponse> responseEntity = clientController.createClientWithContacts(clientRequest);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(clientResponse, responseEntity.getBody());
    }

    @Test
    public void testFindAllClients() {
        MockitoAnnotations.initMocks(this);

        Client client1 = Client.builder()
                .id(1L)
                .name("Egoriottos")
                .contacts(null)
                .build();

        Client client2 = Client.builder()
                .id(2L)
                .name("Igor")
                .contacts(null)
                .build();

        Client client3 = Client.builder()
                .id(3L)
                .name("Egor")
                .contacts(null)
                .build();

        ClientResponse clientResponse1 = new ClientResponse();
        clientResponse1.setName("Egoriottos");
        clientResponse1.setContacts(null);

        ClientResponse clientResponse2 = new ClientResponse();
        clientResponse2.setName("Igor");
        clientResponse2.setContacts(null);

        ClientResponse clientResponse3 = new ClientResponse();
        clientResponse3.setName("Egor");
        clientResponse3.setContacts(null);

        List<Client> clients = List.of(client1, client2, client3);
        List<ClientResponse> clientResponses = List.of(clientResponse1, clientResponse2, clientResponse3);

        when(clientService.getAllClients()).thenReturn(clients);
        when(modelMapper.map(client1, ClientResponse.class)).thenReturn(clientResponse1);
        when(modelMapper.map(client2, ClientResponse.class)).thenReturn(clientResponse2);
        when(modelMapper.map(client3, ClientResponse.class)).thenReturn(clientResponse3);

        ResponseEntity<List<ClientResponse>> response = clientController.getAllClients();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientResponses, response.getBody());
    }

    @Test
    public void testFindClientById() {
        MockitoAnnotations.openMocks(this);

        Client client1 = Client.builder()
                .id(1L)
                .name("Egoriottos")
                .contacts(null)
                .build();

        ClientResponse clientResponse1 = new ClientResponse();
        clientResponse1.setName("Egoriottos");
        clientResponse1.setContacts(null);

        when(clientService.getClientById(1L)).thenReturn(client1);
        when(modelMapper.map(client1, ClientResponse.class)).thenReturn(clientResponse1);
        ResponseEntity<ClientResponse> response = clientController.getClientById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientResponse1, response.getBody());
    }

    @Test
    public void testGetClientInfoById() {
        MockitoAnnotations.openMocks(this);

        Contact contact = Contact.builder()
                .numbers(List.of("88005553535", "7964812939865"))
                .emails(List.of("@mail.ru", "@yandex.ru"))
                .build();

        Client client = Client.builder()
                .id(1L)
                .name("Egor")
                .contacts(List.of(contact))
                .build();

        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setEmails(contact.getEmails());
        contactResponse.setNumbers(contact.getNumbers());


        when(clientService.getContactFromClient(1L)).thenReturn(List.of(contact));
        when(modelMapper.map(contact, ContactResponse.class)).thenReturn(contactResponse);
        ResponseEntity<List<ContactResponse>> response = clientController.getClientInfoById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(contactResponse), response.getBody());
    }

    @Test
    public void testGetClientInfoByIdType() throws Exception {
        MockitoAnnotations.openMocks(this);

        List<String> numbers = List.of("88005553535", "7964812939865");
        List<String> emails = List.of("@mail.ru", "@yandex.ru");

        ContactTypeResponse numbersResponse = new ContactTypeResponse();
        numbersResponse.setContactType(numbers);

        ContactTypeResponse emailsResponse = new ContactTypeResponse();
        emailsResponse.setContactType(emails);

        when(clientService.getContactTypeFromClient(1L, "numbers")).thenReturn(numbers);
        when(clientService.getContactTypeFromClient(1L, "emails")).thenReturn(emails);

        ResponseEntity<ContactTypeResponse> responseNumbers = clientController.getClientInfoByIdType(1L, "numbers");
        assertEquals(200, responseNumbers.getStatusCodeValue());
        assertEquals(numbersResponse, responseNumbers.getBody());

        ResponseEntity<ContactTypeResponse> responseEmails = clientController.getClientInfoByIdType(1L, "emails");
        assertEquals(200, responseEmails.getStatusCodeValue());
        assertEquals(emailsResponse, responseEmails.getBody());

    }
}
