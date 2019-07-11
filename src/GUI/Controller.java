package GUI;

import Main.LinkGetter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable  {

    public TextField Quantityfield;
    public TextField ChannelIDfield;
    public TextArea OutputField;
    public Button SearchButton;

    private long NUMBER_OF_VIDEOS_RETURNED;
    private String CHANNEL_ID;
    private List<String> searchResults;
    private String searchResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void Onsearch(ActionEvent actionEvent) {
        NUMBER_OF_VIDEOS_RETURNED = Long.parseLong(Quantityfield.textProperty().get());
        CHANNEL_ID = ChannelIDfield.textProperty().get();

        LinkGetter linkGetter = new LinkGetter();
        searchResult = StringListToString(linkGetter.GetLink(NUMBER_OF_VIDEOS_RETURNED, CHANNEL_ID));
        System.out.println(searchResult);
        OutputField.textProperty().set(searchResult);

    }

    private String StringListToString(List<String> inputList){
        String returnString = new String();
        for(String s : inputList){
            returnString +=s + "\n";
            System.out.println(returnString);
        }
        return returnString;

    }
}
