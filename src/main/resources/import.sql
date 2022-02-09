/* users */
insert into client(username) values ('ringo');
insert into client(username) values ('bingo');
insert into client(username) values ('shmingo');

/* contacts */
insert into contact(email, phone, client_id) 
    values ('ringo@cool', '897654321', '1');
insert into contact(email, phone, client_id) 
    values ('bingo@cool', '897654321', '2');

/* vinyls */
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Black Death', 'Metallica', 'ROCK', 'Cool', 12, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Apokalypse', 'Metallica', 'ROCK', 'Cool', 20, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Brown Death', 'Metallica', 'ROCK', 'Cool', 5, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Black Greatness', 'Metallica', 'ROCK', 'Cool', 12, 1);

insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('4 Seasons', 'Vivaldi', 'CLASSIC', 'Great', 12, 2);