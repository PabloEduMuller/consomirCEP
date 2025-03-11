package br.unipar.programacaoweb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class EnderecoDAO {

    private EntityManagerFactory emf;

    public EnderecoDAO() {
        emf = Persistence.createEntityManagerFactory("consomeCepPU");
    }

    public void salvar(Endereco endereco) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(endereco); // Usa merge para inserir ou atualizar
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Endereco buscarPorCep(String cep) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e WHERE e.cep = :cep", Endereco.class);
            query.setParameter("cep", cep);
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null se o CEP não for encontrado
        } finally {
            em.close();
        }
    }
}