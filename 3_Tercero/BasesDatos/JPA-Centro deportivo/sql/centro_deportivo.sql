drop table Reserva cascade constraints;
drop table Pista cascade constraints;
drop table Jugador cascade constraints;
drop table ReservaJugador cascade constraints;

drop sequence seq_pista;

create table Pista(
	nro integer primary key,
	descripcion varchar(30) not null,
	capacidad integer not null
);

create table Jugador(
	nif varchar(10) primary key,	
	nombre varchar(30) not null,
	apellido varchar(30) not null
);

create table Reserva(
	fecha date,
	hora integer,
	pista integer references Pista,
	primary key (fecha, hora, pista),
	constraint ERROR_IN_DATE 
		check (fecha >= TO_DATE('01/01/2018','DD/MM/YYYY') and fecha <= TO_DATE('31/12/2018','DD/MM/YYYY')),
	constraint ERROR_IN_HOUR check (hora between 0 and 23 )
);

create table ReservaJugador(
	fecha date,
	hora integer,
	pista integer,
	nif varchar(10),
	constraint fk_reserva foreign key (fecha, hora, pista) references Reserva(fecha, hora, pista),
	constraint fk_jugador foreign key (nif) references Jugador,
	primary key (fecha, hora, pista, nif)
);

create sequence seq_pista;

--Carga de datos para testing

--Pistas de la 1 la 5
insert into Pista values (seq_pista.nextval, 'Pista padel 1', 4); 
insert into Pista values (seq_pista.nextval, 'Pista padel 2', 4);
insert into Pista values (seq_pista.nextval, 'Pista futbito 1', 10);
insert into Pista values (seq_pista.nextval, 'Pista tenis 1', 2);
insert into Pista values (seq_pista.nextval, 'Pista frontón 1', 4);

insert into Jugador values ('11111111A', 'Juan', 'Malaga');
insert into Jugador values ('11111111B', 'David', 'Bugos');
insert into Jugador values ('11111111C', 'Jesus', 'Almeria');
insert into Jugador values ('11111111D', 'Javier', 'Toledo');
insert into Jugador values ('11111111E', 'Raul', 'Zamora');
insert into Jugador values ('11111111F', 'Carlos', 'Cuenca');

insert into Reserva values ('03/03/18', 15, 1);
insert into Reserva values ('03/03/18', 18, 2);
insert into Reserva values ('03/03/18', 18, 4);

-- Completamos el número de jugadores de esta reserva
insert into ReservaJugador values ('03/03/18', 15, 1, '11111111A'); 
insert into ReservaJugador values ('03/03/18', 15, 1, '11111111B'); 
insert into ReservaJugador values ('03/03/18', 15, 1, '11111111C'); 
insert into ReservaJugador values ('03/03/18', 15, 1, '11111111D');

-- Se deja un hueco libre en esta reserva
insert into ReservaJugador values ('03/03/18', 18, 2, '11111111E');

-- Se deja un hueco libre en esta reserva
insert into ReservaJugador values ('03/03/18', 18, 4, '11111111F');

commit;

-- Mostramos el estado de las tablas pistas y reservas de jugadores

select * from pista;

select * from ReservaJugador;

commit;

exit
