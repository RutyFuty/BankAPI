package ru.sberbank.dsmelnikov.bankapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.sberbank.dsmelnikov.bankapi.model.enums.TransactionStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
