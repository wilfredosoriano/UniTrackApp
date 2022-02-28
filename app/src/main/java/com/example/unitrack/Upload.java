package com.example.unitrack;

public class Upload {
    private String Description;
    private String ImageUrl;
    private String Key;

    public Upload(){

    }
    public Upload(String description, String imageUrl){
        if (description.trim().equals("")){
            description = "No Description";
        }

        Description = description;
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
