package re.geist.bananapiano;


import re.geist.bananapiano.serialreader.SerialReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        SerialReader piano = new SerialReader();
        piano.registerHandler(new BasicPianoEventHandler());

        boolean finish = false;
        Scanner scanner = new Scanner(System.in);
        while(!finish){
            System.out.println("1. Start listening Arduino");
            System.out.println("2. Restart connection");
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
                piano.registerHandler(new BasicPianoEventHandler());
                piano.initialize(port);
            }else if(choice.equals("0")){
                piano.close();
                finish = true;
            }
        }

    }


}
