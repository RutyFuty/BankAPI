package ru.sberbank.dsmelnikov.bankapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.dsmelnikov.bankapi.model.Account;
import ru.sberbank.dsmelnikov.bankapi.model.Card;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface AccountDao {

    void createAccount(Account account);

    void removeAccount(Long id);

    void updateAccount(Account account);

    Account getAccountById(Long id);

    List<Account> listAccounts();
}
