package ru.sberbank.dsmelnikov.bankapi.dao;

import ru.sberbank.dsmelnikov.bankapi.model.Client;

import java.util.List;

public interface ClientDao {

    void createClient(Client client);

    void removeClient(Long id);

    void updateClient(Client client);

    Client getClientById(Long id);

    List<Client> listClients();
}
