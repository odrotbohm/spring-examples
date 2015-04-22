/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.web;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import java.net.URI;
import java.time.LocalDateTime;

import lombok.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller exposing resources for orders.
 * 
 * @author Oliver Gierke
 */
@Controller
class OrderController {

	@RequestMapping("/orders/{id}")
	ResponseEntity<OrderResourceModel> showOrder(@PathVariable Long id) {

		// /customers/6

		URI uri = fromMethodCall(controller(CustomerController.class).customers(6L)).build().toUri();

		// URI uri = fromMethodCall(controller(CustomerController.class).customers(6L)).build().toUri();
		return new ResponseEntity<>(new OrderResourceModel(LocalDateTime.now(), uri), HttpStatus.OK);
	}

	/**
	 * Value object to build up a representation model for order resources.
	 * 
	 * @author Oliver Gierke
	 */
	@Value
	static class OrderResourceModel {

		private final LocalDateTime orderDate;
		private final URI customer;
	}
}
