package ee.ttu.t061879.EPOOD2.data;

public class Employee {
	int employee;
	String firstName, lastName;

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
