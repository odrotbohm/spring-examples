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

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = XmlBeamExample.class)
public class XmlBeamExampleTests {

	@Autowired WebApplicationContext context;

	MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void bindsXmlToProjectionInterface() throws Exception {

		for (String filename : Arrays.asList("customer.xml", "customer-nested.xml")) {

			mvc.perform(buildXmlPostRequestFor(filename)).//
					andExpect(status().isOk()).//
					andExpect(content().string(is("Dave Matthews")));
		}
	}

	@Test
	public void invokesBeanValidation() throws Exception {

		mvc.perform(buildXmlPostRequestFor("customer-lastname.xml")).//
				andExpect(status().isBadRequest());
	}

	private static MockHttpServletRequestBuilder buildXmlPostRequestFor(String filename) throws Exception {

		Path path = Paths.get(new ClassPathResource(filename, XmlBeamExampleTests.class).getURI());

		return post("/customers").//
				content(Files.readAllBytes(path)).//
				contentType(MediaType.APPLICATION_XML);
	}
}
