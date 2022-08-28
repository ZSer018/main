package z.hibernate.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @Column(name = "account_num")
    private String accNum;

    @Column(name = "available_amount")
    private int money;

    @Column(name = "currency_acc_type")
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person personId;

}
