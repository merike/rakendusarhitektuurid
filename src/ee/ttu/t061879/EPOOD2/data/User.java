package ee.ttu.t061879.EPOOD2.data;

public class User {
	private String userName;
	private String firstName, lastName;
	private String empRole, currentStructUnit;
	
	public String getEmpRole() {
		return empRole;
	}
	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}
	public String getCurrentStructUnit() {
		return currentStructUnit;
	}
	public void setCurrentStructUnit(String currentStructUnit) {
		this.currentStructUnit = currentStructUnit;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
