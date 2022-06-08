create table COMPARISON_PRICE (
	id IDENTITY PRIMARY KEY auto_increment,
	fk_id bigint not null,
	fk_type varchar(50) not null,
	url varchar(2000) not null,
	price varchar(25) null,
	create_date timestamp not null
);