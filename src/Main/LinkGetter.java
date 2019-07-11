package Main;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkGetter {

    private YouTube youtube;

    private long NUMBER_OF_VIDEOS_RETURNED;

    private  String CHANNEL_ID;

    private String nextpage;
    private String prevpage;
    private List<String> returnlist;
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    public List<SearchResult> searchResultList =new ArrayList<>() ;

    public List<String> GetLink(long Videosreturned, String ChannelID){
        NUMBER_OF_VIDEOS_RETURNED = Videosreturned;
        CHANNEL_ID = ChannelID;
        returnlist = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(com.google.api.client.http.HttpRequest httpRequest) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-GUI").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            search.setKey("AIzaSyACVD12BtkfM_AToR4lvs9MnTDUXgiKMfo");
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setChannelId(CHANNEL_ID);
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            search.setOrder("Date");

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();
            nextpage = searchResponse.getNextPageToken();
            prevpage = searchResponse.getPrevPageToken();


            if (searchResultList != null) {
                Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
                while (iteratorSearchResults.hasNext()) {

                    SearchResult singleVideo = iteratorSearchResults.next();
                    ResourceId rId = singleVideo.getId();

                    // Confirm that the result represents a video. Otherwise, the
                    // item will not contain a video ID.
                    if (rId.getKind().equals("youtube#video")) {
                        returnlist.add("https://www.youtube.com/watch?v=" + rId.getVideoId());
                        System.out.println(returnlist);
                    }
                }
            }
        }
        catch(GoogleJsonResponseException e)

        {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        }
        catch(IOException e)

        {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        catch(Throwable t)

        {
            t.printStackTrace();
        }
        return returnlist;

    }

    public List<String> GetLinkList(){

        List<SearchResult> resultslist = searchResultList;
        List<String> returnlist = new ArrayList<>();
        while (searchResultList.iterator().hasNext()) {

            SearchResult singleVideo = searchResultList.iterator().next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                returnlist.add("https://www.youtube.com/watch?v=" + rId.getVideoId());

            }
        }
        return returnlist;
    }

    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults) {



        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                System.out.println("Thumbnai: " + thumbnail.getUrl());

                System.out.println("https://www.youtube.com/watch?v=" + rId.getVideoId());
            }
        }
    }

}
