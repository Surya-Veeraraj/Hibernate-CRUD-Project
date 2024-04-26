import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class EmployeeDAO {
    private final SessionFactory sessionFactory;

    public EmployeeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ReturnStatus create(EmployeeEntity saveEmployeeEntity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (!session.contains(saveEmployeeEntity)) {
                saveEmployeeEntity = (EmployeeEntity) session.merge(saveEmployeeEntity);
            }
            session.persist(saveEmployeeEntity);
            transaction.commit();
            return new ReturnStatus(true, saveEmployeeEntity);
        } catch (Exception e) {
            handleTransactionException(e, transaction);
            return new ReturnStatus(false, "Failed to create employee: " + e.getMessage());
        }
    }

    public ReturnStatus read() {
        try (Session session = sessionFactory.openSession()) {
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class);
            List<EmployeeEntity> employees = query.list();
            return new ReturnStatus(true, employees);
        } catch (Exception e) {
            handleTransactionException(e);
            return new ReturnStatus(false, e.getMessage());
        }
    }

    public ReturnStatus update(EmployeeEntity updateEmployeeEntity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(updateEmployeeEntity);
            transaction.commit();
            return new ReturnStatus(true, updateEmployeeEntity);
        } catch (Exception e) {
            handleTransactionException(e, transaction);
            return new ReturnStatus(false, e.getMessage());
        }
    }

    public ReturnStatus delete(int empID) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            EmployeeEntity employeeToDelete = session.get(EmployeeEntity.class, empID);
            if (employeeToDelete == null) {
                return new ReturnStatus(false, "Employee with ID " + empID + " does not exist.");
            }
            String deletedEmployeeDetails = employeeToDelete.toString();
            session.delete(employeeToDelete);
            transaction.commit();
            return new ReturnStatus(true, "Employee deleted successfully: " + deletedEmployeeDetails);
        } catch (Exception e) {
            handleTransactionException(e, transaction);
            return new ReturnStatus(false, e.getMessage());
        }
    }

    public ReturnStatus count() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            Long count = (Long) session.createQuery("SELECT COUNT(*) FROM EmployeeEntity").uniqueResult();
            return new ReturnStatus(true, count.intValue());
        } catch (Exception e) {
            handleTransactionException(e, transaction);
            return new ReturnStatus(false, e.getMessage());
        }
    }

    // Method to handle transaction exceptions and rollback the transaction
    private void handleTransactionException(Exception e, Transaction transaction) {
        System.out.println("An error occurred during transaction: " + e.getMessage());
        e.printStackTrace();
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    private void handleTransactionException(Exception e) {
        handleTransactionException(e, null);
    }
}
