package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Vote extends Model {

    @Id
    public Integer id;

    @ManyToOne
    public Game game;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date created = new Date();

    public static Finder<Integer, Vote> find = new Finder<>(Integer.class, Vote.class);

}
