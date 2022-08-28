package z.hibernate.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Set;

@Data
public class Address {
    private Person personId;
    private String address;
    private Set<Person> personList;
}
