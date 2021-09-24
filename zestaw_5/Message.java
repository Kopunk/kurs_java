public class Message {
    static String codePrefix = "CODE:";
    String message;

    Message() {
        this.message = " ";
    }

    Message(String message) {
        if (message.toLowerCase() == "exit") {
            message = codePrefix + "1";
        }
        this.message = message;
    }

    boolean isCode() {
        if (message.startsWith(codePrefix)) {
            return true;
        }
        return false;
    }

    String getCode() {
        if ((!this.isCode()) || (message.length() != codePrefix.length() + 1)) {
            return "MESSAGE";
        }
        char code = message.charAt(codePrefix.length());
        if (code == '0') {
            return "OK";
        } else if (code == '1') {
            return "EXIT";
        } else {
            return "ERROR";
        }
    }

    public String toString() {
        return message;
    }
}
