package net.usrlib.parcel;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ParcelGen {
	private JSONObject mJsonObject = null;
	private Template mTemplate = null;

	public ParcelGen(final String inputJsonFilename) {
		try {
			mJsonObject = new JSONObject(FileUtil.readFile(inputJsonFilename));
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		mTemplate = new Template("src/main/resources/template.txt");

		try {
			mTemplate.buildFromJsonObject(mJsonObject);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		mTemplate.saveTemplateToPath("./");
	}

public ParcelGen() {
}
	public JSONObject getJsonObject() {
		return mJsonObject;
	}

	public static void main (String[] args) {
		for (String s: args) {
			System.out.println(s);
		}
	}
}
