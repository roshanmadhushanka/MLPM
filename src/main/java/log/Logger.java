package log;

import system.Setting;

import java.sql.Timestamp;

/**
 * Created by wso2123 on 8/22/16.
 */
public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void log(MESSAGE_TYPE msgType, String message, String sourceClass, String sourceMethod){
        //Check debug state
        if(!Setting.debug){
            //Hide prompt message
            return;
        }

        //Get current time stamp
        java.util.Date date= new java.util.Date();
        String timeStamp = new Timestamp(date.getTime()).toString();

        //Generate header
        String header = "";
        String log = "";

        //Build message
        switch(msgType){
            case ERROR:
                header = "[ERROR]";
                log = ANSI_RED + timeStamp + " : " + header + " > " + message + "\nSource : " + sourceClass + " -> " + sourceMethod + ANSI_RESET;
                break;
            case INFO:
                header = "[INFO]";
                log = ANSI_BLUE + timeStamp + " : " + header + " > " + message + "\nSource : " + sourceClass + " -> " + sourceMethod + ANSI_RESET;
                break;
            case DEVELOPER:
                header = "[DEVELOPER]";
                log = ANSI_CYAN + timeStamp + " : " + header + " > " + message + "\nSource : " + sourceClass + " -> " + sourceMethod + ANSI_RESET;
                break;
            case SUCCESS:
                header = "[SUCCESS]";
                log = ANSI_GREEN + timeStamp + " : " + header + " > " + message + "\nSource : " + sourceClass + " -> " + sourceMethod + ANSI_RESET;
                break;
            case WARNING:
                header = "[WARNING]";
                log = ANSI_YELLOW + timeStamp + " : " + header + " > " + message + "\nSource : " + sourceClass + " -> " + sourceMethod + ANSI_RESET;
                break;
        }

        //Display message
        System.out.println(log);
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
