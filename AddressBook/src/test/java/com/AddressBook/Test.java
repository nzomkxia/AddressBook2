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
public class Test
{
    private static String path2 = "test.json";
    private static JSONParser parser = new JSONParser();
    private static JSONObject jsonObject = null;
    private static JSONObject init = null;
    private static JSONObject current = null;
    private static AddressBook testCase = null;

    public Test()
    {
    	try {
            Object obj = parser.parse(new FileReader(path2));
            jsonObject = (JSONObject) obj;
            JSONObject initPos = jsonObject;
            JSONObject currentPos = jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    	testCase = new AddressBook(init, current, false, path2);
    }


    public static void main(String [] args)
    {
        String [] commandArr = {"cat"};
        String result = "error cat";
        if(!result.equals(testCase.commandHandle(commandArr)))
        {
            System.out.println("unexpection result in commandHandle in case1");
        }
        String [] commandArr2 = {"cd"};
        String result2 = "error cd";
        if(! result2.equals(testCase.commandHandle(commandArr2)))
        {
            System.out.println("unexpection result in commandHandle case2");
        }
        current = (JSONObject) current.get("entries");
        String [] commandArr3 = {"cat", "entries"};
        if(! current.equals(testCase.commandHandle(commandArr3)))
        {
            System.out.println("unexpection result in commandHandle in case3");
        }
    }

}
