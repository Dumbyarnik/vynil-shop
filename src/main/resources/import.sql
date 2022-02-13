/* users */
insert into client(username) values ('ringo');
insert into client(username) values ('bingo');
insert into client(username) values ('shmingo');
/* admin */
insert into client(username) values ('admin');

/* contacts */
insert into contact(email, phone, client_id) 
    values ('ringo@cool', '897654321', '1');
insert into contact(email, phone, client_id) 
    values ('bingo@cool', '897654321', '2');

/* vinyls */
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Abbey Road', 'The Beatles', 'ROCK', 'Cool', 12, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Pet Sounds', 'The Beach Boys', 'ROCK', 'Cool', 20, 1); 
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('The Doors', 'The Doors', 'ROCK', 'Cool', 20, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Rubber Soul', 'The Beatles', 'ROCK', 'Cool', 20, 1);             
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Random Access Memories', 'Daft Punk', 'ELECTRO', 'Cool', 5, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Cross', 'JUSTICE', 'ELECTRO', 'Cool', 5, 1);
 insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Alive', 'Daft Punk', 'ELECTRO', 'Cool', 5, 1);   
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Homework', 'Daft Punk', 'ELECTRO', 'Cool', 5, 1);   
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Graduation', 'Kanye West', 'HIPHOP', 'Cool', 12, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('The Blueprint', 'Jay Z', 'HIPHOP', 'Cool', 12, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('The Eminem Show', 'Eminem', 'HIPHOP', 'Cool', 12, 1);
insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('Lincense to Ill', 'The Beastie Boys', 'HIPHOP', 'Cool', 12, 1);        

insert into vinyl(title, artist, genre, description, price, client_id) 
    values ('4 Seasons', 'Vivaldi', 'CLASSIC', 'Great', 12, 2);