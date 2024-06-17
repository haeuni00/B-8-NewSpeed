package com.example.newspeed.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }
}
