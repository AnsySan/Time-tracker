create table users
(
    id               bigserial primary key generated always as identity unique,
    username         varchar(40) not null,
    password         varchar(80) not null,
    email            varchar(100) unique not null,
    created_at       timestamptz default current_timestamp,
    updated_at       timestamptz default current_timestamp
);

create table record
(
    id              bigserial primary key generated always as identity unique,
    title           text,
    project_id      bigint not null,
    created_at timestamptz default current_timestamp,
    updated_at timestamptz default current_timestamp,

    CONSTRAINT fk_record_project_id FOREIGN KEY (project_id) REFERENCES project (id)
);

create table project
(
    id            bigserial primary key generated always as identity unique,
    user_id       bigint not null,
    name          varchar(256) not null,
    created_at timestamptz default current_timestamp,
    updated_at timestamptz default current_timestamp,

    CONSTRAINT fk_project_id FOREIGN KEY (user_id) REFERENCES users (id)
);

create table roles
(
    user_id       bigint not null,
    roles         varchar(255) not null,

    CONSTRAINT fk_users_roles FOREIGN KEY (user_id) REFERENCES users (id)
);