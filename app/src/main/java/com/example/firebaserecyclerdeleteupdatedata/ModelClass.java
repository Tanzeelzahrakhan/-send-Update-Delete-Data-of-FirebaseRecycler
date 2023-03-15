package com.example.firebaserecyclerdeleteupdatedata;

public class ModelClass {
    String Name;
    String Email;
    String Password;
    String ImageUrl;

    public ModelClass(String name, String email, String password, String imageUrl) {
        Name = name;
        Email = email;
        Password = password;
        ImageUrl = imageUrl;
    }

    public ModelClass() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
