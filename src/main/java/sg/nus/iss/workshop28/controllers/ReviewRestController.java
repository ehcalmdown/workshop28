package sg.nus.iss.workshop28.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.iss.workshop28.models.EditedComment;
import sg.nus.iss.workshop28.models.Review;
import sg.nus.iss.workshop28.services.ReviewService;

@RestController
@RequestMapping(path = "/api/review")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewSvc;

    @PutMapping(path = "{_id}")//create PUT request to enable updating of info
    public ResponseEntity<String> updateEdits(@PathVariable String _id, @RequestBody EditedComment json) {
        JsonObject result = null;
        Review r = reviewSvc.updateEdits(_id, json);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.toJSON(false));
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("{reviewId}") //obtain the review based on the id
    public ResponseEntity<String> getReviewById(@PathVariable String reviewId) {
        JsonObject result = null;
        Review r = reviewSvc.getReview(reviewId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.toJSON(true));
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("{reviewId}/history") //obtain review history after obtaining review based on id
    public ResponseEntity<String> getReviewHistory(@PathVariable String reviewId) {
        JsonObject result = null;
        Review r = reviewSvc.getReview(reviewId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.toJSON(false));
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

}
