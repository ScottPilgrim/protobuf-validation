package no.entur.protobuf.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/*-
 * #%L
 * Protobuf validator
 * %%
 * Copyright (C) 2017 - 2018 Original authors
 * %%
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
 * #L%
 */

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import no.entur.protobuf.validation.MessageValidationException;
import no.entur.protobuf.validation.ProtobufValidator;
import validation.RepeatMaxMin;

public class RepeatMaxMinTest {

	private ProtobufValidator validator = new ProtobufValidator();

	@Test
	public void testRepeatInside() {
		RepeatMaxMin b = RepeatMaxMin.newBuilder().addHobbies("Football").addHobbies("Golf").addHobbies("Swimming").build();

		Executable e = () -> validator.validate(b);
		assertDoesNotThrow(e);
	}

	@Test
	public void testRepeatTooHigh() {
		RepeatMaxMin b = RepeatMaxMin.newBuilder().addHobbies("Football").addHobbies("Golf").addHobbies("Swimming").addHobbies("Skiing").addHobbies("Riding")
				.addHobbies("Flying").build();
		Executable e = () -> validator.validate(b);
		assertThrows(MessageValidationException.class, e);

	}

	@Test
	public void testRepeatTooLow() {
		RepeatMaxMin b = RepeatMaxMin.newBuilder().addHobbies("Football").build();
		Executable e = () -> validator.validate(b);
		assertThrows(MessageValidationException.class, e);

	}

	@Test
	public void testRepeatMaxHigh() {
		RepeatMaxMin b = RepeatMaxMin.newBuilder().addHobbies("Football").addHobbies("Swimming").addHobbies("Skiing").addHobbies("Riding")
				.addHobbies("Flying").build();
		Executable e = () -> validator.validate(b);
		assertDoesNotThrow(e);

	}

	@Test
	public void testRepeatMinLow() {
		RepeatMaxMin b = RepeatMaxMin.newBuilder().addHobbies("Football").addHobbies("Yoga").build();
		Executable e = () -> validator.validate(b);
		assertDoesNotThrow(e);

	}
}
