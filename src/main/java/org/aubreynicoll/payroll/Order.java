package org.aubreynicoll.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
class Order {
	@Id
	@GeneratedValue
	private Long id;
	private String description;
	private Status status;

	Order() {}

	Order(String description, Status status) {
		this.description = description;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Order))
			return false;
		Order o = (Order) other;
		return id == o.id && description == o.description && status == o.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, status);
	}

	@Override
	public String toString() {
		return "Order [description=" + description + ", id=" + id + ", status=" + status + "]";
	}



}
