package net.usrlib.parcel;

import org.junit.Test;

import junit.framework.TestCase;

public class ParcelGenTest extends TestCase {
	//private String mTestResourceFilename = "src/test/resources/json_input.json";

	@Test
	public void testInputFilenameMissing() {
		boolean success = true;
		try {
			ParcelGen.main(new String[] {ParcelGen.CMD_INPUT_FILE});
		} catch (InvalidInputException e) {
			success = false;
		}

		assertTrue(
				"Should fail when input filename is not specified",
				success == false
		);
	}

	@Test
	public void testOutputFilenameMissing() {
		boolean success = true;
		try {
			ParcelGen.main(new String[] {ParcelGen.CMD_OUTPUT_FILE});
		} catch (InvalidInputException e) {
			success = false;
		}

		assertTrue(
				"Should fail when output filename is not specified",
				success == false
		);
	}
}
