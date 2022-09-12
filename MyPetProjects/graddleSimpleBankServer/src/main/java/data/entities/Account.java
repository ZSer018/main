package data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @Column(name = "account_num")
    private String accNum;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person personId;

    @Column(name = "opening_date")
    private Date openingDate;

    @Column(name = "available_amount")
    private int availableMoney;

    @Column(name = "currency_acc_type")
    private String accountType;

    @OneToMany(mappedBy = "senderAccountId")
    private List<Transaction> transactionList;


    @Override
    public String toString() {
        return "Account{" +
                "accNum='" + accNum + '\'' +
                ", openingDate=" + openingDate +
                ", availableMoney=" + availableMoney +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}

