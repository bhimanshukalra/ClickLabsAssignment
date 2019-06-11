package com.bhimanshukalra.retrofitassignment.models;

import static com.bhimanshukalra.retrofitassignment.constants.constants.NEXT_LINE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.POST_BODY;
import static com.bhimanshukalra.retrofitassignment.constants.constants.POST_ID;
import static com.bhimanshukalra.retrofitassignment.constants.constants.POST_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.POST_USER_ID;

public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public String getPostDetails(){
        String postDetails = "";
        postDetails += POST_USER_ID + userId + NEXT_LINE;
        postDetails += POST_ID+ id + NEXT_LINE;
        postDetails += POST_TITLE+ NEXT_LINE + title + NEXT_LINE;
        postDetails += POST_BODY+ NEXT_LINE + body + NEXT_LINE;
        return postDetails;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
