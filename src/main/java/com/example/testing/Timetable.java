package com.example.testing;

import java.sql.SQLException;
import java.util.List;

public class Timetable {
    public int id;
    public String title;
    public String dataBaseID;
    public TimetableColumn[] columns;

    public Timetable(String title, String dataBaseID){
        this.title = title;
        this.dataBaseID = dataBaseID;
    }

    public static TimetableColumn[] GetEmptyWeekColumns(){
        TimetableColumn[] columns = new TimetableColumn[7];
        for(int i = 0;i<columns.length;i++){
            columns[i] = new TimetableColumn(i);
        }
        columns[0].title = "Monday";
        columns[1].title = "Tuesday";
        columns[2].title = "Wednesday";
        columns[3].title = "Thursday";
        columns[4].title = "Friday";
        columns[5].title = "Saturday";
        columns[6].title = "Sunday";
        return columns;
    }

    public TimeEventPanel AddTimeEvent(TimeEvent timeEvent, TimeEventPanel.ITimeEventListener listener){
        TimeEventPanel timeEventPanel = new TimeEventPanel(timeEvent);
        timeEventPanel.SetListener(listener);
        columns[timeEvent.columnID].AddNewEvent(timeEventPanel, false);
        columns[timeEvent.columnID].OrderColumnEvents();
        columns[timeEvent.columnID].UpdateColumnUI();
        return timeEventPanel;
    }

    public TimeEventPanel AddNewTimeEvent(int column, String name, Instant startTime, Instant endTime, String topic,
                                          String description, TimeEventPanel.ITimeEventListener listener,
                                          AccountsManager.AccessPermission accessPermission) {
        TimeEvent timeEvent = new TimeEvent(column, name, startTime, endTime, topic, description, Instant.GetCurrentTimeHM(),
                AccountsManager.loggedAccount.SID, accessPermission);

        System.out.println(AccountsManager.loggedAccount.SID + " Created a new event");
        System.out.println(timeEvent.creation.creator.SID + " is what is registered");

        DatabaseManager.SaveTimetableEvent(timeEvent, this);
        return AddTimeEvent(timeEvent, listener);
    }

    public TimeEventPanel GetTimeEventPanelOfId(int id){
        TimeEventPanel panel = null;
        for(int i = 0;i<columns.length;i++){
            panel = columns[i].GetTimeEventPanelOfId(id);
            if(panel != null) return panel;
        }
        return null;
    }

    public void RemoveEvent(int eventId){
         columns[GetTimeEventPanelOfId(eventId).event.columnID].DeleteEvent(eventId, dataBaseID);
    }

    public  void ClearEvents(){
        for(int i = 0;i<columns.length;i++){
            columns[i].ClearEvents();
        }
    }

    public List<TimeEvent> RetrieveEventsFromDatabase(TimeEventPanel.ITimeEventListener listener){
        ClearEvents();
        try {
            List<TimeEvent> events = DatabaseManager.RetrieveTimetableEvents(this);
            for(int i = 0;i<events.size();i++) {
                if (AccountsManager.HasAccess(events.get(i).creation.accessPermission, events.get(i).creation.creator.SID)) {
                    AddTimeEvent(events.get(i), listener);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
