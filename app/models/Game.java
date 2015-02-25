package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game extends Model {

    @Id
    public int id;

    public String title;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date created = new Date();

    public boolean owned = false;

    public static Finder<Long, Game> find = new Finder<>(Long.class, Game.class);
}
