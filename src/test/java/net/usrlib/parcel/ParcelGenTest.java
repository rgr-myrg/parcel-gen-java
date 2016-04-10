package net.usrlib.pdto;

import org.json.JSONObject;
import org.junit.Test;

import junit.framework.TestCase;
import net.usrlib.parcel.KeyName;
import net.usrlib.parcel.ParcelGen;

public class ParcelGenTest extends TestCase {
	private String mTestResourceFilename = "src/test/resources/json_input.json";
	private JSONObject mJsonObject;

	@Test
	public void testParserConstructor() {
		final ParcelGen pg = new ParcelGen(mTestResourceFilename);
		mJsonObject = pg.getJsonObject();

		assertTrue(
				"ParcelGen Constructor should create a Json Object from a String",
				mJsonObject != null
		);
		
	}

	@Test
	public void testReturnValue() {
		final ParcelGen pg = new ParcelGen(mTestResourceFilename);
		mJsonObject = pg.getJsonObject();

		assertTrue(
				"optString should return value specified in the json input string",
				"net.usrlib.test".contentEquals(mJsonObject.optString(KeyName.PACKAGE, "package not found"))
		);
	}
}
