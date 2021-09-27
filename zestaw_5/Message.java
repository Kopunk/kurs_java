class Message {
    static String codePrefix = "CODE:";
    String message;

    static String EXIT = "EXIT";

    Message() {
        this.message = " ";
    }

    Message(String message) {
        if (message.toUpperCase() == EXIT) {
            message = codePrefix + "1";
        }
        this.message = message;
    }

    boolean isCode() {
        if (message.startsWith(codePrefix) || message.startsWith(EXIT)) {
            return true;
        }
        return false;
    }

    String getCode() {
        char code = message.charAt(codePrefix.length());
        if (code == '0') {
            return "OK";
        } else if (code == '1') {
            return EXIT;
        } else {
            return "NONE";
        }
    }

    public String toString() {
        return message;
    }
}
