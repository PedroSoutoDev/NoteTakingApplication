import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import DAO.MongoDao;
import DTO.Dto;
import DTO.NoteDto;
import DTO.ResponseBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

public class SparkDemo {

  public static String processRoute(Request req, Response res) {
    Set<String> params = req.queryParams();
    for (String param : params) {
      System.out.println(param + " : " + req.queryParamsValues(param)[0]);
    }
    // Print the id query value
    System.out.println(req.queryMap().get("id").value());
    return "done!";
  }

  public static void main(String[] args) {
    port(1234);

    // Stores notes in the database
    post("/store", SparkDemo::storeNote);

    // Gets a list of all your notes
    get("/list", (req, res) -> {
      if (req.queryParams().size() == 0) {
        ArrayList<NoteDto> allNotes = MongoDao.getInstance().getAllNotes();
        ResponseBuilder rb = new ResponseBuilder();

        rb.setDate(new Date());
        rb.setResponseCode("OK");
        rb.setResponse(allNotes.toArray(new Dto[allNotes.size()]));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(rb.build());
      } else {
        return badRequestResponse();
      }
    });

    // Delete a note if it matches that id
    post("/delete", SparkDemo::deleteById);

    //Get note with specific id
    get("/get", SparkDemo::getById);

    // Update note with a matching ID
    post("/update", (req, res) -> {
      if (req.queryParams().size() == 1 && req.queryMap("_id").value() != null) {
        String id = req.queryMap("_id").value();
        String data =  req.body();
        NoteDto updatedNote = MongoDao.getInstance().updateNoteById(id, req.body());

        ResponseBuilder rb = new ResponseBuilder();
        rb.setDate(new Date());
        rb.setResponseCode("OK");
        rb.set_id(id);

        if (updatedNote._id != null) {
          Dto[] responseArray = {updatedNote};
          rb.setResponse(responseArray);
        } else {
          rb.setResponse(null);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(rb.build());
      } else {
        return badRequestResponse();
      }
    });

    get("*", (req, res) -> badRequestResponse());
  }

  private static String badRequestResponse() {
    ResponseBuilder rb = new ResponseBuilder();
    rb.set_id(null);
    rb.setResponse(null);
    rb.setResponseCode("ERROR");
    rb.setDate(new Date());

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(rb.build());
  }

  private static String storeNote(Request req, Response res) {
    if (req.queryParams().size() == 0) {
      Date date = new Date();
      String data = req.body();
      String addedNoteId = MongoDao.getInstance().storeNote(date, data);
      ResponseBuilder rb = new ResponseBuilder();

      rb.setDate(date);
      rb.set_id(addedNoteId);
      rb.setResponseCode("OK");
      Dto[] responseArray = {new NoteDto(addedNoteId, date, data)};
      rb.setResponse(responseArray);

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(rb.build());
    } else {
      return badRequestResponse();
    }
  }

  private static String deleteById(Request req, Response res){
    if (req.queryParams().size() == 1 && req.queryMap("_id").value() != null) {
      String id = req.queryMap("_id").value();
      NoteDto deletedNote = MongoDao.getInstance().deleteNoteById(id);
      ResponseBuilder rb = new ResponseBuilder();

      rb.setDate(new Date());
      rb.setResponseCode("OK");
      rb.set_id(id);

      if (deletedNote._id != null) {
        Dto[] responseArray = {deletedNote};
        rb.setResponse(responseArray);
      } else {
        rb.setResponse(null);
      }
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(rb.build());
    } else {
      return badRequestResponse();
    }
  }

  private static String getById(Request req, Response res){
    if (req.queryParams().size() == 1 && req.queryMap("_id").value() != null) {
      String id = req.queryMap("_id").value();
      NoteDto getNote = MongoDao.getInstance().getNoteById(id);
      ResponseBuilder rb = new ResponseBuilder();

      rb.setDate(new Date());
      rb.setResponseCode("OK");
      rb.set_id(id);

      if (getNote._id != null) {
        Dto[] responseArray = {getNote};
        rb.setResponse(responseArray);
      } else {
        rb.setResponse(null);
      }
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(rb.build());
    } else {
      return badRequestResponse();
    }
  }
}
