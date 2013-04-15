package com.AddressBook;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;

public class AddressBook {
	private JSONObject currentPos;    //point to current position of json;
	private JSONObject initPos;       // point to initial position of json, write back if any change happened
	private String filePath;
	private boolean modify = false;

	public AddressBook(JSONObject current, JSONObject init, boolean hasModify,
			String path) {
		currentPos = current;
		initPos = init;
		modify = hasModify;
		filePath = path;
	}

	public JSONObject getCurrentPos() {
		return currentPos;
	}

	public JSONObject getInitPOs() {
		return initPos;
	}

	public boolean getModify() {
		return modify;
	}

	public void start() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			Object obj = parser.parse(new FileReader(filePath));
			jsonObject = (JSONObject) obj;
			initPos = jsonObject;
			currentPos = jsonObject;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		while (true) {
			Scanner input = new Scanner(System.in);
			String[] array = input.nextLine().split(" ");
			String result = commandHandle(array);              //wait for user's command an pass it to commandHandle
			if (result.equals("quit"))
				return;
		}

	}

	public String commandHandle(String[] array) {

		if (array[0].equals("ls")) {
			Set<String> keySet = currentPos.keySet();
			for (String key : keySet) {
				System.out.println(key);
			}
		}
		if (array[0].equals("cat")) {
			if (array.length != 2) {
				System.out.println("cat need a key, operation Failed");            //cat must take to strings
				return "error cat";
			}
			System.out.println(array[1] + " : " + currentPos.get(array[1]));
		}
		if (array[0].equals("add")) {
			System.out.print("key: ");
			Scanner inputKey = new Scanner(System.in);
			String keyAdd = inputKey.next();
			if (currentPos.containsKey(keyAdd)) {
				System.out.println("already has the key named" + keyAdd                    //the key should be unique
						+ "add operation Failed");
				return "error add";
			}
			modify = true;
			System.out.print("value: ");
			Scanner inputValue = new Scanner(System.in);
			String valueAdd = inputValue.nextLine();
			currentPos.put(keyAdd, valueAdd);
		}
		if (array[0].equals("cd")) {
			if (array.length != 2) {
				System.out.println("cd need a key, operation Failed");
				return "error cd";
			}
			currentPos = (JSONObject) currentPos.get(array[1]);
		}
		if (array[0].equals("remove")) {
			System.out.print("please give the key: ");
			Scanner removeKey = new Scanner(System.in);
			String remove = removeKey.next();
			if (!currentPos.containsKey(remove)) {
				System.out.println("no key named" + remove
						+ "in current scale, remove operation Failed");
				return "error remove";
			}
			modify = true;
			currentPos.remove(remove);
			System.out.println(remove + " was deleted from JSON");
		}
		if (array[0].equals("!help")) {
			System.out.println("help information");
		}
		if (array[0].equals("!quit")) {
			return "quit";
		}
		if (modify) {                                                           //if the file has been modified, update it
			try {

				FileWriter file = new FileWriter(
						"test.json");
				file.write(initPos.toJSONString());
				file.flush();
				file.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}

}
