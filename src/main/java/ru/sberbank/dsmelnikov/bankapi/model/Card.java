package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CardStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "card")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @Getter
    private Long id;

    @Column(unique = true)
    @Getter
    @Setter
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonManagedReference
    @Getter
    @Setter
    private Account account;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private CardStatus cardStatus;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNumber.equals(card.cardNumber) && account.equals(card.account) && cardStatus == card.cardStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, account, cardStatus);
    }
}
