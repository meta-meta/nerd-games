package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/* Data model for Vote. Play automagically creates a table based on this bean */
@Entity
public class Vote extends Model {

    @Id
    public Integer id;

    @ManyToOne // this annotation creates a foreign key constraint: many votes to 1 game
    public Game game;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date created = new Date();

    public static Finder<Integer, Vote> find = new Finder<>(Integer.class, Vote.class);

}
