package com.example.flashcards.database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ExternFileIO {
	public static String[] readStdFile(String path) {
		int i;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			FileInputStream fIn = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fIn);

			i = isr.read();
			while (i != -1) {

				byteArrayOutputStream.write(i);
				i = isr.read();
			}
			isr.close();
		} catch (Throwable e) {
			// return new String[]{"Error 1",""};
		}
		String fileText = byteArrayOutputStream.toString();
		try {

			if (fileText.contains("FileStyle: {|}")
					|| fileText.contains("FileStyle:{|}")) {
				return encryptStyle(fileText, "{|}");
			} else if (fileText.contains("FileStyle: @")
					|| fileText.contains("FileStyle:@")) {
				return encryptStyle(fileText, "@");
			} else if (fileText.contains("FileStyle:\n")) {
				return encryptStyle(fileText, "\n");
			} else {
				return null;
			}

		} catch (Exception e) {
			return new String[] { "Error 3", "" };
		}
	}

	/**
	 * Tested It works
	 * 
	 * @param fileText
	 *            Text from file
	 * @param style
	 *            style to encrypt
	 * @return String-array Form of the inputfile (fileText)
	 */
	private static String[] encryptStyle(String fileText, String style) {

		ArrayList<String> data = new ArrayList<String>();

		int i = fileText.indexOf(style);

		if (i == -1) {
			return new String[0];
		}

		fileText = fileText.substring(i + style.length());

		i = fileText.indexOf(style);

		do {
			try {
				data.add(fileText.substring(0, i));

			} catch (Exception e) {
				return new String[0];
			}

			fileText = fileText.substring(i + style.length());

			i = fileText.indexOf(style);
		} while (i != -1);

		return data.toArray(new String[data.size()]);

		/*
		 * String rueck[]=new String[data.size()]; for (int j =0;
		 * j<rueck.length;j++){ rueck[j]=data.get(j); } return rueck;
		 */
	}

	/**
	 * 
	 * @param path
	 *            means the path of the Library
	 * @param data
	 *            DAta to write in as String[]
	 * @param encryptType
	 *            Encrypttype as "{|}" or "@"
	 */
	public static void writeStdPath(String path, String[] data,
			String encryptType) {
		File createfile = new File(path);
		try {
			FileOutputStream fOut = new FileOutputStream(createfile);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write("FileStyle: " + encryptType);
			for (int i = 0; i < data.length; i++) {
				osw.write(data[i] + encryptType);
				osw.flush();
			}
			osw.close();
		} catch (Exception e) {

		}

	}
}
