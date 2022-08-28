package z.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Table(name = "Person_data")
public class PersonData {

    @Id
    @Column (name = "person_id")
    private int personid;

    private Date birthday;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_num")
    private int passportNum;

    @Column(name = "issued_by")
    private String issuedBy;

    private String address;

}
