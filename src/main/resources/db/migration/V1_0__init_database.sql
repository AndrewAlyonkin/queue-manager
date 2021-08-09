create table notification
(
    id            varchar(255) not null unique,
    created       timestamp    not null,
    deployment_id varchar(255) not null unique,
    status        varchar(255) not null,
    updated       timestamp,
    primary key (id)
)
