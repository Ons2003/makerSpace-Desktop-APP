package com.example.testing;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimeEventPanel {
    public interface ITimeEventListener{
        public void DisplayTimeEventData(TimeEvent timeEvent);
        public void HideTimeEventData();
        public void StartEditingTimeEventData(TimeEvent timeEvent);
    }

    public TimeEvent event;
    private ITimeEventListener listener;
    public void SetListener(ITimeEventListener listener){ this.listener = listener; }

    ///region USER INTERFACE
    private HBox panel_hbox;
    public HBox GetPanelHBox(){return panel_hbox;}
    private Label name_label;
    private Label startTime_label;
    private Label endTime_label;
    ///endregion

    public TimeEventPanel(TimeEvent timeEvent){
        event = timeEvent;

        //UI
        panel_hbox = new HBox();
        panel_hbox.setAlignment(Pos.CENTER);

        name_label = new Label(event.name);
        name_label.setPadding(new Insets(15,7, 15, 7));
        name_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        name_label.setTextFill(HelloApplication.white);
        name_label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                listener.DisplayTimeEventData(event);
            }
        });
        name_label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                listener.HideTimeEventData();
            }
        });
        name_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                listener.StartEditingTimeEventData(event);
            }
        });

        startTime_label = new Label(event.start.toString());
        startTime_label.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        startTime_label.setTextFill(HelloApplication.green);

        endTime_label = new Label(event.end.toString());
        endTime_label.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        endTime_label.setTextFill(HelloApplication.green);

        panel_hbox.getChildren().add(startTime_label);
        panel_hbox.getChildren().add(name_label);
        panel_hbox.getChildren().add(endTime_label);
    }

}
