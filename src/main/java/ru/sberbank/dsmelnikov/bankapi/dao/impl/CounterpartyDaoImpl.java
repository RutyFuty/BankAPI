package ru.sberbank.dsmelnikov.bankapi.dao.impl;

import org.springframework.stereotype.Repository;
import ru.sberbank.dsmelnikov.bankapi.dao.CounterpartyDao;
import ru.sberbank.dsmelnikov.bankapi.model.Counterparty;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CounterpartyDaoImpl implements CounterpartyDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createCounterparty(Counterparty counterparty) {
        entityManager.persist(counterparty);
    }

    @Override
    public void removeCounterparty(Long id) {
        Counterparty counterparty = entityManager.find(Counterparty.class, id);

        if (counterparty != null) {
            entityManager.remove(counterparty);
        }
    }

    @Override
    public void updateCounterparty(Counterparty counterparty) {
        entityManager.merge(counterparty);
    }

    @Override
    public Counterparty getCounterpartyById(Long id) {
        return entityManager.find(Counterparty.class, id);
    }

    @Override
    public List<Counterparty> listCounterparties() {
        return entityManager.createQuery("from Counterparty as crpt join fetch crpt.account as acc join fetch acc.client", Counterparty.class)
                .getResultList();
    }
}
