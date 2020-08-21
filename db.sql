drop table if exists booked_session;
drop table if exists training_session;
drop table if exists trainer;
drop table if exists review;
drop table if exists gym;
drop table if exists course;
drop table if exists users;

create table if not exists users(
    user_id   serial  not null primary key,
    username varchar not null,
    password varchar not null,
    email varchar not null,
    manager  boolean,
    street varchar not null,
    unique(email),
    unique (user_id, username));

create table if not exists course(
    course_id serial not null primary key,
    course_name varchar not null);

create table if not exists gym(
    gym_id serial not null primary key,
    gym_name varchar not null,
    street varchar not null,
    manager_id integer not null,
    manager_name varchar not null,
    foreign key (manager_id, manager_name) references users(user_id, username),
    unique (gym_id, manager_id, manager_name),
    unique (street));

create table if not exists trainer(
    trainer_id serial not null primary key,
    trainer_name varchar not null,
    gym_id int not null,
    foreign key (gym_id) references gym(gym_id),
    unique (trainer_name, gym_id));

create table if not exists training_session(
    session_id serial not null primary key,
    trainer_id int not null,
    trainer_name varchar not null,
    course_id integer not null,
    individual boolean,
    gym_id integer not null,
    street varchar not null,
    time_start time not null,
    time_end time not null,
    day date not null,
    description varchar,
    recurrence varchar,
    foreign key (course_id) references course(course_id),
    foreign key (trainer_id) references trainer(trainer_id),
    foreign key (trainer_name,gym_id) references trainer(trainer_name, gym_id),
    foreign key (street) references gym(street),
    unique (trainer_id, course_id, time_start, time_end, day));

create table if not exists booked_session(
  book_id serial not null primary key,
  session_id int not null,
  user_id int not null,
  foreign key (session_id) references training_session(session_id),
  foreign key (user_id) references users(user_id));

create table if not exists review(
    review_id serial not null primary key,
    user_id   int    not null,
    course_id int    not null,
    gym_id    int    not null,
    review    varchar,
    rating    int    not null,
    foreign key (user_id) references users (user_id),
    foreign key (course_id) references course (course_id),
    foreign key (gym_id) references gym(gym_id),
    unique(review_id, user_id, course_id, gym_id));

insert into users(username, password, email, manager, street) values ('admin', 'admin','zaguza97@gmail.com', 'false', 'via dei sette metri 5');
insert into users(username, password, email, manager, street) values ('manager', 'manager', 'redslorenz@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('effi', 'pass', 'Andrea.efficace1@gmail.com', 'false', 'via del muro linari 21');
insert into users(username, password, email, manager, street) values ('Jerry Scotti', 'gympass', 'gliassidicuori@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Ginacarlo Magalli', 'gympass1', 'A.effi.97@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Laura Marini', 'gympass2', 'lorybtc@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Valentina Nappi', 'gympass3', 'monkey.d.rossi@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Riley Reid', 'gympass4', 'reds.lorenzorossi@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Valentino Rossi', 'gympass5', 'abcdefghil@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('Federica Nargi', 'gympass6', 'rimembranzeinfiore@gmail.com', 'true', ' ');
insert into users(username, password, email, manager, street) values ('ginopaoluzzi', 'manager1', 'jymhgf@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('fabiomassimo', 'manager2', 'hygtrae@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('carloconti', 'manager3', 'jhygtfr@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('beppeBergomi', 'manager4', 'juryhtg@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('dilettaLeotta', 'manager5', 'jhegtrwef@gmail.com','true', ' ');
insert into users(username, password, email, manager, street) values ('lanarhoades', 'manager6', 'juhyrgte@gmail.com','true', ' ');



insert into course(course_name) values ('Kick Boxing');
insert into course(course_name) values ('Pugilato');
insert into course(course_name) values ('Zumba');
insert into course(course_name) values ('Salsa');
insert into course(course_name) values ('Funzionale');
insert into course(course_name) values ('Walking');
insert into course(course_name) values ('Pump');

insert into gym(gym_name, street, manager_id, manager_name) values ('Evolution','Via del Corso 486 Roma',2,'manager');
insert into gym(gym_name, street, manager_id, manager_name) values ('FitExpress','Via Bernardino Alimena, 111, 00173 Roma RM',4,'Jerry Scotti');
insert into gym(gym_name, street, manager_id, manager_name) values ('eMotion Palestre','Via di Tor Vergata, 1, 00133 Roma RM',5,'Ginacarlo Magalli');
insert into gym(gym_name, street, manager_id, manager_name) values ('Palestre Italiane','Via Mary Pandolfi de Rinaldis, 18 Roma',6,'Laura Marini');
insert into gym(gym_name, street, manager_id, manager_name) values ('Miami Sporting Club','Via Giulio Bonasoni, 68, 00133 Roma RM',7,'Valentina Nappi');
insert into gym(gym_name, street, manager_id, manager_name) values ('CampusXGym','Via di Passo Lombardo, 341, 00133 Roma RM',8,'Riley Reid');
insert into gym(gym_name, street, manager_id, manager_name) values ('S.C. Tuscolano Corefit','Via Filomusi Guelfi, 38, 00173 Roma RM',9,'Valentino Rossi');
insert into gym(gym_name, street, manager_id, manager_name) values ('Sporting Village','Via Polia, 44, 00175 Roma RM',10,'Federica Nargi');
insert into gym(gym_name, street, manager_id, manager_name) values ('Virgin','Viale kennedy 4 Ciampino',11,'ginopaoluzzi');
insert into gym(gym_name, street, manager_id, manager_name) values ('FitActive', 'Via Roma 4 Ciampino', 12, 'fabiomassimo');
insert into gym(gym_name, street, manager_id, manager_name) values ('A.S.D Athena ', 'Viale Roma 101 Ciampino', 13, 'carloconti');
insert into gym(gym_name, street, manager_id, manager_name) values ('Atlantis Sporting Club', 'Via Mura dei Francesi 179B Ciampino', 14, 'beppeBergomi');
insert into gym(gym_name, street, manager_id, manager_name) values ('A.S.D. Europa ', 'Via Palermo 15 Ciampino', 15, 'dilettaLeotta');
insert into gym(gym_name, street, manager_id, manager_name) values ('Axel Vertical Dolls', 'Via Umberto Maddalena 21 Ciampino', 16, 'lanarhoades');


insert into trainer(trainer_name, gym_id) values('Mario Rossi', 1);
insert into trainer(trainer_name, gym_id) values('Giacomo Leopardi', 2);
insert into trainer(trainer_name, gym_id) values('Dante Alighieri', 3);
insert into trainer(trainer_name, gym_id) values('Frank Zappa', 4);
insert into trainer(trainer_name, gym_id) values('Tony Manero', 5);
insert into trainer(trainer_name, gym_id) values('Ugo Fantozzi', 6);
insert into trainer(trainer_name, gym_id) values('Biagio Antonacci', 7);
insert into trainer(trainer_name, gym_id) values('Francesco Totti', 8);
insert into trainer(trainer_name, gym_id) values('Young Signorino', 9);
insert into trainer(trainer_name, gym_id) values('Lionel Messi', 10);
insert into trainer(trainer_name, gym_id) values('Checco lione', 11);
insert into trainer(trainer_name, gym_id) values('Giuseppina Agata', 12);
insert into trainer(trainer_name, gym_id) values('Marco Carta', 13);
insert into trainer(trainer_name, gym_id) values('Paolo Bonolis', 14);


insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (1,'Mario Rossi',1, true, 1, 'Via del Corso 486 Roma', '22:00', '23:00', '2020-8-21', 'Questa è una lezione di KickBoxing','RRULE:FREQ=DAILY;INTERVAL=7;COUNT=3;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (2,'Giacomo Leopardi',2, false, 2, 'Via Bernardino Alimena, 111, 00173 Roma RM', '22:10', '23:10', '2020-8-22', 'Questa è una lezione di Pugilato','RRULE:FREQ=DAILY;INTERVAL=7;COUNT=4;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (3,'Dante Alighieri',3, true, 3, 'Via di Tor Vergata, 1, 00133 Roma RM', '22:20', '23:20', '2020-8-23', 'Questa è una lezione di Zumba','RRULE:FREQ=DAILY;INTERVAL=3;COUNT=4;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (4,'Frank Zappa',4, false, 4, 'Via Mary Pandolfi de Rinaldis, 18 Roma', '22:30', '23:30', '2020-8-21', 'Questa è una lezione di Salsa','RRULE:FREQ=DAILY;INTERVAL=2;COUNT=2;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (5,'Tony Manero',5, true, 5, 'Via Giulio Bonasoni, 68, 00133 Roma RM', '22:00', '23:00', '2020-8-21', 'Questa è una lezione di Funzionale','RRULE:FREQ=DAILY;INTERVAL=1;COUNT=2;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (6,'Ugo Fantozzi',6, false, 6, 'Via di Passo Lombardo, 341, 00133 Roma RM', '22:40', '23:40', '2020-8-22', 'Questa è una lezione di Walking','RRULE:FREQ=DAILY;INTERVAL=7;COUNT=4;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (7,'Biagio Antonacci',7, true, 7, 'Via Filomusi Guelfi, 38, 00173 Roma RM', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump','RRULE:FREQ=MONTHLY;INTERVAL=7;COUNT=4;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (8,'Francesco Totti',1, false, 8, 'Via Polia, 44, 00175 Roma RM', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump','RRULE:FREQ=WEEKLY;INTERVAL=7;COUNT=4;');
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (9,'Young Signorino',2, true, 9, 'Viale kennedy 4 Ciampino', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (10,'Lionel Messi',3, false, 10, 'Via Roma 4 Ciampino', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (11,'Checco lione',4, true, 11, 'Viale Roma 101 Ciampino', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (12,'Giuseppina Agata',5, false, 12, 'Via Mura dei Francesi 179B Ciampino', '22:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (13,'Marco Carta',6, false, 13, 'Via Palermo 15 Ciampino', '23:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);
insert into training_session(trainer_id, trainer_name, course_id, individual, gym_id, street, time_start, time_end, day, description,recurrence)
values (14,'Paolo Bonolis',7, true, 14, 'Via Umberto Maddalena 21 Ciampino', '23:50', '23:50', '2020-8-23', 'Questa è una lezione di Pump',null);

insert into booked_session(session_id, user_id) VALUES (2,3);
insert into booked_session(session_id, user_id) VALUES (1,1);
insert into booked_session(session_id, user_id) VALUES (3,3);
insert into booked_session(session_id, user_id) VALUES (4,1);
insert into booked_session(session_id, user_id) VALUES (5,3);
insert into booked_session(session_id, user_id) VALUES (6,1);
insert into booked_session(session_id, user_id) VALUES (7,3);
