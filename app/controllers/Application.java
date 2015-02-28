package controllers;

import com.avaje.ebean.Ebean;
import models.Game;
import models.Vote;
import play.data.Form;
import play.libs.Scala;
import play.mvc.*;

import views.html.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application extends Controller {

    public static Result index() {
        // query the DB for all games, then split them into owned/not owned
        List<Game> allGames = Game.find.all();
        List<Game> owned = allGames.stream().filter(g -> g.owned).collect(Collectors.toList());
        List<Game> notOwned = allGames.stream().filter(g -> !g.owned).collect(Collectors.toList());

        // Note: I would typically do vote count logic on the DB but I'm not sure how with eBean

        List<Vote> votes = Vote.find.all();

        // generate a map of game ids to votes for that game
        Map<Integer, Integer> votesPerGame = new HashMap<>();
        for(Game g : notOwned) {
            long numVotes = votes.stream().filter(v -> v.game.id.equals(g.id)).count();
            votesPerGame.put(g.id, (int)numVotes);
        }

        // sort by votes descending
        notOwned = notOwned.stream()
                .sorted((g1, g2) -> Integer.compare(votesPerGame.get(g2.id), votesPerGame.get(g1.id)))
                .collect(Collectors.toList());

        return ok(index.render(
                Scala.toSeq(owned),
                Scala.toSeq(notOwned),
                votesPerGame
                ));
    }

    public static Result vote() {
        Form<VoteData> voteForm = Form.form(VoteData.class).bindFromRequest();

        if(voteForm.hasErrors()) {
            return badRequest();
        } else {
            VoteData voteData = voteForm.get();
            Game g = new Game();

            if(null != voteData.newGameTitle && !voteData.newGameTitle.isEmpty()) {

                // check for duplicates (case insensitive)
                if(Game.find.where().ilike("title", voteData.newGameTitle).findRowCount() > 0) {
                    return badRequest(voteData.newGameTitle + " already exists");
                }

                g.title = voteData.newGameTitle;
                Ebean.save(g);
            } else {
                g = Game.find.byId(voteData.gameId);
            }

            Vote v = new Vote();
            v.game = g;

            Ebean.save(v);

            return ok(voted.render(g));
        }
    }

    public static Result gamesNotOwned() {
        List<Game> notOwned = Game.find.all().stream()
                .filter(g -> !g.owned)
                .collect(Collectors.toList());

        return ok(gamesNotOwned.render(
                Scala.toSeq(notOwned)
        ));
    }

    public static Result acquireGame() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] gameIds = map.get("gameId");

        for(String gameId : gameIds) {
            Game g = Game.find.byId(Integer.parseInt(gameId));
            g.owned = true;
            Ebean.save(g);
        }

        return Results.redirect("/");
    }
}
