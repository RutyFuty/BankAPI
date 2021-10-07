package ru.sberbank.dsmelnikov.bankapi.dao.impl;

import org.springframework.stereotype.Repository;
import ru.sberbank.dsmelnikov.bankapi.dao.CardDao;
import ru.sberbank.dsmelnikov.bankapi.model.Card;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CardDaoImpl implements CardDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createCard(Card card) {
        entityManager.persist(card);
    }

    @Override
    public void removeCard(Long id) {
        Card card = entityManager.find(Card.class, id);

        if (card != null) {
            entityManager.remove(card);
        }
    }

    @Override
    public void updateCard(Card card) {
        entityManager.merge(card);
    }

    @Override
    public Card getCardById(Long id) {

        return entityManager.find(Card.class, id);
    }

    @Override
    public List<Card> listCards() {
        return entityManager.createQuery("from Card as crd join fetch crd.account as acc join fetch acc.client", Card.class).getResultList();
    }

    public List<Card> listCardsOnAccount(Long accountId) {
        return entityManager.createQuery("from Card as c join fetch c.account as acc where acc.id=:id", Card.class)
                .setParameter("id", accountId)
                .getResultList();
    }
}
