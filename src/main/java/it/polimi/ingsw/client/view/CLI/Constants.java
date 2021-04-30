package it.polimi.ingsw.client.view.CLI;

public class Constants {


    //ANSI COLOR
    public static final String	ANSI_BLACK				= "\u001B[0;30m";
    public static final String	ANSI_RED				= "\u001B[0;31m";
    public static final String	ANSI_GREEN				= "\u001B[0;32m";
    public static final String	ANSI_YELLOW				= "\u001B[0;33m";
    public static final String	ANSI_BLUE				= "\u001B[0;34m";
    public static final String	ANSI_PURPLE   			= "\u001B[0;35m";
    public static final String	ANSI_CYAN				= "\u001B[0;36m";
    public static final String	ANSI_GREY				= "\u001B[0;37m";
    public static final String  ANSI_WHITE              = "\u001B[1;30m";
    public static final String  ANSI_RESET              = "\u001B[0m";


    //RESOURCE
    public final static String SERVANT      = ANSI_PURPLE + "\u265F" + ANSI_RESET;
    public final static String COIN         = ANSI_YELLOW + "\uD83D\uDCB0" + ANSI_RESET;
    public final static String STONE        = "\u23E2";
    public final static String SHIELD       = ANSI_BLUE + "\uD83D\uDEE1" + ANSI_RESET;
    public final static String UNKNOWN      = "\u2753";

    public final static String FAITH    = "\u271F";
    public final static String CIRCLE = "\uD83D\uDF89";
    public final static String RECTANGLE = "\u25AD";

    public final static String AUTHORS = "Authors: " + ANSI_BLUE + "Francesco Leone " + ANSI_RED + "Nicola Landini " +
                                            ANSI_GREEN + "Lorenzo Iovine " +ANSI_RESET;

    public final static String LEADER_CARD_TOP_EDGE = "++++++++++++++++\n";
    public final static String LEADER_CARD_LEFT_EDGE = "+";
    public final static String LEADER_CARD_RIGHT_EDGE = "+\n";
    public final static String FIVE_WITHE_SPACE = "     ";

    //DEVELOPMENT CARD
    public final static String DEV_CARD_TOP = "+++++++++++++";

    //BALL
    public final static String WhiteBALL    = ANSI_WHITE + CIRCLE + ANSI_RESET;
    public final static String PurpleBALL   = ANSI_PURPLE + CIRCLE + ANSI_RESET;
    public final static String BlueBALL     = ANSI_BLUE + CIRCLE + ANSI_RESET;
    public final static String YellowBALL   = ANSI_YELLOW + CIRCLE + ANSI_RESET;
    public final static String GreyBALL     = ANSI_GREY + CIRCLE + ANSI_RESET;
    public final static String RedBALL      = ANSI_RED + CIRCLE + ANSI_RESET;
    public final static String BALL_TOP_BOTTOM_EDGE = "+++++++++++++";

    public final static String LEFT_ARROW = "🠔";
    public final static String UPPER_ARROW = "\uD83E\uDC15";

    //FAITH PATH
    public final static String OnePOINT     = ANSI_YELLOW + "❶" + ANSI_RESET;
    public final static String TwoPOINTS    = ANSI_YELLOW + "❷" + ANSI_RESET;
    public final static String FourPOINTS   = ANSI_YELLOW + "❹" + ANSI_RESET;
    public final static String SixPOINTS    = ANSI_YELLOW + "❻" + ANSI_RESET;
    public final static String NinePOINTS   = ANSI_YELLOW + "❾" + ANSI_RESET;
    public final static String TwelvePOINTS = ANSI_YELLOW + "⓬" + ANSI_RESET;
    public final static String SixteenPOINTS= ANSI_YELLOW + "⓰" + ANSI_RESET;
    public final static String TwentyPOINTS = ANSI_YELLOW + "⓴" + ANSI_RESET;
    public final static String PapalPOINT1  = ANSI_GREEN + "➊" + ANSI_RESET;
    public final static String PapalPOINT2  = ANSI_GREEN + "➋" + ANSI_RESET;
    public final static String PapalPOINT3  = ANSI_GREEN + "➌" + ANSI_RESET;
    public final static String PapalCELL    = ANSI_RED + "⳩" + ANSI_RESET;
    public final static String ColorFAITH   = ANSI_PURPLE + FAITH + ANSI_RESET;
    public final static String FAITH_LEGEND = ANSI_WHITE +"   FAITH PATH:\n"+ ANSI_RESET +"      ->"+ ANSI_CYAN +" Cyan "+ ANSI_RESET +"numbers represent the position of the FaithPath\n"+"      ->"+ ANSI_RED +" Red "+ ANSI_RESET+"numbers represent the position of papal regions\n"+"      -> "+ PapalCELL +" represents papal spaces\n\n";
    public final static String FAITH_MIDDLE_EDGE = "   + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +";
    public final static String FAITH_TOP_EDGE    = "   + + + + + + + + + + + + + " + OnePOINT +" + + + + + + + + + + + "+ TwoPOINTS +" + + + + + + + + + + + "+ FourPOINTS +" + + + + + + + + + + + + "+ SixPOINTS +" +\n";
    public final static String FAITH_BOTTOM_EDGE = "   + "+ TwentyPOINTS +" + + + + + + + + + + + + "+ SixteenPOINTS +" + + + + + + + + + + + + "+ TwelvePOINTS +" + + + + + + + + + + + + "+ NinePOINTS +" + + + + + + + + + + +\n";

    //SINGLE PLAYER
    public final static String BCM = ANSI_BLACK + FAITH + ANSI_RESET;
    public final static String CARD         = "▇";
    public final static String YellowCARD   = ANSI_YELLOW + CARD + ANSI_RESET;
    public final static String BlueCARD     = ANSI_BLUE + CARD + ANSI_RESET;
    public final static String GreenCARD    = ANSI_GREEN + CARD + ANSI_RESET;
    public final static String PurpleCARD   = ANSI_PURPLE + CARD + ANSI_RESET;
    public final static String SHUFFLE      = ANSI_WHITE + "\uD83D\uDD00" + ANSI_RESET;
    public final static String AT_TOP_BOTTOM_EDGE = ANSI_WHITE + " ******" + ANSI_RESET;
    public final static String TOP_CARD = ANSI_WHITE +"***\n * "+ ANSI_RESET;
    public final static String BOTTOM_CARD = ANSI_WHITE +" *\n"+ ANSI_RESET;


    //STORAGE
    public final static String STORAGE_TOP_BOTTOM_EDGE = "++++++++++++\n";
    public final static String TEN_EMPTY_SPACE = "          ";
    public final static String EDGE = "|";
    public final static String RIGHT_EDGE = "|\n";
    public final static String EMPTY_SPACE = "_";
    public final static String FOUR_EMPTY_SPACE = "    ";
    public final static String FIVE_EMPTY_SPACE = "     ";
    public final static String THREE_EMPTY_SPACE = "   ";
    public final static String TWO_EMPTY_SPACE = "  ";

    //VAULT

    public final static String VAULT = "+++++++\n" +
            "+VAULT+\n" +
            "+++++++";


    public final static String MAESTRI_RINASCIMENTO = "_________   _______  _______  _______  _______ _________ _______ _________   ______   _______  _            \n" +
            "\\__   __/  (       )(  ___  )(  ____ \\(  ____ \\\\__   __/(  ____ )\\__   __/  (  __  \\ (  ____ \\( \\           \n" +
            "   ) (     | () () || (   ) || (    \\/| (    \\/   ) (   | (    )|   ) (     | (  \\  )| (    \\/| (           \n" +
            "   | |     | || || || (___) || (__    | (_____    | |   | (____)|   | |     | |   ) || (__    | |           \n" +
            "   | |     | |(_)| ||  ___  ||  __)   (_____  )   | |   |     __)   | |     | |   | ||  __)   | |           \n" +
            "   | |     | |   | || (   ) || (            ) |   | |   | (\\ (      | |     | |   ) || (      | |           \n" +
            "___) (___  | )   ( || )   ( || (____/\\/\\____) |   | |   | ) \\ \\_____) (___  | (__/  )| (____/\\| (____/\\     \n" +
            "\\_______/  |/     \\||/     \\|(_______/\\_______)   )_(   |/   \\__/\\_______/  (______/ (_______/(_______/     \n" +
            "                                                                                                            \n" +
            " _______ _________ _        _______  _______  _______ _________ _______  _______  _       _________ _______ \n" +
            "(  ____ )\\__   __/( (    /|(  ___  )(  ____ \\(  ____ \\\\__   __/(       )(  ____ \\( (    /|\\__   __/(  ___  )\n" +
            "| (    )|   ) (   |  \\  ( || (   ) || (    \\/| (    \\/   ) (   | () () || (    \\/|  \\  ( |   ) (   | (   ) |\n" +
            "| (____)|   | |   |   \\ | || (___) || (_____ | |         | |   | || || || (__    |   \\ | |   | |   | |   | |\n" +
            "|     __)   | |   | (\\ \\) ||  ___  |(_____  )| |         | |   | |(_)| ||  __)   | (\\ \\) |   | |   | |   | |\n" +
            "| (\\ (      | |   | | \\   || (   ) |      ) || |         | |   | |   | || (      | | \\   |   | |   | |   | |\n" +
            "| ) \\ \\_____) (___| )  \\  || )   ( |/\\____) || (____/\\___) (___| )   ( || (____/\\| )  \\  |   | |   | (___) |\n" +
            "|/   \\__/\\_______/|/    )_)|/     \\|\\_______)(_______/\\_______/|/     \\|(_______/|/    )_)   )_(   (_______)\n" +
            "                                                                                                            \n";


    public static void main(String[] args) {
        System.out.println(VAULT);
    }
}
