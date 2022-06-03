create table unknowns (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	title varchar(500) not null,
    create_date timestamp not null
);