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

	public static void main (String[] args) throws InvalidInputException {
		String command = "";

		// Ex: {-i}{input}{-o}{output}
		for (int i = 0; i < args.length; i++) {
			command += "{" + args[i] + "}";
		}

		if (!command.contains("-i")) {
			throw new InvalidInputException("Please specify -i option for input file.");
		} else if (!command.contains("-o")) {
			throw new InvalidInputException("Please specify -o option for output file.");
		}

		System.out.println(StringUtil.getOptionValue(command, "-o"));

		//		// Extracts {word}
//		// ([^\{][a-zA-Z_.\-\/]+[^\}])
//		
//		final String key = "-i";
//		Pattern pattern = Pattern.compile("(\\{-o\\}\\{)([\\w+\\/\\-.]+)[^\\}]");
//		pattern = Pattern.compile("(?<=\\{-o\\}\\{)([\\w+\\/\\-.]+)(?=\\})");
//
//		final Matcher matcher = pattern.matcher(command);
//
//		if (matcher.find()) {
//			System.out.println(matcher.group(0));
//			//.replaceAll("\\{-i\\}", "")
////			for (int i = 0; i < matcher.groupCount(); i++) {
////				System.out.println(i + "--->" + matcher.group(i));
////			}
//		}
	}
}
