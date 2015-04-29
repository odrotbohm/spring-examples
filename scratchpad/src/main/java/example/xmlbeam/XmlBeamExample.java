/*
 * Copyright 2015 the original author or authors.
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
package example.xmlbeam;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlbeam.annotation.XBRead;

/**
 * Sample application to show the usage of an XMLBeam projection interface within a Spring MVC controller.
 * 
 * @author Oliver Gierke
 */
@SpringBootApplication
public class XmlBeamExample {

	@RestController
	public static class SampleController {

		@RequestMapping(value = "/customers", method = RequestMethod.POST)
		public String handleCustomer(@Valid @RequestBody Customer customer) {
			return String.format("%s %s", customer.getFirstname(), customer.getLastname());
		}
	}

	/**
	 * XMLBeam projection interface. Allows more lenient binding of XML documents compared to a mapping approach like
	 * JAXB. Also note the usage of Bean Validation annotations on the projection proxy that can be used to validate the
	 * incoming data.
	 *
	 * @author Oliver Gierke
	 */
	public interface Customer {

		@NotEmpty
		@XBRead("//firstname")
		String getFirstname();

		@XBRead("//lastname")
		String getLastname();
	}
}
