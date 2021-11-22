public class ThreadLogger extends Thread {
    void log(String logString) {
        System.out.println("@ " + this.getClass().getName() + ": " + logString);
    }

    void logStart() {
        log("STARTED");
    }

    void logMessage(Message message) {
        log(message.toString());
    }
}
