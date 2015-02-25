package controllers;

import models.Game;
import play.*;
import play.db.*;
import play.libs.Scala;
import play.mvc.*;

import views.html.*;

import javax.sql.DataSource;
import java.sql.Connection;

public class Application extends Controller {

    public static Result index() {
        DataSource dataSource = DB.getDataSource();
        Connection conn = DB.getConnection();

        return ok(index.render(Scala.toSeq(Game.find.all())));
    }

}
