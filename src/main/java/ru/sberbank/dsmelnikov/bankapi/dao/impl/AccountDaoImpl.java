package ru.sberbank.dsmelnikov.bankapi.dao.impl;

import org.springframework.stereotype.Repository;
import ru.sberbank.dsmelnikov.bankapi.dao.AccountDao;
import ru.sberbank.dsmelnikov.bankapi.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createAccount(Account account) {
        if (account != null) {
            entityManager.persist(account);
        }
    }

    @Override
    public void removeAccount(Long id) {
        Account account = entityManager.find(Account.class, id);

        if (account != null) {
            entityManager.remove(account);
        }
    }

    @Override
    public void updateAccount(Account account) {
        if (account != null) {
            entityManager.merge(account);
        }
    }

    @Override
    public Account getAccountById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public List<Account> listAccounts() {
        return entityManager.createQuery("from Account as acc join fetch acc.client", Account.class).getResultList();
    }
}
