drop database devicemanagerdb;
drop user devicemanager;
create user devicemanager with password 'password';
create database devicemanagerdb with template=template0 owner=devicemanager;
\c devicemanagerdb devicemanager;
alter default privileges grant all on tables to devicemanager;
alter default privileges grant all on sequences to devicemanager;
create table users(user_id serial primary key not null, username varchar(20) not null, password text not null, created_at timestamp not null default current_timestamp);
create table devices(device_id serial primary key not null, user_id integer not null, os varchar(20) not null, created_at timestamp not null default current_timestamp, updated_at timestamp not null default current_timestamp);
alter table devices add constraint device_users foreign key (user_id) references users(user_id);
