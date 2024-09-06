package org.example.unibelltesttask.controller;

import lombok.RequiredArgsConstructor;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        Client response = clientService.createClient(modelMapper.map(clientRequest, CreateClientCommand.class));
        return ResponseEntity.ok(modelMapper.map(response, ClientResponse.class));
    }
    @PostMapping("/create/withInfo")
    public ResponseEntity<ClientResponse> createClientWithContacts(@RequestBody ClientWithContactsRequest clientRequest) {
        Client response = clientService.createClientWithContacts(modelMapper.map(clientRequest, CreateClientWithContactCommand.class));
        return ResponseEntity.ok(modelMapper.map(response, ClientResponse.class));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientResponse> clientResponses = clients.stream()
                .map(client -> modelMapper.map(client, ClientResponse.class)).toList();
        return ResponseEntity.ok(clientResponses);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(modelMapper.map(client, ClientResponse.class));
    }
    @GetMapping("/get/info/{id}")
    public ResponseEntity<List<ContactResponse>> getClientInfoById(@PathVariable Long id) {
        List<Contact> contacts = clientService.getContactFromClient(id);
        return ResponseEntity.ok(contacts.stream().map(obj -> modelMapper.map(obj, ContactResponse.class)).toList());
    }
    @GetMapping("/get/info/type/{id}")
    public ResponseEntity<ContactTypeResponse> getClientInfoByIdType(@PathVariable Long id, @RequestParam String type) {
        List<String> contacts = clientService.getContactTypeFromClient(id, type);
        ContactTypeResponse contactTypeResponse = new ContactTypeResponse();
        contactTypeResponse.setContactType(contacts);
        return ResponseEntity.ok(contactTypeResponse);
    }
}
