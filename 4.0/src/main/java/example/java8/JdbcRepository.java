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
package example.java8;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * Sample repository implementation using a {@link JdbcTemplate} to showcase how Java 8 allows writing more concise code
 * with existing APIs.
 * 
 * @author Oliver Gierke
 */
class JdbcRepository {

	private final JdbcTemplate template;

	/**
	 * Creates a new {@link JdbcRepository} for the given {@link DataSource}.
	 * 
	 * @param template
	 */
	public JdbcRepository(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	/**
	 * Shows the usage of
	 * {@link JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, org.springframework.jdbc.core.RowCallbackHandler)}
	 * using Java 8 Lambda expressions in contrast to the traditional style.
	 */
	public void executeQueryWithPreparedStatementAndRowMapper() {

		template.query("SELECT name, age FROM person WHERE dep = ?", ps -> ps.setString(1, "Sales"),
				(rs, rowNum) -> new Person(rs.getString(1), rs.getString(2)));

		// VS.

		template.query("SELECT name, age FROM person WHERE dep = ?", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, "Sales");
			}
		}, new RowMapper<Person>() {
			@Override
			public Person mapRow(ResultSet rs, int i) throws SQLException {
				return new Person(rs.getString(1), rs.getString(2));
			}
		});
	}

	/**
	 * Showes the usage of {@link JdbcTemplate#query(org.springframework.jdbc.core.PreparedStatementCreator, RowMapper)}
	 * using a Java 8 method reference.
	 */
	public void executeQueryWithPreparedStatementAndMethodReference() {

		template.query("SELECT name, age FROM person WHERE dep = ?", ps -> ps.setString(1, "Sales"),
				JdbcRepository::mapPerson);
	}

	// Implicit RowMapper
	private static Person mapPerson(ResultSet rs, int rowNum) throws SQLException {
		return new Person(rs.getString(1), rs.getString(2));
	}

	static class Person {

		public Person(String firstname, String lastname) {

		}
	}
}
