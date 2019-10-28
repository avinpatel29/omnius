package com.omnius.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import com.omnius.commonutilities.GetConfig;
import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class API_Tests {


    public static void main(String[] args) throws IOException, ConfigurationException {
//        getStatuses();
//        documentDetailsByStatus("REOPENED");
        documentDetailsByFilename("24.pdf");
    }


    /**
     * Method to get all the items from the JSON response
     *
     * @return JSON Array
     * @throws IOException
     * @throws ConfigurationException
     */
    public static JSONArray getItems() throws IOException, ConfigurationException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Object> list = new ArrayList<>();
        Map<String, Integer> statuses = new HashMap<String, Integer>();
        Response staff = mapper.readValue(new File(System.getProperty("user.dir")+ GetConfig.getProperties("RESPONSE_JSON")), Response.class);
        String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
        JSONObject json = new JSONObject(prettyStaff1);
        JSONArray ja = json.getJSONObject("payload").getJSONArray("items");

        return ja;
    }

    /**
     * Method to return document statuses
     *
     * @return Map data structure which has statuses and its count
     * @throws IOException
     * @throws ConfigurationException
     */
    public static Map<String, Integer> getStatuses() throws IOException, ConfigurationException {
        JSONArray ja = getItems();
        Map<String, Integer> statuses = new HashMap<String, Integer>();
        for (int i=0;i<ja.length();i++) {
            int num = 0;
            if (statuses.containsKey(ja.getJSONObject(i).getString("status"))) {
                num = statuses.get(ja.getJSONObject(i).getString("status"));
            }
            statuses.put(ja.getJSONObject(i).getString("status"), num + 1);
        }
        return statuses;
    }

    /**
     *  Method to return  documents details of the status passed
     *
     * @param status Status to be selected and processed
     * @return Map data structure which return document details without 'status' attributes
     * @throws IOException
     * @throws ConfigurationException
     */
    public static ArrayList<Object> documentDetailsByStatus(String status) throws IOException, ConfigurationException {
        ArrayList<Object> list = new ArrayList<>();
        JSONArray ja = getItems();
        for (int i = 0; i < ja.length(); i++) {
            if ((ja.getJSONObject(i).getString("status")).equals(status)) {
                JSONObject jsonObject =  ja.getJSONObject(i);
                jsonObject.remove("status");
                list.add(JSON.parse(ja.getJSONObject(i).toString()));
            }
        }
        System.out.println(list.toString());
        return list;
    }


    /**
     * Method to return  documents details of the filename passed
     * @param filename Filename
     * @return Map data structure which return document details without 'file_name' attributes
     * @throws IOException
     * @throws ConfigurationException
     */
    public static ArrayList<Object> documentDetailsByFilename(String filename) throws IOException, ConfigurationException {
        ArrayList<Object> list = new ArrayList<>();
        JSONArray ja = getItems();
        for (int i = 0; i < ja.length(); i++) {
            if ((ja.getJSONObject(i).getString("file_name")).equals(filename)) {
                JSONObject jsonObject =  ja.getJSONObject(i);
                jsonObject.remove("file_name");
                list.add(JSON.parse(ja.getJSONObject(i).toString()));
            }
        }
        System.out.println(list.toString());
        return list;
    }
}

