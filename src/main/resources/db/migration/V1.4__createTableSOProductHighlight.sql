create table so_product (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	img_url varchar(2000) not null,
	name varchar(500) not null,
	price varchar(20) not null,
	end_date date null,
	create_date timestamp not null
);