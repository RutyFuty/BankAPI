package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.sberbank.dsmelnikov.bankapi.model.enums.TransactionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "src_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @Getter
    @Setter
    private Account txSrcAccount;

    @ManyToOne
    @JoinColumn(name = "dst_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @Getter
    @Setter
    private Account txDstAccount;

    @Getter
    @Setter
    private BigDecimal txAmount;

    @Temporal(TemporalType.TIME)
    @Getter
    @Setter
    private Date txDateTime;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private TransactionStatus txStatus;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", txSrcAccount=" + txSrcAccount +
                ", txDstAccount=" + txDstAccount +
                ", txAmount=" + txAmount +
                ", txDateTime=" + txDateTime +
                ", txStatus=" + txStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) && txSrcAccount.equals(that.txSrcAccount) && txDstAccount.equals(that.txDstAccount) && txAmount.equals(that.txAmount) && txDateTime.equals(that.txDateTime) && txStatus == that.txStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, txSrcAccount, txDstAccount, txAmount, txDateTime, txStatus);
    }
}
