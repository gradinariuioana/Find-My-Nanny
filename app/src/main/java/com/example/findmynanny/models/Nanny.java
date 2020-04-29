package com.example.findmynanny.models;
import org.json.JSONException;
import org.json.JSONObject;

public class Nanny {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String phoneNumber;
    private String photoUrl;

    public Nanny(){
    }

    public Nanny fromJSON(JSONObject nannyJson) {
        try {
            id = nannyJson.getInt("id");
            firstName = nannyJson.getString("firstName");
            lastName = nannyJson.getString("lastName");
            age = Integer.parseInt(nannyJson.getString("age"));
            address = nannyJson.getString("address");
            phoneNumber = nannyJson.getString("phoneNumber");
            photoUrl = nannyJson.getString("photoUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
