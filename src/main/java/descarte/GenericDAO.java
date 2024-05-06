package descarte;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class GenericDAO<T, ID extends Serializable> {

    private final Class<T> persistentClass;
    private final Session session;

    protected GenericDAO(Class<T> persistentClass, Session session) {
        this.persistentClass = persistentClass;
        this.session = session;
    }

    public T getById(ID id) {
        return session.get(persistentClass, id);
    }

    public List<T> getAll() {
        return session.createQuery("FROM " + persistentClass.getName(), persistentClass).getResultList();
    }

    public void saveOrUpdate(T entity) {
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(entity);
        tx.commit();
    }

    public void delete(T entity) {
        Transaction tx = session.beginTransaction();
        session.delete(entity);
        tx.commit();
    }
}
