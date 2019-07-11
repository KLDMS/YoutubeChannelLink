package GUI;

import Main.ChannelLinkGetter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable  {

    public ChoiceBox OrderChoiceBox;
    public javafx.scene.control.DatePicker DatePicker;
    public CheckBox DateCheck;
    ObservableList<String> orderStatus = FXCollections.observableArrayList("date","rating","relevance","title ","videoCount","viewCount");
    public TextField Quantityfield;
    public TextField ChannelIDfield;
    public TextArea OutputField;


    private long NUMBER_OF_VIDEOS_RETURNED;
    private String CHANNEL_ID;
    private String DateAfter;
    private List<String> searchResults;
    private String searchResult;
    ChannelLinkGetter channel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OrderChoiceBox.setValue("date");
        OrderChoiceBox.setItems(orderStatus);


    }

    public void Onsearch(ActionEvent actionEvent) {
        NUMBER_OF_VIDEOS_RETURNED = Long.parseLong(Quantityfield.textProperty().get());
        CHANNEL_ID = ChannelIDfield.textProperty().get();
        if(DatePicker.getValue() !=null)
            DateAfter = DatePicker.getValue().toString()+"T00:00:00Z";
        else DateAfter = new String("1970-01-01T00:00:00Z");
        System.out.println(DateAfter);


        channel = new ChannelLinkGetter();
        searchResult = StringListToString(channel.GetLink(NUMBER_OF_VIDEOS_RETURNED, CHANNEL_ID,"None",OrderChoiceBox.getId(),DateAfter));
        System.out.println(searchResult);
        OutputField.textProperty().set(searchResult);

    }

    private String StringListToString(List<String> inputList){
        String returnString = new String();
        if(inputList.isEmpty() || inputList == null){returnString = "No Results";}
        else for(String s : inputList){
            returnString +=s + "\n";
        }
        return returnString;

    }


    public void OnPrev(ActionEvent actionEvent) {
        searchResult = StringListToString(channel.GetLink(NUMBER_OF_VIDEOS_RETURNED, CHANNEL_ID,"Prev",OrderChoiceBox.getId(),DateAfter));
        OutputField.textProperty().set(searchResult);
    }

    public void OnNext(ActionEvent actionEvent) {
        searchResult = StringListToString(channel.GetLink(NUMBER_OF_VIDEOS_RETURNED, CHANNEL_ID,"Next",OrderChoiceBox.getId(),DateAfter));
        OutputField.textProperty().set(searchResult);
    }


    public void OnDateCheck(ActionEvent actionEvent) {
        if(DateCheck.isSelected()){
            DatePicker.setDisable(false);
        }
        else{
            DatePicker.setDisable(true);
        }
    }
}
