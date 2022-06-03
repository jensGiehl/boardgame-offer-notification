create table messages (
	id IDENTITY PRIMARY KEY auto_increment,
	fk_id bigint not null,
	fk_type varchar(50) not null,
	message_id long not null,
	message_type varchar(50) not null,
	chat_id long not null,
	create_date timestamp not null
);