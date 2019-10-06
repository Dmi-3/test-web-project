create table doctors
(
    id bigint identity primary key,
    first_name varchar(50) not null,
    last_name varchar(50),
    patronymic varchar(50),
    specialization_id bigint not null,
    constraint fk_specialization_id foreign key specialization_id references specializations (id)
);

create table patients
(
    id bigint identity primary key,
    first_name varchar(50) not null,
    last_name varchar(50),
    patronymic varchar(50),
    phone varchar(30) not null
);

create table prescriptions
(
    id bigint identity primary key,
    description varchar(255) not null,
    patient_id bigint not null,
    doctor_id bigint not null,
    created_date default current_timestamp not null,
    prescription_priority_id bigint not null,

    constraint fk_patient_id foreign key patient_id  references patients (id),
    constraint fk_doctor_id foreign key doctor_id  references doctors (id),
    constraint fk_prescription_priority_id foreign key prescription_priority_id  references prescription_priorities (id)
);

create table prescription_priorities
(
    id bigint identity primary key,
    name varchar(20)
);

create table specializations
(
    id bigint identity primary key,
    name varchar(20)
);
