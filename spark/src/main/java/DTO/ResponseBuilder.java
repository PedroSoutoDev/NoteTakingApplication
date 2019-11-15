package DTO;

import java.util.Date;

public class ResponseBuilder {
    private Date date;
    private String _id;
    private String responseCode;
    private Dto[] response;

    public ResponseDto build(){
        ResponseDto responseToBuild = new ResponseDto(date, _id, responseCode, response);
        return responseToBuild;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponse(Dto[] response) {
        this.response = response;
    }
}
