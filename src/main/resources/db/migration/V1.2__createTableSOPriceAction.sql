create table so_price_action (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	img_url varchar(2000) not null,
	name varchar(500) not null,
	price varchar(20) not null,
	valid_until date null,
	create_date timestamp not null
);