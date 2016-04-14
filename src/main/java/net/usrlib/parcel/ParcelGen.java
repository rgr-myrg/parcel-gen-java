/*
 * Copyright 2016. UsrLib.Net
 *
 * Licensed under the Apache License,  Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *@author https://github.com/rgr-myrg
 */
package net.usrlib.parcel;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ParcelGen {
	public static final String CMD_INPUT_FILE = "-i";
	public static final String CMD_OUTPUT_FILE = "-o";
	public static final String TEMPLATE_FILENAME = "src/main/resources/template.txt";

	public static void main (String[] args) throws InvalidInputException {
		final CommandLineOptions options = new CommandLineOptions(new String[] {CMD_INPUT_FILE, CMD_OUTPUT_FILE});
		options.setArguments(args);

		String inputFilename = options.getValueForOption(CMD_INPUT_FILE);
		String outputFilename = options.getValueForOption(CMD_OUTPUT_FILE);

		if (inputFilename == null ) {
			throw InvalidInputException.errorMissingOptionValue(CMD_INPUT_FILE);
		}

		if (outputFilename == null) {
			throw InvalidInputException.errorMissingOptionValue(CMD_OUTPUT_FILE);
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

		final Template template = new Template(TEMPLATE_FILENAME);
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
