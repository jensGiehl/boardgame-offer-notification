create table milan (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	name varchar(500) not null,
	img_url varchar(2000) null,
	price varchar(20) not null,
	stock_text text not null,
	create_date timestamp not null
);