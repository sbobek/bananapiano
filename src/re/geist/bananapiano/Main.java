package re.geist.bananapiano;


import re.geist.bananapiano.serialreader.EventHandler;
import re.geist.bananapiano.serialreader.SerialReader;

import javax.sound.midi.Instrument;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws Exception {
        SerialReader piano = new SerialReader();
        EventHandler handler =new GenericPianoEventHandler();
        piano.registerHandler(handler);

        boolean finish = false;
        Scanner scanner = new Scanner(System.in);
        while(!finish){
            System.out.println("1. Start listening Arduino");
            System.out.println("2. Restart connection");
            System.out.println("3. Configure handler");
            System.out.println("4. Configure instruments");
            System.out.println("5. Test Synthesizer");
            System.out.println("0. Exit");
            String choice = scanner.nextLine();

            if(choice.equals("1")){
                piano.initialize();
                System.out.println("Started!");
            }else if(choice.equals("2")){
                System.out.print("Provide port name [\"/dev/ttyACM0\"]: ");
                String port = scanner.nextLine();
                if(port.trim().length() == 0){
                    port = SerialReader.DEFAULT_PORT;
                }

                piano = new SerialReader();
                piano.unregisterHandlers();
                piano.registerHandler(handler);
                piano.initialize(port);
            }else if(choice.equals("3")){
                System.out.print("Choose Handler (BasicHandler/GenericHandler) [GenericHandler]: ");
                String handlerStr = scanner.nextLine();
                if(handlerStr.equals("BasicHandler")){
                    piano.unregisterHandlers();
                    handler = new BasicPianoEventHandler();
                    piano.registerHandler(handler);
                }else if(handlerStr.equals("GenericHandler")){
                    piano.unregisterHandlers();
                    handler = new GenericPianoEventHandler();
                    piano.registerHandler(handler);
                }
            }else if(choice.equals("4")){
                if(!(handler instanceof GenericPianoEventHandler)){
                    System.out.println("You need to have GenericHandler for custom instruments");
                    continue;
                }
                int instrument = printAndChooseInstruments(((GenericPianoEventHandler) handler).getInstrumentList());
                if(instrument != -1){
                    ((GenericPianoEventHandler) handler).reloadInstrument(instrument);
                }
            }else if(choice.equals("5")){
                testSynthesizer(handler);
            }
            else if(choice.equals("0")){
                piano.close();
                finish = true;
            }
        }

    }

    public static int printAndChooseInstruments(Instrument[] instrumentList){
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < instrumentList.length;i++){
            System.out.println(i+". "+instrumentList[i].getName());
            if((i+1)%10 == 0 || i == instrumentList.length-1) {
                System.out.print("Select instrument [type n for next, q for quit]");
                String choice = sc.nextLine();
                if (choice.equals("n")) {
                    try {
                        Runtime.getRuntime().exec("clear");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (choice.equals("q")) {
                    return -1;
                }else if(choice.matches("[0-9]*")){
                    return Integer.parseInt(choice);
                }else{
                    try {
                        Runtime.getRuntime().exec("clear");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i -= 9;
                }
            }
        }
        return -1;

    }

    public static void testSynthesizer(EventHandler handler) throws InterruptedException {
        String [] notes = new String[]{"C","D","E","F","G","A","H","CC"};
        for(int i = 0; i < notes.length;i++){
            handler.handle(notes[i]+":P");
            sleep(500);
        }

    }


}
