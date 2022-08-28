package z.hibernate.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    private Date datetime;

    private Account sender_account_id;
    private Account receiver_account_id;

    private int amount;
}


