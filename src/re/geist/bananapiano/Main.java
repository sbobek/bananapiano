package re.geist.bananapiano;


import re.geist.bananapiano.serialreader.SerialReader;

public class Main {

    public static void main(String[] args) throws Exception {
        SerialReader piano = new SerialReader();
        piano.registerHandler(new PianoEventHandler());
        piano.initialize();

        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
                piano.close();
            }
        };
        t.start();
        System.out.println("Started");
    }


}
