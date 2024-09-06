package org.example.unibelltesttask.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientWithContactsRequest {
    private String name;
    private List<String> numbers;
    private List<String> emails;
}
