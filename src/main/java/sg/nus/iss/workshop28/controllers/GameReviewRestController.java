package sg.nus.iss.workshop28.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.iss.workshop28.models.Comment;
import sg.nus.iss.workshop28.models.CommentResult;
import sg.nus.iss.workshop28.models.Game;
import sg.nus.iss.workshop28.services.ReviewService;

@RestController
@RequestMapping(path = "/game")
public class GameReviewRestController {
    @Autowired
    private ReviewService reviewSvc;

    @GetMapping("{gameId}/reviews") //get reviews based on game id
    public ResponseEntity<String> getReviewHistory(@PathVariable String gameId) {
        JsonObject result = null;
        Optional<Game> r = reviewSvc.aggregateGame(gameId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.get().toJSON());
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/highest")//obtain based on highest rating
    public ResponseEntity<String> getHighestRatedGames(@RequestParam String username,
            @RequestParam Integer limit) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getResultForCommentResult(limit, username, "highest", 6).toString());
    }

    @GetMapping("/lowest") //obtain data based on the lowest rating
    public ResponseEntity<String> getLowestRatedGames(@RequestParam String username,
            @RequestParam Integer limit) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getResultForCommentResult(limit, username, "lowest", 5).toString());
    }


    private JsonObject getResultForCommentResult(Integer limit, String username,
            String rating, Integer ratingInt) {
        JsonObject result = null;
        List<Comment> r = reviewSvc.aggregateGamesComment(limit, username, ratingInt);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        CommentResult cr = new CommentResult();
        cr.setRating(rating);
        cr.setGames(r);
        cr.setTimestamp(LocalDateTime.now().toString());
        builder.add("workshop28b", cr.toJSON());

        result = builder.build();
        return result;
    }
}

