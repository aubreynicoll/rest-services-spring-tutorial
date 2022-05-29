package org.aubreynicoll.payroll;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OrderController {
	private final OrderRepository repository;
	private final OrderModelAssembler assembler;

	OrderController(OrderRepository repository, OrderModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/orders")
	CollectionModel<EntityModel<Order>> all() {
		List<EntityModel<Order>> orders =
				repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}


	@PostMapping("/orders")
	ResponseEntity<EntityModel<Order>> create(@RequestBody Order newOrder) {
		EntityModel<Order> entityModel = assembler.toModel(repository.save(newOrder));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@GetMapping("/orders/{id}")
	ResponseEntity<EntityModel<Order>> one(@PathVariable Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		EntityModel<Order> entityModel = assembler.toModel(order);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@PatchMapping("/orders/{id}/cancel")
	ResponseEntity<?> cancel(@PathVariable Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(repository.save(order)));
		}

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Method not allowed").withDetail(
						String.format("Order status is %s, cannot cancel order", order.getStatus())));
	}

	@PatchMapping("/orders/{id}/complete")
	ResponseEntity<?> complete(@PathVariable Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(repository.save(order)));
		}

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Method not allowed").withDetail(
						String.format("Order status is %s, cannot complete order", order.getStatus())));
	}


}
