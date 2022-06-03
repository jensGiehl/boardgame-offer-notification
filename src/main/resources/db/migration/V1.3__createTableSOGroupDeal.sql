create table so_groupdeal (
	id IDENTITY PRIMARY KEY auto_increment,
	url varchar(2000) not null,
	img_url varchar(2000) not null,
	name varchar(500) null,
	quantity int null,
	price varchar(20) null,
	valid_until date null,
	fail_count int null,
	create_date timestamp not null
);