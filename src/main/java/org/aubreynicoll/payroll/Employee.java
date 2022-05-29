package org.aubreynicoll.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Employee {
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String role;

	Employee() {}

	Employee(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return String.format("%s %s", firstName, lastName);
	}

	public void setName(String name) {
		String[] nameParts = name.split(" ");
		firstName = nameParts[0];
		lastName = nameParts[1];
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee e = (Employee) o;
		return id == e.id && firstName == e.firstName && lastName == e.lastName && role == e.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, role);
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", role="
				+ role + "]";
	}

}
