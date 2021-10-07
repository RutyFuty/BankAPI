package ru.sberbank.dsmelnikov.bankapi.dao;

import ru.sberbank.dsmelnikov.bankapi.model.Account;

import java.util.List;

public interface AccountDao {

    void createAccount(Account account);

    void removeAccount(Long id);

    void updateAccount(Account account);

    Account getAccountById(Long id);

    List<Account> listAccounts();
}
