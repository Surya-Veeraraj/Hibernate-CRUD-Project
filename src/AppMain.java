import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AppMain {
	public static void main(String[] args) throws ClassNotFoundException {

		HibernateUtil hibernateUtil = new HibernateUtil();
		System.out.println("Hibernate Configuration Completed");
		System.out.println("Session Factory Creation Completed");

		try (SessionFactory sessionFactory = hibernateUtil.getSessionFactory()) {
			EmployeeDAO employeeDAO = new EmployeeDAO(sessionFactory);
			// Start a new session
			Session session = sessionFactory.openSession();
			// Begin a new transaction
			Transaction transaction = session.beginTransaction();
			// Create a new employee
			EmployeeEntity employee1 = new EmployeeEntity("Surya", "Kovilpatti");
			try {
				// Add the employee
				ReturnStatus createStatus = employeeDAO.create(employee1);
				if (createStatus.isReturnStatus()) {
					System.out.println("Employee created successfully.");
				} else {
					System.out.println("Failed to create employee: " + createStatus.getReturnObject());
					return;
				}
				// Get the count of employees after adding
				ReturnStatus countStatus = employeeDAO.count();
				if (countStatus.isReturnStatus()) {
					int finalCount = (int) countStatus.getReturnObject();
					System.out.println("Total number of employees: " + finalCount);
				} else {
					System.out.println("Failed to count employees: " + countStatus.getReturnObject());
					return;
				}
			} catch (Exception e) {
				handleException(e);
			}
			// Read and print all employees
			try {
				ReturnStatus readStatus = employeeDAO.read();
				if (readStatus.isReturnStatus()) {
					List<EmployeeEntity> employees = (List<EmployeeEntity>) readStatus.getReturnObject();
					System.out.println("Selected Employee Details are as follows:");
					for (EmployeeEntity employee : employees) {
						printEmployee(employee);
					}
				} else {
					System.out.println("Failed to read employees: " + readStatus.getReturnObject());
					return;
				}
			} catch (Exception e) {
				handleException(e);
			}
			// Update an employee
			try {
				employee1.setEmpName("Ajith");
				employee1.setEmpCity("Gova");
				ReturnStatus updateStatus = employeeDAO.update(employee1);
				if (updateStatus.isReturnStatus()) {
					System.out.println("Updated Employee Details are as follows:");
					printEmployee(employee1);
				} else {
					System.out.println("Failed to update employee: " + updateStatus.getReturnObject());
					return;
				}
			} catch (Exception e) {
				handleException(e);
			}

			// Delete an employee
			try {
				ReturnStatus deleteStatus = employeeDAO.delete(employee1.getEmpID());
				System.out.println(deleteStatus.getReturnObject());
			} catch (Exception e) {
				handleException(e);
			}

			// Commit the transaction
			transaction.commit();
		} catch (Exception e) {
			handleException(e);
		}
	}
	private static void handleException(Exception e) {
		e.printStackTrace();
	}

	private static void handleException(String errorMessage) {
		System.out.println("An error occurred: " + errorMessage);
	}

	private static void printEmployee(EmployeeEntity employeeEntity) {
		System.out.print("Employee ID: " + employeeEntity.getEmpID() + " ");
		System.out.print("Employee Name: " + employeeEntity.getEmpName() + " ");
		System.out.print("Employee City: " + employeeEntity.getEmpCity() + " ");
		System.out.println();
	}
}
