package org.example.unibelltesttask.service.interfaces;

import org.example.unibelltesttask.dto.command.CreateClientCommand;
import org.example.unibelltesttask.dto.command.CreateClientWithContactCommand;
import org.example.unibelltesttask.entity.Client;
import org.example.unibelltesttask.entity.Contact;

import java.util.List;

public interface ClientService {
    Client createClient(CreateClientCommand command);

    Client createClientWithContacts(CreateClientWithContactCommand command);

    List<Client> getAllClients();

    Client getClientById(Long id);

    List<Contact> getContactFromClient(Long id);

    List<String> getContactTypeFromClient(Long id, String type);
}
