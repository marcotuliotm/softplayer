create table person (
	id bigserial primary key,
	name varchar(150) not null,
	birth date not null,
	gender varchar(10),
	place_of_birth varchar(100),
	nationality varchar(100),
	cpf varchar(14) not null,
	email varchar(100),
	created_at timestamp not null,
	updated_at timestamp not null
);

create unique index person_cpf_uk
	on person (cpf);