
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mattafire
 */
public class Hibernate {
    public static void main(String[] arugs){
        UserDetails user = new UserDetails();
        user.setUserId(1);
        user.setUserName("First");
        
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        
        user = null;
        
        session = sessionFactory.openSession();
        session.beginTransaction();
        user = (UserDetails) session.get(UserDetails.class, 1);
        System.out.println("User Name retrieved is " + user.getUserName());
    }
}
