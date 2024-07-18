drop database if exists progress_tracker;
create database progress_tracker;
use progress_tracker;

create table users (
	user_id int unsigned primary key auto_increment,
    username varchar(25) not null unique,
    password varchar(255) not null
);

CREATE TABLE topics (
    topic_id INT unsigned PRIMARY KEY AUTO_INCREMENT,
    topic_name VARCHAR(20) NOT NULL unique
);

create table entries (
	entry_id int unsigned primary key auto_increment,
    topic_id int unsigned,
    name varchar(100) not null unique,
    description varchar(1000),
    foreign key (topic_id) references topics(topic_id)
);

create table user_entries (
	user_id int unsigned,
    entry_id int unsigned,
    progress tinyint,
    primary key (user_id, entry_id),
    foreign key (user_id) references users(user_id),
    foreign key (entry_id) references entries(entry_id),
    CHECK (progress BETWEEN -1 AND 1)
);

insert into users (username, password) values("Rita", "rita1234");
insert into users (username, password) values("Rus", "rus12345");
insert into users (username, password) values("Jack", "jack1234");
INSERT INTO users (username, password) VALUES ("Alice", "alice1234");
INSERT INTO users (username, password) VALUES ("Bob", "bob12345");
INSERT INTO users (username, password) VALUES ("Charlie", "charlie1234");
INSERT INTO users (username, password) VALUES ("Diana", "diana1234");
INSERT INTO users (username, password) VALUES ("Eve", "eve12345");
INSERT INTO users (username, password) VALUES ("Frank", "frank1234");
INSERT INTO users (username, password) VALUES ("Grace", "grace1234");
INSERT INTO users (username, password) VALUES ("Hank", "hank12345");
INSERT INTO users (username, password) VALUES ("Ivy", "ivy1234");
INSERT INTO users (username, password) VALUES ("John", "john1234");
INSERT INTO users (username, password) VALUES ("Karen", "karen12345");
INSERT INTO users (username, password) VALUES ("Leo", "leo1234");
INSERT INTO users (username, password) VALUES ("Mona", "mona1234");
INSERT INTO users (username, password) VALUES ("Nina", "nina12345");
INSERT INTO users (username, password) VALUES ("Oscar", "oscar1234");
INSERT INTO users (username, password) VALUES ("Paul", "paul1234");
INSERT INTO users (username, password) VALUES ("Quinn", "quinn12345");
INSERT INTO users (username, password) VALUES ("Rachel", "rachel1234");
INSERT INTO users (username, password) VALUES ("Sam", "sam1234");
INSERT INTO users (username, password) VALUES ("Tina", "tina12345");


insert into topics values(null, "TV Shows");
insert into topics values(null, "Books");
insert into topics values(null, "Music");
INSERT INTO topics VALUES (NULL, "Movies");
INSERT INTO topics VALUES (NULL, "Podcasts");
INSERT INTO topics VALUES (NULL, "Games");


insert into entries (topic_id, name, description) values(1, "Game of Thrones", "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.");
insert into entries (topic_id, name, description) values(1, "The Sopranos", "New Jersey mob boss Tony Soprano deals with personal and professional issues in his home and business life that affect his mental state, leading him to seek professional psychiatric counseling.");
insert into entries (topic_id, name, description) values(1, "Breaking Bad", "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.");
insert into entries (topic_id, name, description) values(3, "Bohemian Rhapsody", "A song by the British rock band Queen, written by Freddie Mercury.");
insert into entries (topic_id, name, description) values(3, "Imagine", "A song by John Lennon, envisioning a world of peace and unity.");
insert into entries (topic_id, name, description) values(3, "Stairway to Heaven", "A song by Led Zeppelin, considered one of the greatest rock songs of all time.");
insert into entries (topic_id, name, description) values(3, "Hotel California", "A song by the Eagles, known for its iconic guitar solo.");
insert into entries (topic_id, name, description) values(3, "Hey Jude", "A song by The Beatles, written by Paul McCartney.");
insert into entries (topic_id, name, description) values(3, "Smells Like Teen Spirit", "A song by Nirvana, considered an anthem of the 1990s.");
insert into entries (topic_id, name, description) values(3, "Billie Jean", "A song by Michael Jackson, known for its distinctive bass line.");
insert into entries (topic_id, name, description) values(2, "To Kill a Mockingbird by Harper Lee", "Narrated by 10-year-old Scout Finch, To Kill a Mockingbird centers on her father, small-town lawyer Atticus Finch, and his fight against racial prejudice in the 1930s Deep South. You may have read this beloved coming-of-age story when you were in school, but it’s worth revisiting this classic as an adult. Poignant and powerful, the central themes remain timeless and timely.");
insert into entries (topic_id, name, description) values(2, "Pride and Prejudice by Jane Austen", "Often imitated but never duplicated, Jane Austen’s Pride and Prejudice is a British classic and one of the most beloved books of all time. Detailing the social mores of 19th-century England, Austen crafts a novel that is amusing, timeless and utterly delightful. If you haven’t met Elizabeth Bennet and Mr. Darcy yet, now is the time, for this is surely a book that everyone should read!");
insert into entries (topic_id, name, description) values(2, "1984 by George Orwell", "In 1984, George Orwell envisions a grim dystopian future where Big Brother is always watching and free thought is forbidden. For Winston, the only hope of survival is joining the revolution intent on overthrowing the Party. This chilling tale of governmental power and control will make you think about the freedoms we take for granted… and who might be watching.");
insert into entries (topic_id, name, description) values(2, "A Tree Grows in Brooklyn by Betty Smith", "A Tree Grows in Brooklyn centers on 11-year-old Francie Nolan, who lives in the Williamsburg section of Brooklyn with her Irish and Austrian immigrant parents and younger brother, Neely. When her father dies, the family reels, but Francie is determined to pursue her dreams of finishing her education and going to college. This poignant coming-of-age story is a beautiful and timeless tale of resilience and hope.");
insert into entries (topic_id, name, description) values(2, "The Book Thief by Markus Zusak", "In this #1 New York Times bestseller, it’s 1939 Nazi Germany and Liesel Meminger is a young foster girl who steals for her survival. Then she discovers books. Liesel learns to read and shares her stolen books with her neighbors and the Jewish man who hides in her basement. Markus Zusak, author of I Am the Messenger, has written a heartbreaking tale of the power and joy books offer, even in the darkest days.");
-- Insert more entries for each topic
-- Movies
INSERT INTO entries (topic_id, name, description) VALUES (4, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.");
INSERT INTO entries (topic_id, name, description) VALUES (4, "The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.");
INSERT INTO entries (topic_id, name, description) VALUES (4, "Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.");
INSERT INTO entries (topic_id, name, description) VALUES (4, "Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
INSERT INTO entries (topic_id, name, description) VALUES (4, "Forrest Gump", "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold from the perspective of an Alabama man with an IQ of 75.");

-- Podcasts
INSERT INTO entries (topic_id, name, description) VALUES (5, "Serial", "A podcast that investigates a true story over the course of a season.");
INSERT INTO entries (topic_id, name, description) VALUES (5, "This American Life", "An American weekly hour-long radio program produced in collaboration with Chicago Public Media and hosted by Ira Glass.");
INSERT INTO entries (topic_id, name, description) VALUES (5, "Radiolab", "A show about curiosity where sound illuminates ideas, and the boundaries blur between science, philosophy, and human experience.");
INSERT INTO entries (topic_id, name, description) VALUES (5, "Stuff You Should Know", "If you've ever wanted to know about champagne, satanism, the Stonewall Uprising, chaos theory, LSD, El Nino, true crime, and Rosa Parks, then look no further.");
INSERT INTO entries (topic_id, name, description) VALUES (5, "The Daily", "This is how the news should sound. The biggest stories of our time, told by the best journalists in the world. Hosted by Michael Barbaro.");

-- Games
INSERT INTO entries (topic_id, name, description) VALUES (6, "The Legend of Zelda: Breath of the Wild", "An open-world action-adventure game where players control Link, who awakens from a deep slumber to a mysterious voice that guides him to defeat Calamity Ganon.");
INSERT INTO entries (topic_id, name, description) VALUES (6, "The Witcher 3: Wild Hunt", "A story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences.");
INSERT INTO entries (topic_id, name, description) VALUES (6, "Red Dead Redemption 2", "An epic tale of life in America’s unforgiving heartland. The game's vast and atmospheric world also provides the foundation for a brand new online multiplayer experience.");
INSERT INTO entries (topic_id, name, description) VALUES (6, "Minecraft", "A game about placing blocks and going on adventures. Explore randomly generated worlds and build amazing things from the simplest of homes to the grandest of castles.");
INSERT INTO entries (topic_id, name, description) VALUES (6, "Fortnite", "A game that features Battle Royale, where 100 players fight to be the last one standing, and Creative, where players design games and battle arenas.");


insert into user_entries values(1, 1, 1);
insert into user_entries values(1, 3, -1);
insert into user_entries values(2, 3, -1);
insert into user_entries values(2, 4, 1);
insert into user_entries values(2, 7, 1);
insert into user_entries values(2, 10, 1);
insert into user_entries values(2, 13, 0);
insert into user_entries values(3, 3, 0);
-- Insert more user_entries
INSERT INTO user_entries VALUES (1, 5, 0);  -- User 1 with entry "Hey Jude"
INSERT INTO user_entries VALUES (1, 8, 1);  -- User 1 with entry "Billie Jean"
INSERT INTO user_entries VALUES (1, 11, 1); -- User 1 with entry "Pride and Prejudice by Jane Austen"
INSERT INTO user_entries VALUES (2, 2, 0);  -- User 2 with entry "The Sopranos"
INSERT INTO user_entries VALUES (2, 5, -1); -- User 2 with entry "Hey Jude"
INSERT INTO user_entries VALUES (3, 1, 1);  -- User 3 with entry "Game of Thrones"
INSERT INTO user_entries VALUES (3, 12, 1); -- User 3 with entry "1984 by George Orwell"
INSERT INTO user_entries VALUES (3, 14, 0); -- User 3 with entry "The Book Thief by Markus Zusak"
INSERT INTO user_entries VALUES (3, 17, 1); -- User 3 with entry "Pulp Fiction"
INSERT INTO user_entries VALUES (3, 21, -1);-- User 3 with entry "Stuff You Should Know"
INSERT INTO user_entries VALUES (3, 24, 0); -- User 3 with entry "Red Dead Redemption 2"
-- Assuming new users with user_ids 4 to 13
-- Adding entries for new users

-- User 4 entries
INSERT INTO user_entries VALUES (4, 2, 1);   -- "The Sopranos"
INSERT INTO user_entries VALUES (4, 5, 1);   -- "Hey Jude"
INSERT INTO user_entries VALUES (4, 12, 0);  -- "1984 by George Orwell"
INSERT INTO user_entries VALUES (4, 16, 1);  -- "The Dark Knight"
INSERT INTO user_entries VALUES (4, 20, 1);  -- "Serial"
INSERT INTO user_entries VALUES (4, 27, -1); -- "Minecraft"

-- User 5 entries
INSERT INTO user_entries VALUES (5, 3, 1);   -- "Breaking Bad"
INSERT INTO user_entries VALUES (5, 7, -1);  -- "Hotel California"
INSERT INTO user_entries VALUES (5, 11, 0);  -- "Pride and Prejudice by Jane Austen"
INSERT INTO user_entries VALUES (5, 17, 1);  -- "Pulp Fiction"
INSERT INTO user_entries VALUES (5, 21, 0);  -- "Stuff You Should Know"
INSERT INTO user_entries VALUES (5, 25, 1);  -- "The Witcher 3: Wild Hunt"

-- User 6 entries
INSERT INTO user_entries VALUES (6, 1, 1);   -- "Game of Thrones"
INSERT INTO user_entries VALUES (6, 8, 0);   -- "Billie Jean"
INSERT INTO user_entries VALUES (6, 13, 1);  -- "A Tree Grows in Brooklyn by Betty Smith"
INSERT INTO user_entries VALUES (6, 18, 0);  -- "Inception"
INSERT INTO user_entries VALUES (6, 22, 1);  -- "The Daily"
INSERT INTO user_entries VALUES (6, 24, -1); -- "Red Dead Redemption 2"

-- User 7 entries
INSERT INTO user_entries VALUES (7, 4, 1);   -- "Bohemian Rhapsody"
INSERT INTO user_entries VALUES (7, 9, 1);   -- "To Kill a Mockingbird by Harper Lee"
INSERT INTO user_entries VALUES (7, 15, 0);  -- "The Godfather"
INSERT INTO user_entries VALUES (7, 19, -1); -- "Forrest Gump"
INSERT INTO user_entries VALUES (7, 26, 1);  -- "Fortnite"
INSERT INTO user_entries VALUES (7, 27, 0);  -- "Minecraft"

-- User 8 entries
INSERT INTO user_entries VALUES (8, 6, 1);   -- "Smells Like Teen Spirit"
INSERT INTO user_entries VALUES (8, 10, 0);  -- "The Book Thief by Markus Zusak"
INSERT INTO user_entries VALUES (8, 14, 1);  -- "Pride and Prejudice by Jane Austen"
INSERT INTO user_entries VALUES (8, 18, -1); -- "Inception"
INSERT INTO user_entries VALUES (8, 20, 0);  -- "Serial"
INSERT INTO user_entries VALUES (8, 23, 1);  -- "The Legend of Zelda: Breath of the Wild"

-- User 9 entries
INSERT INTO user_entries VALUES (9, 3, 1);   -- "Breaking Bad"
INSERT INTO user_entries VALUES (9, 6, -1);  -- "Smells Like Teen Spirit"
INSERT INTO user_entries VALUES (9, 12, 0);  -- "1984 by George Orwell"
INSERT INTO user_entries VALUES (9, 15, 1);  -- "The Godfather"
INSERT INTO user_entries VALUES (9, 22, 0);  -- "The Daily"
INSERT INTO user_entries VALUES (9, 25, 1);  -- "The Witcher 3: Wild Hunt"

-- User 10 entries
INSERT INTO user_entries VALUES (10, 2, 1);  -- "The Sopranos"
INSERT INTO user_entries VALUES (10, 8, 1);  -- "Billie Jean"
INSERT INTO user_entries VALUES (10, 10, 0); -- "The Book Thief by Markus Zusak"
INSERT INTO user_entries VALUES (10, 19, -1);-- "Forrest Gump"
INSERT INTO user_entries VALUES (10, 21, 1); -- "Stuff You Should Know"
INSERT INTO user_entries VALUES (10, 23, 0); -- "The Legend of Zelda: Breath of the Wild"

-- User 11 entries
INSERT INTO user_entries VALUES (11, 4, 1);  -- "Bohemian Rhapsody"
INSERT INTO user_entries VALUES (11, 6, -1); -- "Smells Like Teen Spirit"
INSERT INTO user_entries VALUES (11, 13, 0); -- "A Tree Grows in Brooklyn by Betty Smith"
INSERT INTO user_entries VALUES (11, 17, 1); -- "Pulp Fiction"
INSERT INTO user_entries VALUES (11, 22, 0); -- "The Daily"
INSERT INTO user_entries VALUES (11, 24, 1); -- "Red Dead Redemption 2"

-- User 12 entries
INSERT INTO user_entries VALUES (12, 1, 1);  -- "Game of Thrones"
INSERT INTO user_entries VALUES (12, 5, 1);  -- "Hey Jude"
INSERT INTO user_entries VALUES (12, 12, 0); -- "1984 by George Orwell"
INSERT INTO user_entries VALUES (12, 18, 1); -- "Inception"
INSERT INTO user_entries VALUES (12, 20, 0); -- "Serial"
INSERT INTO user_entries VALUES (12, 27, -1);-- "Minecraft"

-- User 13 entries
INSERT INTO user_entries VALUES (13, 3, 1);  -- "Breaking Bad"
INSERT INTO user_entries VALUES (13, 8, 1);  -- "Billie Jean"
INSERT INTO user_entries VALUES (13, 11, 0); -- "Pride and Prejudice by Jane Austen"
INSERT INTO user_entries VALUES (13, 16, -1);-- "The Dark Knight"
INSERT INTO user_entries VALUES (13, 25, 1); -- "The Witcher 3: Wild Hunt"
INSERT INTO user_entries VALUES (13, 26, 0); -- "Fortnite"


select * from users;
select * from topics;
select * from entries;
select * from user_entries;
