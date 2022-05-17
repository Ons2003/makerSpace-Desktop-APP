package com.example.ya;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineController implements TimeEventPanel.ITimeEventListener {

    public static TimelineController instance;

    private Timetable timetable = new Timetable("Makerspace Reservation Timeline", "makerspace_timeline");

    ///region USER INTERFACE VARIABLES
    @FXML
    public BorderPane table_Pane;
    @FXML
    public Label title_Label;
    @FXML
    public HBox contentParent_HBox;
    @FXML
    public Label eventTitle_label;
    @FXML
    public Label eventType_label;
    @FXML
    public Label eventCreator_label;
    @FXML
    public Label eventCreationTime_label;
    @FXML
    public Label eventDescription_label;
    @FXML
    public AnchorPane eventDisplayAPane;
    @FXML
    public Button loggedAccountName_button;
    @FXML
    public Button goHomeButton;
    @FXML
    public Label loggedAccountName_Label;
    ///endregion

    ///region EVENT CREATION
    public static boolean isCreatingNewEvent = false;
    private Stage newEventPopup;
    private static int columnSelected;
    ///endregion

    ///region EVENT EDITING
    public static boolean isEditingEvent = false;
    private TimeEventPanel eventBeingEdited;
    ///endregion

    public static TopicParameters[] topics;

    public void initialize() throws IOException {
        instance = this;

        topics = new TopicParameters[]{
                new TopicParameters("Personal Project", new Account.UserRole[]{Account.UserRole.Student, Account.UserRole.SuperUser},
                        AccountsManager.AccessPermission.Owner, 0),
                new TopicParameters("Studies Project", new Account.UserRole[]{Account.UserRole.Student, Account.UserRole.SuperUser},
                        AccountsManager.AccessPermission.Owner, 1),
                new TopicParameters("Experiment", new Account.UserRole[]{Account.UserRole.Student, Account.UserRole.SuperUser},
                        AccountsManager.AccessPermission.Owner, 2),
                new TopicParameters("Class Meeting", Account.UserRole.Professor, AccountsManager.AccessPermission.Global, 3),
                new TopicParameters("Weekly Presence", Account.UserRole.SuperUser, AccountsManager.AccessPermission.SuperUserOnly, 4)
        };

        SetupTable();

        eventDisplayAPane.setVisible(false);
    }

    public static TopicParameters GetTopicOfTitle(String eventTopic){
        for(int i = 0; i < topics.length; i++){
            if(topics[i].title == eventTopic) return topics[i];
        }
        return null;
    }

    public static String[] GetTopicsTitlesForUserRole(Account.UserRole role){
        List<String> result = new ArrayList<String>();
        for(int i = 0;i<topics.length;i++){
            for(int j = 0; j<topics[i].availableFor.length; j++){
                if(topics[i].availableFor[j] == role) {
                    result.add(topics[i].title);
                    break;
                }
            }
        }
        return result.toArray(new String[0]);
    }

    public static TopicParameters[] GetTopicsForUserRole(Account.UserRole role){
        List<TopicParameters> result = new ArrayList<TopicParameters>();
        for(int i = 0;i<topics.length;i++){
            for(int j = 0; j<topics[i].availableFor.length; j++){
                if(topics[i].availableFor[j] == role) {
                    result.add(topics[i]);
                    break;
                }
            }
        }
        return result.toArray(new TopicParameters[0]);
    }

    public void SetupTable(){
        contentParent_HBox.setSpacing(0);
        title_Label.setText(timetable.title);
        title_Label.setId("title");
        timetable.columns = Timetable.GetEmptyWeekColumns();
        for(int i = 0;i<timetable.columns.length;i++){
            timetable.columns[i].GenerateColumnUI();
            contentParent_HBox.getChildren().add(timetable.columns[i].GetColumnVbox());

            Button addButton = new Button("Add Event");
            addButton.setFocusTraversable(false);
            addButton.setStyle("-fx-background-color: #fff045");
            int columnIndex = i;
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    StartNewEventCreation(columnIndex);
                }
            });
            timetable.columns[i].AddStaticNode(addButton);
        }
        timetable.RetrieveEventsFromDatabase(this);

        try {
            SetupNewEventPopup();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    ///region EVENT CREATION
    private void SetupNewEventPopup() throws IOException {
        newEventPopup = new Stage();
        newEventPopup.setTitle("New Event");
        newEventPopup.setResizable(false);
        BorderPane pane = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(Core.class.getResource("NewTimeEvent-View.fxml"));
        newEventPopup.setScene(new Scene(fxmlLoader.load()));
        newEventPopup.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                FinishEventEditing();
            }
        });
    }

    public void StartNewEventCreation(int columnId){
        if(isCreatingNewEvent) return;
        columnSelected = columnId;
        OpenEventEditor(false);
    }


    public void OpenEventEditor(boolean isEditing){
        if(isEditing){
            NewTimeEvent_Controller.instance.SetupData(timetable.columns[eventBeingEdited.event.columnID].title,
                    isEditing, GetTopicsForUserRole(AccountsManager.loggedAccount.role));
            NewTimeEvent_Controller.instance.PreFill(eventBeingEdited.event);
        }else{
            NewTimeEvent_Controller.instance.SetupData(timetable.columns[columnSelected].title, isEditing,
                    GetTopicsForUserRole(AccountsManager.loggedAccount.role));
        }

        isCreatingNewEvent = true;
        newEventPopup.show();
    }

    public void FinishEventEditing(){
        if(newEventPopup.isShowing())
            newEventPopup.hide();
        if(isCreatingNewEvent)
            isCreatingNewEvent = false;
        if(isEditingEvent)
            isEditingEvent = false;
    }

    public void CreateNewTimeEvent(String name, Instant startTime, Instant endTime, String topic, String description) {
        timetable.AddNewTimeEvent(columnSelected, name, startTime, endTime, topic, description, this,
                GetTopicOfTitle(topic).viewPermission);
    }

    public void RemoveTimeEvent(){
        timetable.RemoveEvent(eventBeingEdited.event.id);
        FinishEventEditing();
    }

    public void SaveTimeEventEdits(String name, Instant startTime, Instant endTime, String topic, String description){
        eventBeingEdited.event.name = name;
        eventBeingEdited.event.start = startTime;
        eventBeingEdited.event.end = endTime;
        eventBeingEdited.event.topic = topic;
        eventBeingEdited.event.description = description;
        DatabaseManager.EditTimetableEvent(timetable, eventBeingEdited.event);

        timetable.RetrieveEventsFromDatabase(this);
        FinishEventEditing();
    }
    ///endregion

    ///region EVENT ACTIONS
    @Override
    public void DisplayTimeEventData(TimeEvent timeEvent) {
        eventTitle_label.setText(timeEvent.name);
        eventType_label.setText(timeEvent.topic.toString());
        System.out.println("Creator SID is " + timeEvent.creation.creator.SID);
        eventCreator_label.setText(timeEvent.creation.creator.first);
        eventCreationTime_label.setText(timeEvent.creationDate.toString() + " " + timeEvent.creationDate.day + "/" +
                timeEvent.creationDate.month);
        eventDescription_label.setText(timeEvent.description);

        eventDisplayAPane.setVisible(true);
    }

    public void goToHome(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Account Profile");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void HideTimeEventData() {
        eventDisplayAPane.setVisible(false);
    }

    @Override
    public void StartEditingTimeEventData(TimeEvent timeEvent) {
        if(isEditingEvent) return;
        isEditingEvent = true;
        eventBeingEdited = timetable.GetTimeEventPanelOfId(timeEvent.id);
        OpenEventEditor(true);
    }
    ///endregion

    public void OpenHome(){
        Core.changeScene("Home.fxml", "Home");
    }
}