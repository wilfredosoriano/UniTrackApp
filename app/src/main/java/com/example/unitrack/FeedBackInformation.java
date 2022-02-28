package com.example.unitrack;

public class FeedBackInformation {
    String feedback, star;

    public FeedBackInformation(String feedback, String star) {
        this.feedback = feedback;
        this.star = star;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
