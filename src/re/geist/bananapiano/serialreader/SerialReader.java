package re.geist.bananapiano.serialreader;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;


public class SerialReader implements SerialPortDataListener {
    private static final int COMMAND_SIZE = 5;
    private static final char COMMAND_TERMINATED = '\n';
    private char[] commandBuffer = new char[COMMAND_SIZE];
    private int commandCarretLoc = 0;
    SerialPort serialPort;
    EventHandler eventHandler;
    /** The port we're normally going to use. */
    public static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi Arduino
            "/dev/ttyACM1", // Raspberry Pi Arduino
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
    };

    public static final String DEFAULT_PORT = "/dev/ttyACM0";
    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */

    public synchronized void registerHandler(EventHandler h){
        eventHandler = h;
    }

    public synchronized  void unregisterHandlers(){
        if(eventHandler != null) {
            eventHandler.terminate();
            eventHandler = null;
        }
    }


    public void initialize(String forcePort) {

        serialPort = null;
        SerialPort[] portEnum = SerialPort.getCommPorts();

        //First, Find an instance of serial port as set in PORT_NAMES.
        for(SerialPort p : portEnum){
                if (p.getSystemPortPath().equals(forcePort)) {
                    serialPort = p;
                    break;
                }
        }
        if (serialPort == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort.openPort();

            // add listener
            serialPort.addDataListener(this);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public void initialize() {
        initialize(DEFAULT_PORT);
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        byte[] newData = event.getReceivedData();
        for (int i = 0; i < newData.length; ++i) {
            char c = (char) newData[i];
            if (c == COMMAND_TERMINATED) {
                commandBuffer[commandCarretLoc]='\0';
                eventHandler.handle(String.valueOf(commandBuffer));
                commandCarretLoc = 0;
            } else {
                commandBuffer[commandCarretLoc++] = c;
            }
        }
    }


    public void close() {
        serialPort.removeDataListener();
        serialPort.closePort();
    }
}
