package org.aubreynicoll.payroll;

class OrderNotFoundException extends RuntimeException {
	OrderNotFoundException(Long id) {
		super(String.format("Order %d not found", id));
	}
}
