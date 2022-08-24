package z.hibernate.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Address {
    private int personId;
    private String address;
    private List<Person> personList;
}
