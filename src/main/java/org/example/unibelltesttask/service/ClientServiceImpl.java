package org.example.unibelltesttask.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.unibelltesttask.dto.command.CreateClientCommand;
import org.example.unibelltesttask.dto.command.CreateClientWithContactCommand;
import org.example.unibelltesttask.entity.Client;
import org.example.unibelltesttask.entity.Contact;
import org.example.unibelltesttask.repository.ClientRepository;
import org.example.unibelltesttask.repository.ContactRepository;
import org.example.unibelltesttask.service.interfaces.ClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ContactRepository contactRepository;

    public Client createClient(CreateClientCommand command){
        Client client = Client.builder()
                .name(command.getName())
                .build();
        clientRepository.save(client);
        return client;
    }

    public Client createClientWithContacts(CreateClientWithContactCommand command){
            Contact contact = Contact.builder()
                    .emails(command.getEmails())
                    .numbers(command.getNumbers())
                    .build();

        contactRepository.save(contact);

        Client client = Client.builder()
                .name(command.getName())
                .contacts(List.of(contact))
                .build();
        clientRepository.save(client);
        return client;
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Client with id " + id + " not found"));
    }

    public List<Contact> getContactFromClient(Long id){
        Client client = getClientById(id);
        return client.getContacts();
    }

    public List<String> getContactTypeFromClient(Long id, String type){
        Client client = getClientById(id);
        List<Contact> contacts = client.getContacts();
        if(type.equals("numbers")){
           return contacts.stream().flatMap(contact -> contact.getNumbers().stream())
                   .toList();
        }
        else if(type.equals("emails")){
            return contacts.stream().flatMap(contact -> contact.getEmails().stream())
                    .toList();
        }
        else{
            throw new IllegalArgumentException("Invalid type of contact");
        }
    }

}
