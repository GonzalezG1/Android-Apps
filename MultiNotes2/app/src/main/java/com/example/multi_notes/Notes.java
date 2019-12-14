package com.example.multi_notes;
//everything worked until I added serializable
//new import and implements
import java.io.Serializable;

public class Notes implements Serializable {
    private String title;
    private String WrittenNotes;

    private static int ctr = 1;

    public Notes() {
        this.title = "Title Name " + ctr;
        // this.empId = System.currentTimeMillis();
        this.WrittenNotes = "Written Notes " + ctr;
        ctr++;
    }
public Notes (String t, String n)
{
    this.title=t;
    this.WrittenNotes=n;
}

public void setTitle(String title) { this.title=title;}
public void setWrittenNotes(String WrittenNotes) { this.WrittenNotes=WrittenNotes;}

    public String getTitle() {
        return title;
    }


    public String getWrittenNotes() {
        return WrittenNotes;
    }

    @Override
    public String toString() {
        return title + WrittenNotes;
    }
}
