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

import java.util.HashMap;
import java.util.Map;

public class CommandLineOptions {
	private Map<String, String> mOptionsMap = new HashMap<String, String>();
	private String[] mOptionsList;

	public CommandLineOptions(String[] options) {
		mOptionsList = options;
	}

	public final void setArguments(String[] cmdLineArgs) throws InvalidInputException {
		for (int argsIndex = 0; argsIndex < cmdLineArgs.length; argsIndex++) {
			final String argument = cmdLineArgs[argsIndex];

			for (int optionsIndex = 0; optionsIndex < mOptionsList.length; optionsIndex++) {
				if (argument.contains(mOptionsList[optionsIndex])) {
					int nextArgsItemIndex = argsIndex + 1;

					if (nextArgsItemIndex < cmdLineArgs.length) {
						String nextArgsItemValue = cmdLineArgs[nextArgsItemIndex];

						if (nextArgsItemValue.startsWith("-")) {
							throw InvalidInputException.errorInvalidFilename(argument);
						}

						mOptionsMap.put(argument, nextArgsItemValue);
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
