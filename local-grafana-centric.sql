-- Create Local Grafana Database (MySQL)

create schema  if not exists centric;

use centric;

drop table if exists engineering;

create table engineering
(
    id   int          not null,
    name varchar(256) not null,
    primary key (id)
);

insert into engineering (id, name)
values (100, 'Access Management'),
       (101, 'Adoption'),
       (102, 'Bulk Scan'),
       (103, 'Civil Damages'),
       (104, 'Civil Secure Data Transfer'),
       (105, 'CMC'),
       (106, 'CTSC'),
       (107, 'Divorce'),
       (108, 'Employment Tribunals'),
       (109, 'Employment Tribunals (Legacy)'),
       (110, 'ETHOS'),
       (111, 'Evidence Management'),
       (112, 'FACT'),
       (113, 'Family Integration'),
       (114, 'Family Private Law'),
       (115, 'Fee & Pays'),
       (116, 'Financial Remedy'),
       (117, 'FPLA'),
       (118, 'Help With Fees'),
       (119, 'hmc'),
       (120, 'I & A'),
       (121, 'IDAM'),
       (122, 'LAU'),
       (123, 'Management Information'),
       (124, 'No Fault Drive'),
       (125, 'PCQ'),
       (126, 'Platform'),
       (127, 'Performance Test'),
       (128, 'Pre-recorded Evidence'),
       (129, 'Private Law'),
       (130, 'Probate'),
       (131, 'Publishing & Information'),
       (132, 'RTPS'),
       (133, 'Scheduling and Listing'),
       (134, 'Special Tribunals'),
       (135, 'SSCS'),
       (136, 'Video Hearings'),
       (137, 'Work Allocation'),
       (138, 'XUI');


drop table if exists delivery;

create table delivery
(
    id   int        not null auto_increment,
    team_id int     not null,
    delivery_lead_time int,
    rec_date        date not null,
    primary key (id)
);

create table change_lead_rate
(
    id   int        not null auto_increment,
    team_id int     not null,
    change_lead_rate int,
    rec_date        date not null,
    primary key (id)
);

create table mean_time_to_recovery
(
    id   int        not null auto_increment,
    team_id int     not null,
    mttr            int,
    rec_date        date not null,
    primary key (id)
);

create table change_failure_rate
(
    id   int        not null auto_increment,
    team_id int     not null,
    change_failure  int,
    rec_date        date not null,
    primary key (id)
);


create table combined_state
(
    team_id   int       not null auto_increment,
    delivery_lead_time  int,
    change_lead_rate    int,
    change_failure      int,
    mttr                int,
    primary key (team_id)
);

create table perspectives
(
    id                      int not null auto_increment,
    team_id                 int,
    repos_without_renovate  int,
    code_smells_per_repo    int,
    coverage                numeric,
    complexity              numeric,
    deprecated_helm_charts  int,
    rec_date                date not null,
    primary key (id)
)
