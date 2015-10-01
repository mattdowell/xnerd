DROP DATABASE IF EXISTS `xnerd`;
create database `xnerd`;
use `xnerd`;


CREATE TABLE `games` (
  `game_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `owned` boolean default false,
  `create_ts` timestamp NOT NULL default current_timestamp,
  PRIMARY KEY  (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `votes` (
  `game_id` int(11) NOT NULL,
  `create_ts` timestamp NOT NULL default current_timestamp,
  CONSTRAINT `votes_fk` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

use xnerd;

delete from games;
delete from votes;

-- Test data
insert into games (name, owned) values ('Leisure Suit Larry', true);
insert into games (name, owned) values ('Halo 2', true);
insert into games (name, owned) values ('Halo', false);
insert into games (name, owned) values ('Grand Theft Auto', true);
insert into games (name, owned) values ('Halo 3', true);
insert into games (name, owned) values ('Star Wars Battlefront II', false);
insert into games (name, owned) values ('Doom 3', false);
insert into games (name, owned) values ('E.T. The Game', true);
insert into games (name, owned) values ('1984', true);
insert into games (name, owned) values ('Galactica', false);
insert into games (name, owned) values ('Galaga', true);
insert into games (name, owned) values ('Space Invaders', true);
insert into games (name, owned) values ('Space Wars', true);
insert into games (name, owned) values ('Pac Man', false);
insert into games (name, owned) values ('Ms. Pac Man', true);

-- Votes test data, this script will have to be updated with the proper Game_id
insert into votes (game_id) values (1);
insert into votes (game_id) values (4);
insert into votes (game_id) values (1);
insert into votes (game_id) values (1);
insert into votes (game_id) values (2);
insert into votes (game_id) values (3);
insert into votes (game_id) values (5);
insert into votes (game_id) values (6);
insert into votes (game_id) values (4);
insert into votes (game_id) values (8);
insert into votes (game_id) values (1);
insert into votes (game_id) values (1);
insert into votes (game_id) values (2);
insert into votes (game_id) values (1);
insert into votes (game_id) values (8);
insert into votes (game_id) values (9);
insert into votes (game_id) values (10);
insert into votes (game_id) values (10);
insert into votes (game_id) values (10);
insert into votes (game_id) values (11);
insert into votes (game_id) values (5);
insert into votes (game_id) values (10);
insert into votes (game_id) values (5);
insert into votes (game_id) values (5);
insert into votes (game_id) values (5);
insert into votes (game_id) values (2);
insert into votes (game_id) values (3);
insert into votes (game_id) values (14);
insert into votes (game_id) values (12);
insert into votes (game_id) values (5);
insert into votes (game_id) values (5);

