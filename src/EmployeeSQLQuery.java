
public class EmployeeSQLQuery {
//	JPQL - Java Persistence Query Language
//	JPQL provides a simple and straightforward way to get all entities from a table.
//	Simplicity is the advantage of JPQL. JPQL is very close to SQL, and is therfore easier to write and understand
	public String findAll() {
		StringBuilder sqlQueryString = new StringBuilder();
		sqlQueryString.append("SELECT e FROM EmployeeEntity e ORDER BY EmpID ASC");
		return sqlQueryString.toString();
	}
}
