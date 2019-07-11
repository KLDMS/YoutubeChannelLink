package Main;



public class Main  {

    private static long NUMBER_OF_VIDEOS_RETURNED =50;

    private static String CHANNEL_ID = "UCH_1nXeXLXXk32-NOU5G44g";

    public static void main(String[] args) {


        LinkGetter linkGetter= new LinkGetter();
        linkGetter.GetLink(NUMBER_OF_VIDEOS_RETURNED, CHANNEL_ID);


    }

}
