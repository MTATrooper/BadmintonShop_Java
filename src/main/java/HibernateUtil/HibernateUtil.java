/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateUtil;
 
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 public class HibernateUtil {
        //Annotation based configuration
    private static SessionFactory sessionFactory;
        private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            //"/hibernate.cfg.xml"
            configuration.addAnnotatedClass(com.shop.models.Product.class);
            configuration.addAnnotatedClass(com.shop.models.Producer.class);
            configuration.addAnnotatedClass(com.shop.models.Category.class);
            configuration.addAnnotatedClass(com.shop.models.Account.class);
            configuration.addAnnotatedClass(com.shop.models.Bill.class);
            configuration.addAnnotatedClass(com.shop.models.BillDetail.class);
            configuration.addAnnotatedClass(com.shop.models.News.class);
            configuration.addAnnotatedClass(com.shop.models.Price.class);
            configuration.addAnnotatedClass(com.shop.models.Comment.class);
            configuration.addAnnotatedClass(com.shop.models.Statistic.class);
            configuration.addAnnotatedClass(com.shop.models.ExportProduct.class);
            configuration.addAnnotatedClass(com.shop.models.ImportProduct.class);
            configuration.configure();
            System.out.println("Hibernate Annotation Configuration loaded");
             
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");
             
             sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
    
    public static void shutdown() {
    	// Close caches and connection pools
       sessionFactory.close();
    }
     
    
     
}
