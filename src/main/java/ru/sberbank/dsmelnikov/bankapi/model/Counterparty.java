package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CounterpartyStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "counterparty")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Counterparty {

    @Id
    @SequenceGenerator(name = "counterparty_sequence", sequenceName = "counterparty_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counterparty_sequence")
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonManagedReference
    @Getter
    @Setter
    private Account account;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private CounterpartyStatus counterpartyStatus;

    @Override
    public String toString() {
        return "Counterparty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account=" + account +
                ", counterpartyStatus=" + counterpartyStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counterparty that = (Counterparty) o;
        return name.equals(that.name) && account.equals(that.account) && counterpartyStatus == that.counterpartyStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, account, counterpartyStatus);
    }
}
