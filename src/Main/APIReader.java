package Main;

import java.io.*;

public class APIReader {

    public String GetKey() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/APIKEYhere.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String APIKEY = new String();
        String line;
        while((line = in.readLine()) != null)
        {
            APIKEY += line;
        }
        in.close();
        return APIKEY;
    }
}
