package ca.ucalcary.cpsc.groupprojectgui;

import java.io.File;

public class Main {
    public static void main(String []args){
        if (args.length>2){
            System.out.println("Expected one command line argument");
        }
        if (args.length == 1){
            String filename = args[0];
            File file = new File(filename);
            if (!file.exists()|| !file.canRead()){
                System.err.println("cannot load from file");
                System.exit(1);
            }
            Menu.menuLoop(file);
        }else {
            Menu.menuLoop(null);
        }

    }
}
