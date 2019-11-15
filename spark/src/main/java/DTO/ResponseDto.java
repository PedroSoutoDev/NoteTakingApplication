package DTO;

import java.util.Date;

public class ResponseDto implements Dto{
    public final Date date;
    public final String _id;
    public final String responseCode;
    public final Dto[] response;

    ResponseDto(Date date, String _id, String responseCode, Dto[] response) {
        this.date = date;
        this._id = _id;
        this.responseCode = responseCode;
        this.response = response;
    }
}
