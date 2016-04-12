package net.usrlib.parcel;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ParcelGen {
	public static final String CMD_INPUT_FILE = "-i";
	public static final String CMD_OUTPUT_FILE = "-o";

	public static void main (String[] args) throws InvalidInputException {
		final CommandLineOptions options = new CommandLineOptions(new String[] {CMD_INPUT_FILE, CMD_OUTPUT_FILE});
		options.setArguments(args);

		String inputFilename = options.getValueForOption(CMD_INPUT_FILE);
		String outputFilename = options.getValueForOption(CMD_OUTPUT_FILE);

		if (inputFilename == null || outputFilename == null) {
			throw InvalidInputException.errorMissingOptionValue(CMD_INPUT_FILE);
		}

		String fileContents = null;

		try {
			fileContents = FileUtil.readFile(inputFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fileContents == null) {
			throw new InvalidInputException("Unable to read file '" + inputFilename + "'");
		}
	
		JSONObject json = null;

		try {
			json = new JSONObject(fileContents);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (json == null) {
			throw new InvalidInputException("Unable to parse json file. Ensure input file contains valid json");
		}

		final Template template = new Template("src/main/resources/template.txt");
		boolean hasTemplate = false;

		try {
			template.buildFromJsonObject(json);
			hasTemplate = true;
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		if (!hasTemplate) {
			throw new InvalidInputException("Unable to build template.");
		}

		boolean isTemplateSaved = false;

		try {
			template.saveTemplateToPath(outputFilename);
			isTemplateSaved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!isTemplateSaved) {
			throw new InvalidInputException("Unable to save template to '" + outputFilename + "'");
		}
	}
}
