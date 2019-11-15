package DAO;

import DTO.NoteDto;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class MongoDao {
    private static MongoDao mongoDaoObject;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> notesCollection;


    public static MongoDao getInstance() {
        if (mongoDaoObject == null) {
            mongoDaoObject = new MongoDao();
        }
        return mongoDaoObject;
    }

    private MongoDao() {
        // Open connection
        mongoClient = new MongoClient("localhost", 27017);
        // Get ref to database
        db = mongoClient.getDatabase("HomeworkTwoDatabase");
        // Get ref to collection
        notesCollection = db.getCollection("NotesCollection");
    }

    public String storeNote(Date date, String data) {
        Document note = new Document("date", date)
                .append("data", data);
        notesCollection.insertOne(note);
        String _id = note.get("_id").toString();
        return _id;
    }

    public ArrayList<NoteDto> getAllNotes() {
        Gson gson = new Gson();
        ArrayList<NoteDto> notesList = new ArrayList<>();
        MongoCursor<Document> cursor = notesCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document tempDoc = cursor.next();
                NoteDto tempNote = new NoteDto(tempDoc.get("_id").toString(),
                        (Date)tempDoc.get("date"),
                        tempDoc.get("data").toString());

                notesList.add(tempNote);
            }
        } finally {
            cursor.close();
        }
        return notesList;
    }

    public NoteDto deleteNoteById(String id) {
        try {
            ObjectId idToSearch = new ObjectId(id);
            Document deletedDocument = notesCollection.findOneAndDelete(eq("_id", idToSearch));
            if (deletedDocument != null) {
                NoteDto deletedNote = new NoteDto(deletedDocument.get("_id").toString(), (Date)deletedDocument.get("date"),
                        deletedDocument.get("data").toString());
                return deletedNote;
            }
            return new NoteDto(null, null, null);
        }
        catch(Exception e) {
            return new NoteDto(null, null, null);
        }

    }

    public NoteDto getNoteById(String id) {
        try {
            ObjectId idToSearch = new ObjectId(id);
            Document foundDocument = notesCollection.find(eq("_id", idToSearch)).first();
            if (foundDocument != null) {
                NoteDto foundNote = new NoteDto(foundDocument.get("_id").toString(), (Date) foundDocument.get("date"),
                        foundDocument.get("data").toString());
                return foundNote;
            }
            return new NoteDto(null, null, null);
        }
        catch(Exception e) {
            return new NoteDto(null, null, null);
        }
    }

    public NoteDto updateNoteById(String id, String data) {
        try {
            ObjectId idToSearch = new ObjectId(id);

            Document docToUpdate = notesCollection.find(eq("_id", idToSearch)).first();
            if (docToUpdate == null) {
                return new NoteDto(null, null, null);
            } else {
                notesCollection.updateOne(eq("_id", idToSearch),
                        new Document("$set", new Document("data", data)));

                return new NoteDto(id, new Date(), data);
            }
        }
        catch(Exception e) {
            return new NoteDto(null, null, null);
        }
    }
}
