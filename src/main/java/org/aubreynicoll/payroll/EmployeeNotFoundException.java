package org.aubreynicoll.payroll;

class EmployeeNotFoundException extends RuntimeException {
	EmployeeNotFoundException(Long id) {
		super(String.format("Employee %d not found", id));
	}

}
