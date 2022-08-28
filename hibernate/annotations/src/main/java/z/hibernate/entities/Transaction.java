package z.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;
    private int receiverId;
    private int senderId;
    private int amount;
}
