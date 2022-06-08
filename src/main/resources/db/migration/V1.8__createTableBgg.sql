create table BGG (
	id IDENTITY PRIMARY KEY auto_increment,
	fk_id bigint not null,
	fk_type varchar(50) not null,
	bgg_id bigint null,
	bgg_rating varchar(10) null,
	wishing int null,
	wanting int null,
	bgg_link varchar(2000) null,
	create_date timestamp not null
);