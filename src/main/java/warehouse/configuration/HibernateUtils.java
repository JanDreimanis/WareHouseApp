package warehouse.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import warehouse.entities.Customer;
import warehouse.entities.Order;
import warehouse.entities.Product;
import warehouse.entities.Supplier;

import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class HibernateUtils {
    private static final List<Class<?>> ENTITY_CLASSES = asList(Customer.class, Product.class, Supplier.class, Order.class);

    private static SessionFactory sessionFactory;

    public static Session currentSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        //this is not thread safe
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            Configuration configuration = createConfiguration();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unable to create session factory", e);
        }
    }

    public static void runInTransaction(Consumer<Session> consumer) {
        runInTransaction(session -> {
            consumer.accept(session);
            return null;
        });
    }

    public static <T> T runInTransaction(Function<Session, T> consumer) {
        Transaction transaction = null;
        try {
            Session session = currentSession();
            transaction = session.beginTransaction();

            T result = consumer.apply(session);

            transaction.commit();
            return result;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        }
    }

    // Close caches and connection pools
    public static void close() {
        getSessionFactory().close();
    }

    private static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperties(createProperties());

        ENTITY_CLASSES.forEach(configuration::addAnnotatedClass);
        return configuration;
    }

    private static Properties createProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/warehouse?serverTimezone=UTC&useSSL=false");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "L13nux123");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.SHOW_SQL, "false");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        return properties;
    }
}
