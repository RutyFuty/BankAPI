package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.sberbank.dsmelnikov.bankapi.model.enums.AccountStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @Getter
    private Long id;

    @Column(unique = true)
    @Getter
    @Setter
    private String accountNumber;

    @Getter
    @Setter
    private BigDecimal accountBalance;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private AccountStatus accountStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonManagedReference
    @Getter
    @Setter
    private Client client;

    @OneToMany(mappedBy = "account")
    @JsonBackReference
    @Getter
    @Setter
    private Set<Card> cards;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountStatus='" + accountStatus + '\'' +
                ", client=\n  " + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber) && accountBalance.equals(account.accountBalance) && accountStatus == account.accountStatus && client.equals(account.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountBalance, accountStatus, client);
    }
}
