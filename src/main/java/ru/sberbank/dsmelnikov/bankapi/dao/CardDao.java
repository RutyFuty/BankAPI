package ru.sberbank.dsmelnikov.bankapi.dao;

import ru.sberbank.dsmelnikov.bankapi.model.Card;

import java.util.List;

public interface CardDao {

    void createCard(Card card);

    void removeCard(Long id);

    void updateCard(Card card);

    Card getCardById(Long id);

    List<Card> listCards();
}
