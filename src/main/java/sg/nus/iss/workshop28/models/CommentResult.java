package sg.nus.iss.workshop28.models;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CommentResult {
    private String rating;
    private String timestamp;
    private List<Comment> games;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getGames() {
        return games;
    }

    public void setGames(List<Comment> games) {
        this.games = games;
    }

    public static CommentResult create(Document d) {
        CommentResult c = new CommentResult();
        // c.setRating(d.getString("rating"));
        c.setTimestamp(LocalDateTime.now().toString());

        return c;
    }

    public JsonObject toJSON() {

        return Json.createObjectBuilder()
                .add("rating", getRating())
                .add("games", getGames().toString())
                .add("timestamp", getTimestamp())
                .build();
    }
}