package DTO;

import java.util.Date;

public class NoteDto implements Dto{
    public final String data;
    public final String _id;
    public final Date date;

    public NoteDto(String _id, Date date, String data){
        this._id = _id;
        this.date = date;
        this.data = data;
    }
}
