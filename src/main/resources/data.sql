insert into utente(ruolo, cognome, nome, password, username) values(0, 'Pacello', 'Paolo', 'Dr_pacello97', 'PaoloPac');
insert into utente(ruolo, cognome, nome, password, username) values(0, 'Neri', 'Mario', 'Giujone3000_1', 'GiujoTwitchDB');
insert into utente(ruolo, cognome, nome, password, username) values	(2, 'Emiliani', 'Giulio', 'gEmiliani321!', 'Gemils');
insert into utente(ruolo, cognome, nome, password, username) values	(2, 'Lazzari', 'Matteo', 'mLazzari98?!', 'LazzariM');
insert into utente(ruolo, cognome, nome, password, username) values	(1, 'poggi', 'mauro', 'DePaoli55_', 'GDePaoli55_');


insert into ristorante(utente_id, nome) values(3, 'Ristorante N');
insert into ristorante(utente_id, nome)	values(4, 'Sushi');

insert into categoria(nome)values('primi');
insert into categoria(nome)values('secondi');
insert into categoria(nome)values('contorni');
insert into categoria(nome)values('dolci');
insert into categoria(nome)values('bevande');
insert into categoria(nome)values('senza glutine');

insert into ingrediente(nome) values('pasta');
insert into ingrediente(nome) values('uova');
insert into ingrediente(nome) values('pomodoro');
insert into ingrediente(nome) values('carne');
insert into ingrediente(nome) values('salmone');
insert into ingrediente(nome) values('tonno');

insert into piatto(prezzo, categoria_id, ristorante_id, descrizione, nome) values (8.90, 1, 1, 'spaghetti con uova e guanciale', 'Carbonara');
insert into piatto(prezzo, categoria_id, ristorante_id, descrizione, nome) values (25.00, 2, 2, 'misto di pesce crudo', 'Sashimi');

insert into ingredienti_piatto(id_ingrediente, id_piatto) values (1, 1);
insert into ingredienti_piatto(id_ingrediente, id_piatto) values (2, 1);
insert into ingredienti_piatto(id_ingrediente, id_piatto) values (3, 1);
insert into ingredienti_piatto(id_ingrediente, id_piatto) values (5, 2);
insert into ingredienti_piatto(id_ingrediente, id_piatto) values (6, 2);

insert into ordine(stato, cliente_id, data_invio, ristorante_id) values (0, 1, '2023-07-04 15:26:49.019040', 1);
insert into ordine(stato, cliente_id, data_invio, ristorante_id) values (0, 1, '2023-06-30 15:27:48.641167', 2);
insert into ordine(stato, cliente_id, data_invio, ristorante_id) values (3, 1, '2023-06-30 15:27:48.641167', 2);
insert into ordine(stato, cliente_id, data_invio, ristorante_id) values (2, 2, '2023-07-09 16:20:48.000000', 1);

insert into rigadordine(quantità, ordine_id, piatto_id) values (2, 1, 1);
insert into rigadordine(quantità, ordine_id, piatto_id) values (3, 2, 2);
