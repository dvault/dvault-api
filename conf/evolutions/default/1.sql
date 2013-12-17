# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "users" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,"email" VARCHAR(254) NOT NULL,"admin" INTEGER NOT NULL);

# --- !Downs

drop table "users";

