package com.pikri.hands_on_gradle.dto;

public class PingDto {
    public static class Response {
        private String message;
    
        public Response(String message) {
            this.message = message;
        }
    
        // Getters and setters
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
