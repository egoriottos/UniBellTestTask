package org.example.unibelltesttask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.unibelltesttask.entity.Contact;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String name;
    private List<Contact> contacts;
}
