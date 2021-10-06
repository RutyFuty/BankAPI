package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @SequenceGenerator(name = "client_sequence", sequenceName = "client_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @Getter
    private Long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String middleName;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    @Getter
    @Setter
    private Set<Account> accounts;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName +
                ", lastName='" + lastName +
                ", middleName='" + middleName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id) && firstName.equals(client.firstName) && lastName.equals(client.lastName) && middleName.equals(client.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName);
    }
}
