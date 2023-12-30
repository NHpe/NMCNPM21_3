package com.group18.dormitory.Model;

public class RoomRegistrationInformation {
    private String roomId;
    private String studentId;

    public RoomRegistrationInformation() {
    }

    public RoomRegistrationInformation(String roomId, String studentId) {
        this.roomId = roomId;
        this.studentId = studentId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
