package com.example.testing;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class TimetableColumn {
    public String title;
    public List<TimeEventPanel> events = new ArrayList<>();
    private List<Node> staticNodes = new ArrayList<>();
    public int id;

    ///region USER INTERFACE
    private VBox column_vbox;
    public VBox GetColumnVbox(){ return column_vbox; }
    private Label columnTitle_label;
    ///endregion

    public TimetableColumn(int id){
        this.id = id;
    }

    public void GenerateColumnUI() {
        column_vbox = new VBox();
        column_vbox.setId("column");
        column_vbox.setAlignment(Pos.TOP_CENTER);
        column_vbox.setStyle("-fx-background-color: #1E1E1E");
        column_vbox.setMinHeight(400);
        column_vbox.setPrefWidth(300);

        columnTitle_label = new Label(title);
        columnTitle_label.setId("column-title");
        columnTitle_label.setTextFill(HelloApplication.yellow);
        AddStaticNode(columnTitle_label);
    }

    public void UpdateColumnUI(){
        column_vbox.getChildren().clear();
        for(int i = 0;i<staticNodes.size();i++){
            column_vbox.getChildren().add(staticNodes.get(i));
        }
        for(int i = 0;i<events.size();i++){
            column_vbox.getChildren().add(events.get(i).GetPanelHBox());
        }
    }

    public void ClearEvents(){
        for(int j = 0;j<events.size();j++){
            System.out.println("Mahabech yetfasa5 noumrou " +  column_vbox.getChildren().contains(events.get(j).GetPanelHBox()));
            column_vbox.getChildren().remove(events.get(j).GetPanelHBox());
        }
        events.clear();
    }

    public void DeleteEvent(int eventId, String timetableDBID){
        TimeEventPanel panel = GetTimeEventPanelOfId(eventId);
        column_vbox.getChildren().remove(panel.GetPanelHBox());
        events.remove(panel);
        DatabaseManager.RemoveTimetableEvent(timetableDBID, eventId);
        System.out.println("Removed panel");
    }

    public TimeEventPanel GetTimeEventPanelOfId(int id){
        for(int j = 0;j< events.size();j++){
            if(events.get(j).event.id == id) return events.get(j);
        }
        return null;
    }

    public void AddNewEvent(TimeEventPanel eventPanel, boolean updateUI){
        events.add(eventPanel);
        if(updateUI)
            UpdateColumnUI();
    }

    public void AddStaticNode(Node node){
        staticNodes.add(node);
        UpdateColumnUI();
    }

    public void OrderColumnEvents(){
        do {
            for (int x = 0; x < events.size() - 1; x++) {
                TimeEventPanel eventPanelX = events.get(x);
                TimeEventPanel eventPanelY = events.get(x + 1);
                if (!Instant.IsSoonerThan(eventPanelX.event.start, eventPanelY.event.start)) {
                    events.set(x, eventPanelY);
                    events.set(x + 1, eventPanelX);
                }
            }
        }while(!IsColumnOrdered());
    }

    private boolean IsColumnOrdered(){
        for(int x = 0;x< events.size()-1;x++) {
            TimeEventPanel eventPanelX = events.get(x);
            TimeEventPanel eventPanelY = events.get(x + 1);
            if(!Instant.IsSoonerThan(eventPanelX.event.start, eventPanelY.event.start)) return false;
        }
        return true;
    }
}
