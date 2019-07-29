create table client (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    enabled boolean not null default true,
    name varchar(50) not null,
    url varchar(255) not null
);

create unique index client_name_uk_insensitive on client (upper(name));
create unique index client_url_uk_insensitive on client (upper(url));

create table company (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    enabled boolean not null default true,
    trading_name varchar(255) not null,
    uuid varchar(36) not null,
    client_id bigint not null,
    constraint company_client_id_fk foreign key (client_id) references client,
    parent_id bigint,
    constraint company_parent_id_fk foreign key (parent_id) references company
);

create unique index company_uuid_uk_insensitive on company (upper(uuid));

create table authentication (
    id bigserial primary key,
    login varchar(255) not null,
    enabled boolean not null default true
);

create unique index auth_login_uk_insensitive on authentication (upper(login));

create table user_company (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    authentication_id bigint not null,
    constraint user_company_authentication_id_fk foreign key (authentication_id) references authentication,
    email varchar(255) not null,
    name varchar(255) not null,
    company_id bigint not null,
    constraint user_company_company_id_fk foreign key (company_id) references company
);

create unique index user_company_email_uk_insensitive on user_company (upper(email));

create table user_client (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    authentication_id bigint not null,
    constraint user_client_authentication_id_fk foreign key (authentication_id) references authentication,
    email varchar(255) not null,
    name varchar(255) not null,
    client_id bigint not null,
    constraint user_client_client_id_fk foreign key (client_id) references client
);

create unique index user_client_email_uk_insensitive on user_client (upper(email));

create table device_group (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    description varchar(255) not null,
    register_pin varchar(7) not null,
    company_id bigint not null,
    constraint device_group_company_id_fk foreign key (company_id) references company
);

create unique index device_group_register_pin_uk_insensitive on device_group (upper(register_pin));

create table device (
    id bigserial primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    authentication_id bigint not null,
    constraint device_authentication_id_fk foreign key (authentication_id) references authentication,
    self_identification varchar(255) not null,
    push_token varchar(255) null,
    provision_type character varying(50) not null,
    status character varying(50) not null,
    serial_number varchar(255),
    imei varchar(16),
    company_id bigint not null,
    constraint device_company_id_fk foreign key (company_id) references company,
    device_group_id bigint not null,
    constraint device_device_group_id_fk foreign key (device_group_id) references device_group
);

create unique index device_self_identification_uk_insensitive on device (upper(self_identification));

create table device_state (
    id bigserial primary key,
    created_at timestamptz not null,
    collected_at timestamptz not null,
    device_id bigint not null,
    constraint device_state_device_id_fk foreign key (device_id) references device,
    disk_space_available_in_bytes bigint not null,
    battery_charge_percentage smallint,
    battery_charging boolean not null
);

create table geolocation (
    id bigserial primary key,
    created_at timestamptz not null,
    collected_at timestamptz not null,
    device_id bigint not null,
    constraint geolocation_device_id_fk foreign key (device_id) references device,
    longitude numeric(10,7) not null,
    latitude numeric(9,7) not null
);