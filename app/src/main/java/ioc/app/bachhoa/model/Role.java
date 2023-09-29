package ioc.app.bachhoa.model;

import java.util.List;




public class Role {

	private String roleID;

	private String workRole;

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getWorkRole() {
		return workRole;
	}

	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}

//    @OneToMany(mappedBy = "role")
//    private List<Employee> employees;
//
//    @OneToMany(mappedBy = "role")
//    private List<RoleBasedSalary> roleBasedSalaries;

}
