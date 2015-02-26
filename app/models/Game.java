package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game extends Model {

    @Id
    public Integer id;

    public String title;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date created = new Date();

    public boolean owned = false;

    public static Finder<Integer, Game> find = new Finder<>(Integer.class, Game.class);
}
