/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.filtros;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Eduardo
 */
@WebFilter ( servletNames ={"Faces Servlet"})
public class JPAFiltro implements Filter {
    
    private EntityManagerFactory factory;
    
   
    public void init ( FilterConfig filterConfig ) throws ServletException
    {                                                               
        this.factory = Persistence.createEntityManagerFactory("Web-Repositorio");
    }
    
   
    public void destroy()
    {
        this.factory.close();
    }
    
    public void doFilter ( ServletRequest request , ServletResponse response ,
            FilterChain chain ) throws IOException , ServletException 
    {
        //Chegada
        EntityManager manager = this.factory.createEntityManager();
        request.setAttribute("EntityManager",manager);
        manager.getTransaction().begin();
        
        // Chegada
        
        // Faces Servlet
        chain.doFilter(request, response);
        // Faces Servlet
        
        // Saida
        try
        {
            manager.getTransaction().commit();
        }
        catch ( Exception e)
        {
            manager.getTransaction().rollback();
        }
        finally
        {
            manager.close();
        }
        
        // Saida
                        
    }
            
}
