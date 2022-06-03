create table so_schmiede (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	img_url varchar(2000) not null,
	name varchar(500) not null,
	create_date timestamp not null
);