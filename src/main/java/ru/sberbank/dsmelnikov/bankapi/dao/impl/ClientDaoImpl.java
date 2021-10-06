package ru.sberbank.dsmelnikov.bankapi.dao.impl;

import org.springframework.stereotype.Repository;
import ru.sberbank.dsmelnikov.bankapi.dao.ClientDao;
import ru.sberbank.dsmelnikov.bankapi.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createClient(Client client) {
        entityManager.persist(client);
    }

    @Override
    public void removeClient(Long id) {
        Client client = entityManager.find(Client.class, id);

        if (client != null) {
            entityManager.remove(client);
        }
    }

    @Override
    public void updateClient(Client client) {
        entityManager.merge(client);
    }

    @Override
    public Client getClientById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public List<Client> listClients() {
        return entityManager.createQuery("from Client", Client.class).getResultList();
    }
}
