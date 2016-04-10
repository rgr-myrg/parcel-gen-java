package net.usrlib.parcel;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Template {
	private static final String EOL = System.lineSeparator();
	private static final String TAB = "\t";
	private static final String SPC = " ";
	private static final String CMA = ",";
	private static final String EQL = " = ";
	private static final String SCL = ";" + EOL;

	private static final String DOUBLE_INDENT = "\t\t";

	private String mTemplateContents = null;
	private String mNewFileContents = null;
	private String mClassname = null;

	private String mStaticConstants = "";
	private String mInstanceVariables = "";
	private String mInstanceVariableGetters = "";
	private String mConstructorSignature = "";
	private String mConstructorFields = "";
	private String mParcelFields = "";
	private String mWriteToParcelFields = "";
	private String mContentValues = "";

	private JSONArray mPropertiesJsonArray = null;

	public Template(String filename) {
		try {
			mTemplateContents = FileUtil.readFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String buildFromJsonObject(final JSONObject jsonObject) throws InvalidInputException {
		if (jsonObject.isNull(KeyName.PACKAGE)) {
			throw InvalidInputException.errorMissingProperty(KeyName.PACKAGE);
		}

		if (jsonObject.isNull(KeyName.CLASSNAME)) {
			throw InvalidInputException.errorMissingProperty(KeyName.CLASSNAME);
		}

		if (jsonObject.isNull(KeyName.PROPERTIES)) {
			throw InvalidInputException.errorMissingProperty(KeyName.PROPERTIES);
		}

		mClassname = jsonObject.getString(KeyName.CLASSNAME);
		mPropertiesJsonArray = jsonObject.getJSONArray(KeyName.PROPERTIES);

		buildClassProperties();

		mNewFileContents = mTemplateContents
				.replaceAll(
						buildRegexToken(KeyName.PACKAGE),
						jsonObject.getString(KeyName.PACKAGE)
				)
				.replaceAll(
						buildRegexToken(KeyName.CLASSNAME),
						mClassname
				)
				.replaceAll(
						buildRegexToken(KeyName.STATIC_CONSTANTS),
						mStaticConstants
				)
				.replaceAll(
						buildRegexToken(KeyName.INSTANCE_VARIABLES),
						mInstanceVariables
				)
				.replaceAll(
						buildRegexToken(KeyName.INSTANCE_VARIABLE_GETTERS),
						mInstanceVariableGetters
				)
				.replaceAll(
						buildRegexToken(KeyName.CONSTRUCTOR_SIGNATURE),
						mConstructorSignature
				)
				.replaceAll(
						buildRegexToken(KeyName.CONSTRUCTOR_FIELDS),
						mConstructorFields
				)
				.replaceAll(
						buildRegexToken(KeyName.PROPERTIES_SIZE),
						String.valueOf(mPropertiesJsonArray.length())
				)
				.replaceAll(
						buildRegexToken(KeyName.PARCEL_FIELDS),
						mParcelFields
				)
				.replaceAll(
						buildRegexToken(KeyName.WRITE_TO_PARCEL_FIELDS),
						mWriteToParcelFields
				)
				.replaceAll(
						buildRegexToken(KeyName.CONTENT_VALUES),
						mContentValues
				);

		return mNewFileContents;
	}

	public void saveTemplateToPath(final String path) {
		try {
			FileUtil.writeToFile(mNewFileContents, path + "/" + mClassname + KeyName.FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildClassProperties() {
		for (int index = 0; index < mPropertiesJsonArray.length(); index++) {
			JSONObject item = mPropertiesJsonArray.getJSONObject(index);
			if (item.isNull(KeyName.PROPERTY_NAME) || item.isNull(KeyName.PROPERTY_TYPE)) {
				continue;
			}

			String itemName = item.getString(KeyName.PROPERTY_NAME);
			String itemType = item.getString(KeyName.PROPERTY_TYPE);

			// Compose static constants. Ex: public static final String ID = "id"
			mStaticConstants += TAB + buildStaticConstant(itemName);

			// Compose instance variables. Ex: private int id;
			mInstanceVariables += TAB + "private " + itemType + SPC + itemName + SCL;

			// Compose constructor signature. Ex: public class MyClass(int id) 
			mConstructorSignature += EOL + DOUBLE_INDENT + itemType + SPC + itemName + CMA;

			// Compose constructor fields. Ex: this.id = id;
			mConstructorFields += DOUBLE_INDENT + "this." + itemName + EQL + itemName + SCL;

			// Compose parcel fields. Ex: this.id = Integer.valueOf(data[0]);
			mParcelFields += DOUBLE_INDENT + "this." + itemName + EQL + buildValueOfParcel(itemType, index) + SCL;

			// Compose writeToParcel fileds. Ex: String.valueOf(this.id)
			mWriteToParcelFields += DOUBLE_INDENT + TAB + buildStringValueOf(itemName, itemType) + CMA + EOL;

			// Compose getters. Ex: public final int getId()
			mInstanceVariableGetters += TAB + buildGetter(itemName, itemType) + EOL;

			// Compose content values. Ex: values.put(ID, id);
			mContentValues += DOUBLE_INDENT + buildContentValue(itemName);
		}

		// Trim trailing commas and newlines
		mStaticConstants = StringUtil.trimEndOfLine(mStaticConstants);
		mInstanceVariables = StringUtil.trimEndOfLine(mInstanceVariables);
		mConstructorSignature = StringUtil.trimLast(mConstructorSignature, ",");
		mConstructorFields = StringUtil.trimEndOfLine(mConstructorFields);
		mParcelFields = StringUtil.trimEndOfLine(mParcelFields);
		mWriteToParcelFields = StringUtil.trimLast(mWriteToParcelFields, "," + EOL);
		mInstanceVariableGetters = StringUtil.trimEndOfLine(mInstanceVariableGetters);
		mContentValues = StringUtil.trimEndOfLine(mContentValues);
	}

	private String buildStaticConstant(final String name) {
		return "public static final String " 
				+ name.toUpperCase() + EQL 
				+ '"' + name + '"' + SCL;
	}

	private String buildValueOfParcel(final String type, final int index) {
		// Example: String.valueOf(data[0]);
		final String dataPoint = "data[" + String.valueOf(index) + "]";
	
		return !type.contentEquals("String") ? "String.valueOf(" + dataPoint + ")" : dataPoint;
	}

	private String buildStringValueOf(final String name, final String type) {
		String valueOf = "this." + name;

		if (!type.contentEquals("String")) {
			valueOf = "String.valueOf(" + valueOf + ")";
		}

		return valueOf;
	}

	private String buildGetter(final String name, final String type) {
		return "public final " + type 
				+ " get" + StringUtil.firstToUppercase(name) + "() {" + EOL
				+ DOUBLE_INDENT + "return " + name + SCL 
				+ TAB + "}" + EOL;
	}

	private String buildContentValue(final String name) {
		return "values.put(" + name.toUpperCase() +", " + name + ")" + SCL;
	}

	private String buildRegexToken(final String key) {
		return "\\{" + key + "\\}";
	}
}
