alter table data
    add column BGG_USER_RATED int null;

alter table data
    add column BGG_RANK int null;

alter table data
    add column BEST_PRICE_POSSIBLE char(5) not null;