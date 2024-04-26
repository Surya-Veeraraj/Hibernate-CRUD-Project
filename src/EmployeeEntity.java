import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employeeentity")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empID")
	private int EmpID;

	@Column(name = "empName")
	private String EmpName;

	@Column(name = "empCity")
	private String EmpCity;

	// No-args constructor required for Hibernate
	public EmployeeEntity() {
	}

	// Constructor without empID parameter
	public EmployeeEntity(String empName, String empCity) {
		this.EmpName = empName;
		this.EmpCity = empCity;
	}

	// Getters and setters
	public int getEmpID() {
		return EmpID;
	}

	public void setEmpID(int empID) {
		EmpID = empID;
	}

	public String getEmpName() {
		return EmpName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public String getEmpCity() {
		return EmpCity;
	}

	public void setEmpCity(String empCity) {
		EmpCity = empCity;
	}

	@Override
	public String toString() {
		return "[EmpID=" + EmpID + ", EmpName=" + EmpName + ", EmpCity=" + EmpCity + "]";
	}
}
