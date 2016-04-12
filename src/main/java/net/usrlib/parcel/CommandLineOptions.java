package net.usrlib.parcel;

import java.util.HashMap;
import java.util.Map;

public class CommandLineOptions {
	private Map<String, String> mOptionsMap = new HashMap<String,String>();
	private String[] mOptionsList;

	public CommandLineOptions(String[] options) {
		mOptionsList = options;
	}

	public final void setArguments(String[] cmdLineArgs) {
		for (int argsIndex = 0; argsIndex < cmdLineArgs.length; argsIndex++) {
			final String argument = cmdLineArgs[argsIndex];

			for (int optionsIndex = 0; optionsIndex < mOptionsList.length; optionsIndex++) {
				if (argument.contains(mOptionsList[optionsIndex])) {
					int nextArgsItem = argsIndex + 1;

					if (nextArgsItem < cmdLineArgs.length) {
						mOptionsMap.put(argument, cmdLineArgs[nextArgsItem]);
					}
				}
			}
		}
	}

	public final void addOption(final String option) {
		mOptionsList[mOptionsList.length] = option;
	}

	public final String getValueForOption(final String option) {
		//System.out.println("[CMD] getValueForOption " + option);
		return (mOptionsMap.containsKey(option) ? mOptionsMap.get(option) : null);
	}
}
