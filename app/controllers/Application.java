package controllers;

import com.avaje.ebean.Ebean;
import models.Game;
import models.Vote;
import play.data.Form;
import play.db.*;
import play.libs.Scala;
import play.mvc.*;

import views.html.*;

import javax.sql.DataSource;
import java.sql.Connection;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(Scala.toSeq(Game.find.all())));
    }

    public static Result vote() {
        Form<VoteData> voteForm = Form.form(VoteData.class).bindFromRequest();

        if(voteForm.hasErrors()) {
            return badRequest();
        } else {
            VoteData voteData = voteForm.get();
            Game g = new Game();

            if(null != voteData.newGameTitle) {
                //TODO: check if game already exists in DB... maybe on frontend?
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
}
