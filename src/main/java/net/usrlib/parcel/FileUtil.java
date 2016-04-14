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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	public static final void writeToFile(final String contents, final String filename) throws IOException {
		final FileWriter writer = new FileWriter(new File(filename));

		try {
			writer.write(contents);
		} finally {
			writer.close();
		}
	}

	public static final String readFile(final String fileName) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileName));
		final StringBuilder builder = new StringBuilder();

		try {
			String line = reader.readLine();

			while (line != null) {
				builder.append(line);
				builder.append(System.lineSeparator());
				line = reader.readLine();
			}	
		} finally {
			reader.close();
		}

		return builder.toString();
	}
}
