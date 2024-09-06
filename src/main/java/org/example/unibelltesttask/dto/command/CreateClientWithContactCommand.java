package org.example.unibelltesttask.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientWithContactCommand {
    private String name;
    private List<String> emails;
    private List<String> numbers;
}
