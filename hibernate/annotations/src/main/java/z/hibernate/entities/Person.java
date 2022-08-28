package z.hibernate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Person {

    @Id
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    @OneToMany(mappedBy = "personId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accountList;

    @OneToMany(mappedBy = "personId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loanList;

    @OneToMany(mappedBy = "personId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guarantor> guarantorList;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
