/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagio.dao;

import estagio.model.Produto;
import estagio.view.util.JPAUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Pereira
 */
public class ProdutoDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public ProdutoDAO() 
    {
        em = new JPAUtil().getEntityManager();
    }
    
    @Transactional
    public void inserir(Produto produto) 
    {
        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {

            em.persist(produto);
            em.getTransaction().commit();

        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
        }
    }
    
    public void alterar(Produto produto) 
    {
        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {

            Produto aux = em.find(Produto.class,produto.getId());
            aux.setNome(produto.getNome());
            aux.setFornecedor(produto.getFornecedor());
            aux.setCategoria(produto.getCategoria());
            aux.setPreco(produto.getPreco());
            aux.setPreco_compra(produto.getPreco_compra());
            em.merge(aux);
            em.getTransaction().commit();

        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
        }
    }
    
    public boolean Deletar(Produto produto) {
        boolean deletado = false;

        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {

            Produto aux = em.find(Produto.class,produto.getId());
            em.remove(aux);
            em.getTransaction().commit();
            deletado = true;
        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
            return deletado;
        }
    }
    public Produto busca(String busca) {
        Produto produto = new Produto();

        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {

            produto = em.find(Produto.class,busca);
            em.getTransaction().commit();

        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
        }
        return produto;
    }
    
    public Produto listar(Long busca) {
        String jpql="";
        Produto produto=null;
        List<Produto> retorno = new ArrayList<Produto>();
        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {
            jpql = "select m from Produto m where m.id = :pId";  
            Query query = em.createQuery(jpql);
            query.setParameter("pId", busca);  
            retorno = query.getResultList();            
            produto = retorno.get(0);
            em.getTransaction().commit();

        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
        }
        return produto;
    }
    
    public List<Produto> listar(String busca) {
        String jpql="";
        List<Produto> retorno = new ArrayList<Produto>();
        if(!em.isOpen())
        {
            em = new JPAUtil().getEntityManager();
        }
        else
            em.getTransaction().begin();
        try
        {
            if(busca.compareTo("") == 0)
                jpql = "select m from Produto m order by m.nome";
            else
                jpql = "select m from Produto m where m.nome like :pBusca "
                        + "OR m.fornecedor.nome like :pBusca order by m.nome";  

            Query query = em.createQuery(jpql);
            if (busca.compareTo("")!=0) 
                query.setParameter("pBusca", "%"+busca+"%");  
            retorno = query.getResultList();
            em.getTransaction().commit();

        } 
        catch(Exception e)
        {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            em.close();
        }
        return retorno;
    }
   
}
