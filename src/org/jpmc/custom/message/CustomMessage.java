package org.jpmc.custom.message;

public class CustomMessage {

    String fileName;
    String message;
    String result;

    public CustomMessage(String fileName, String message, String result) {
        this.fileName=fileName;
        this.message=message;
        this.result=result;
    }

    @Override
    public String toString() {
        return "File " + this.fileName + " " + this.message + " " + this.result;
    }

}
