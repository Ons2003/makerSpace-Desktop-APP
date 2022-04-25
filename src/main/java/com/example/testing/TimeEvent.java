package com.example.testing;

public class TimeEvent {
    public int id;
    public CreationInfo creation;
    public int columnID;
    public String name;
    public Instant start;
    public Instant end;
    public Instant creationDate;
    public String description;
    public String topic;

    public TimeEvent(int columnID, String name, Instant start, Instant end, String topic, String description, Instant creationDate,
                     String creatorSID, AccountsManager.AccessPermission accessPermission){
        this.columnID = columnID;
        this.name = name;
        this.start = new Instant(start);
        this.end = new Instant(end);
        this.topic = topic;
        this.creationDate = creationDate;
        this.description = description;
        System.out.println("i am and me only " + creatorSID);
        creation = new CreationInfo(creatorSID, accessPermission);
    }
}
