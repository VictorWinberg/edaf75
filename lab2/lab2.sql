-- SQL script to create the tables necessary for lab 2 in EDAF75.
-- MySQL version.
--
-- Creates the tables TODO and
-- populates them with (simulated) data.
--
-- We disable foreign key checks temporarily so we can delete the
-- tables in arbitrary order, and so insertion is faster.

PRAGMA foreign_keys = off;

-- Drop the tables if they already exist.

DROP TABLE IF EXISTS movies;
CREATE TABLE movies (
  title         TEXT NOT NULL,
  PRIMARY KEY   (title)
);

DROP TABLE IF EXISTS theaters;
CREATE TABLE theaters (
  name          TEXT NOT NULL,
  seats         INTEGER NOT NULL CHECK (seats > 0),
  PRIMARY KEY   (name)
);

-- We will do a lot of inserts, so we start a transaction to make it faster.

BEGIN TRANSACTION;

-- Populate the movies table.

INSERT
INTO   movies (title)
VALUES ('The Shawshank Redemption'),
       ('The Godfather'),
       ('The Dark Knight'),
       ('The Godfather: Part II'),
       ('Pulp Fiction'),
       ('The Lord of the Rings: The Return of the King'),
       ('Schindlers List'),
       ('12 Angry Men'),
       ('The Good, the Bad and the Ugly'),
       ('The Lord of the Rings: The Fellowship of the Ring'),
       ('Inception'),
       ('Forrest Gump'),
       ('Fight Club'),
       ('Star Wars: Episode V - The Empire Strikes Back'),
       ('Coco'),
       ('Goodfellas'),
       ('Star Wars: Episode IV - A New Hope'),
       ('The Lord of the Rings: The Two Towers'),
       ('The Matrix'),
       ('One Flew Over the Cuckoos Nest'),
       ('Seven Samurai'),
       ('Léon: The Professional'),
       ('Interstellar'),
       ('Saving Private Ryan'),
       ('The Silence of the Lambs'),
       ('The Usual Suspects'),
       ('Se7en'),
       ('City of God'),
       ('Spirited Away'),
       ('Life Is Beautiful'),
       ('The Intouchables'),
       ('Its a Wonderful Life'),
       ('Dangal'),
       ('City Lights'),
       ('Whiplash'),
       ('The Departed'),
       ('Back to the Future'),
       ('The Prestige'),
       ('Raiders of the Lost Ark'),
       ('The Lion King'),
       ('The Green Mile'),
       ('Gladiator'),
       ('Your Name'),
       ('Apocalypse Now'),
       ('Memento'),
       ('American History X'),
       ('Alien'),
       ('Psycho'),
       ('The Pianist'),
       ('Terminator 2'),
       ('The Lives of Other'),
       ('Once Upon a Time in the West'),
       ('Casablanca'),
       ('Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb'),
       ('Sunset Boulevard'),
       ('Rear Window'),
       ('Grave of the Fireflies'),
       ('Cinema Paradiso'),
       ('Like Stars on Earth'),
       ('The Great Dictator'),
       ('Modern Times'),
       ('My Father and My Son'),
       ('Django Unchained'),
       ('The Dark Knight Rises'),
       ('American Beauty'),
       ('The Shining'),
       ('Braveheart'),
       ('Aliens'),
       ('Once Upon a Time in America'),
       ('Old Boy - Hämnden'),
       ('WALL·E'),
       ('3 Idiots'),
       ('Vertigo'),
       ('Citizen Kane'),
       ('Princess Mononoke'),
       ('Das Boot'),
       ('North by Northwest'),
       ('A Separation'),
       ('M'),
       ('Paths of Glory'),
       ('Children of Heaven'),
       ('Witness for the Prosecution'),
       ('The Bandit'),
       ('Three Billboards Outside Ebbing, Missouri'),
       ('Call Me by Your Name'),
       ('Inglourious Basterds'),
       ('Indiana Jones and the Last Crusade'),
       ('Batman Begins'),
       ('Requiem for a Dream'),
       ('Good Will Hunting'),
       ('Taxi Driver'),
       ('A Clockwork Orange'),
       ('Snatch'),
       ('Scarface'),
       ('Full Metal Jacket'),
       ('Reservoir Dogs'),
       ('Star Wars: Episode VI - Return of the Jedi'),
       ('Eternal Sunshine of the Spotless Mind'),
       ('2001: A Space Odyssey'),
       ('Toy Story');

-- Populate the theaters table.

INSERT
INTO   theaters (name, seats)
VALUES ('AMC', 600),
       ('Castello Lopes', 250),
       ('Cinamon', 250),
       ('Cinemark', 250),
       ('Cinema City International', 800),
       ('Cineplex', 250),
       ('Cecchi Gori Group', 250),
       ('CinemaxX', 250),
       ('CineStar', 250),
       ('Cineworld', 250),
       ('Curzon', 250),
       ('Empire', 1000),
       ('EuroPalaces', 250),
       ('Euroscoop', 250),
       ('Gaumont', 1200),
       ('Hollywood', 2000),
       ('Kinepolis', 250),
       ('Merlin', 250),
       ('Multikino', 250),
       ('Multiplex Cinemas (Ukraine)', 100),
       ('National Amusements', 250),
       ('Odessa Kino', 250),
       ('Odeon', 250),
       ('Omniplex', 250),
       ('Palace', 150),
       ('Parkway Cinemas', 250),
       ('Picturedrome Electric Theatre Co Ltd (Picturedrome Cinemas)', 250),
       ('Picturehouse', 250),
       ('Pathé', 250),
       ('Reel', 250),
       ('S & B Cinemas', 250),
       ('Scott', 250),
       ('Showcase', 250),
       ('The Space Cinema', 250),
       ('SF Bio', 500),
       ('UCI (United Cinemas International)', 600),
       ('UGC', 250),
       ('Utopolis', 1500),
       ('Vue', 250),
       ('Village', 250),
       ('Ward Anderson', 2500),
       ('Yelmo Cines', 250),
       ('Cinemas NOS', 250),
       ('UCI Cinemas', 250),
       ('Finnkino', 50),
       ('Nordisk Film Biografer', 300);

-- Commit the transaction.

END TRANSACTION;

-- And re-enable foreign key checks.

PRAGMA foreign_key = on;

