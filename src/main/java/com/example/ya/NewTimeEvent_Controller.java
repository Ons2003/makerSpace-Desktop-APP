package com.example.ya;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewTimeEvent_Controller {

    @FXML
    public Label day_label;
    @FXML
    public TextField eventName_if;
    @FXML
    public TextField startTimeH_if;
    public TextField startTimeM_if;
    @FXML
    public TextField endTimeH_if;
    public TextField endTimeM_if;
    @FXML
    public ChoiceBox eventType_cbox;
    @FXML
    public TextArea descriptionTArea;
    @FXML
    public Button createButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button saveButton;


    public static NewTimeEvent_Controller instance;


    public void initialize() {
        instance = this;

    }

    public void SetupData(String dayName, boolean isEditing, TopicParameters[] topicParameters){
        day_label.setText(dayName);
        eventType_cbox.setItems(FXCollections.observableArrayList(TimelineController.GetTopicsTitlesForUserRole(AccountsManager.loggedAccount.role)));
        eventType_cbox.setValue((String)eventType_cbox.getItems().get(0));
        createButton.setVisible(!isEditing);
        saveButton.setVisible(isEditing);
        deleteButton.setVisible(isEditing);

        eventName_if.setText("New Event");
        startTimeM_if.setText("00");
        startTimeH_if.setText("07");
        endTimeM_if.setText("00");
        endTimeH_if.setText("08");
        descriptionTArea.setText("More Information ...");
    }

    public void PreFill(TimeEvent timeEvent){
        eventName_if.setText(timeEvent.name);
        startTimeH_if.setText(timeEvent.start.hour + "");
        startTimeM_if.setText(timeEvent.start.minute + "");
        endTimeH_if.setText(timeEvent.end.hour + "");
        endTimeM_if.setText(timeEvent.end.minute + "");
        eventType_cbox.setValue(timeEvent.topic);
        descriptionTArea.setText(timeEvent.description);
    }

    public void SaveEdits_ButtonInput() {
        int startHour = 6;
        int startMinute = 59;
        int endHour = 7;
        int endMinute = 1;

        if(!startTimeH_if.getText().isBlank())
            startHour = Integer.parseInt(startTimeH_if.getText());
        if(!startTimeM_if.getText().isBlank())
            startMinute = Integer.parseInt(startTimeM_if.getText());
        if(!endTimeH_if.getText().isBlank())
            endHour = Integer.parseInt(endTimeH_if.getText());
        if(!endTimeM_if.getText().isBlank())
            endMinute = Integer.parseInt(endTimeM_if.getText());

        String name = eventName_if.getText();
        if(name.isEmpty()) name = "New Event";

        TimelineController.instance.SaveTimeEventEdits(name, new Instant(startHour, startMinute),
                new Instant(endHour, endMinute), (String)eventType_cbox.getValue(), descriptionTArea.getText());
    }

    public void DeleteEvent_ButtonInput() {
        TimelineController.instance.RemoveTimeEvent();
    }

    public void CreateEvent_ButtonInput(){
        int startHour = 6;
        int startMinute = 59;
        int endHour = 7;
        int endMinute = 1;

        if(!startTimeH_if.getText().isBlank())
             startHour = Integer.parseInt(startTimeH_if.getText());
        if(!startTimeM_if.getText().isBlank())
            startMinute = Integer.parseInt(startTimeM_if.getText());
        if(!endTimeH_if.getText().isBlank())
            endHour = Integer.parseInt(endTimeH_if.getText());
        if(!endTimeM_if.getText().isBlank())
            endMinute = Integer.parseInt(endTimeM_if.getText());

        String name = eventName_if.getText();
        if(name.isEmpty()) name = "New Event";

        TimelineController.instance.CreateNewTimeEvent(name, new Instant(startHour, startMinute),
                new Instant(endHour, endMinute), (String)eventType_cbox.getValue(), descriptionTArea.getText());
        TimelineController.instance.FinishEventEditing();
    }
}
