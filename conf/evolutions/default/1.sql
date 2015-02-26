# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  id                        integer not null,
  title                     varchar(255),
  created                   timestamp,
  owned                     boolean,
  constraint pk_game primary key (id))
;

create table vote (
  id                        integer not null,
  game_id                   integer,
  created                   timestamp,
  constraint pk_vote primary key (id))
;

create sequence game_seq;

create sequence vote_seq;

alter table vote add constraint fk_vote_game_1 foreign key (game_id) references game (id);
create index ix_vote_game_1 on vote (game_id);



# --- !Downs

drop table if exists game cascade;

drop table if exists vote cascade;

drop sequence if exists game_seq;

drop sequence if exists vote_seq;

