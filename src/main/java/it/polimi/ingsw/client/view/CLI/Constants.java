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
    public final static String WhiteBALL = ANSI_WHITE + CIRCLE + ANSI_RESET;
    public final static String PurpleBALL = ANSI_PURPLE + CIRCLE + ANSI_RESET;
    public final static String BlueBALL = ANSI_BLUE + CIRCLE + ANSI_RESET;
    public final static String YellowBALL = ANSI_YELLOW + CIRCLE + ANSI_RESET;
    public final static String GreyBALL = ANSI_GREY + CIRCLE + ANSI_RESET;
    public final static String RedBALL = ANSI_RED + CIRCLE + ANSI_RESET;
    public final static String BALL_TOP_BOTTOM_EDGE = "+++++++++++++";

    public final static String LEFT_ARROW = "🠔";
    public final static String UPPER_ARROW = "\uD83E\uDC15";

    public final static String MAESTRI_RINASCIMENTO = "\"_________   _______  _______  _______  _______ _________ _______ _________   ______   _______  _            \\n\" +\n" +
            "                \"\\\\__   __/  (       )(  ___  )(  ____ \\\\(  ____ \\\\\\\\__   __/(  ____ )\\\\__   __/  (  __  \\\\ (  ____ \\\\( \\\\           \\n\" +\n" +
            "                \"   ) (     | () () || (   ) || (    \\\\/| (    \\\\/   ) (   | (    )|   ) (     | (  \\\\  )| (    \\\\/| (           \\\" +\n" +
            "                \"   | |     | || || || (___) || (__    | (_____    | |   | (____)|   | |     | |   ) || (__    | |           \\n\" +\n" +
            "                \"   | |     | |(_)| ||  ___  ||  __)   (_____  )   | |   |     __)   | |     | |   | ||  __)   | |           \\n\" +\n" +
            "                \"   | |     | |   | || (   ) || (            ) |   | |   | (\\\\ (      | |     | |   ) || (      | |           \\n\" +\n" +
            "                \"___) (___  | )   ( || )   ( || (____/\\\\/\\\\____) |   | |   | ) \\\\ \\\\_____) (___  | (__/  )| (____/\\\\| (____/\\\\     \\n\" +\n" +
            "                \"\\\\_______/  |/     \\\\||/     \\\\|(_______/\\\\_______)   )_(   |/   \\\\__/\\\\_______/  (______/ (_______/(_______/     \\n\" +\n" +
            "                \"                                                                                                            \\n\" +\n" +
            "                \" _______ _________ _        _______  _______  _______ _________ _______  _______  _       _________ _______ \\n\" +\n" +
            "                \"(  ____ )\\\\__   __/( (    /|(  ___  )(  ____ \\\\(  ____ \\\\\\\\__   __/(       )(  ____ \\\\( (    /|\\\\__   __/(  ___  )\\n\" +\n" +
            "                \"| (    )|   ) (   |  \\\\  ( || (   ) || (    \\\\/| (    \\\\/   ) (   | () () || (    \\\\/|  \\\\  ( |   ) (   | (   ) |\\n\" +\n" +
            "                \"| (____)|   | |   |   \\\\ | || (___) || (_____ | |         | |   | || || || (__    |   \\\\ | |   | |   | |   | |\\n\" +\n" +
            "                \"|     __)   | |   | (\\\\ \\\\) ||  ___  |(_____  )| |         | |   | |(_)| ||  __)   | (\\\\ \\\\) |   | |   | |   | |\\n\" +\n" +
            "                \"| (\\\\ (      | |   | | \\\\   || (   ) |      ) || |         | |   | |   | || (      | | \\\\   |   | |   | |   | |\\n\" +\n" +
            "                \"| ) \\\\ \\\\_____) (___| )  \\\\  || )   ( |/\\\\____) || (____/\\\\___) (___| )   ( || (____/\\\\| )  \\\\  |   | |   | (___) |\\n\" +\n" +
            "                \"|/   \\\\__/\\\\_______/|/    )_)|/     \\\\|\\\\_______)(_______/\\\\_______/|/     \\\\|(_______/|/    )_)   )_(   (_______)\\n\" +\n" +
            "                \"";


    public static void main(String[] args) {
        System.out.println(FAITH);
    }
}
