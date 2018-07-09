package com.maoding.circle.dto;

public class MaodingCircleNoticeDTO {

    private MaodingCircleBaseDTO circle;

    private MaodingCircleCommentDataDTO comment;

    public MaodingCircleBaseDTO getCircle() {
        return circle;
    }

    public void setCircle(MaodingCircleBaseDTO circle) {
        this.circle = circle;
    }

    public MaodingCircleCommentDataDTO getComment() {
        return comment;
    }

    public void setComment(MaodingCircleCommentDataDTO comment) {
        this.comment = comment;
    }
}
