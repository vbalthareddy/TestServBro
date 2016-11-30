create table plans(
Name  VARCHAR(50),
  id   VARCHAR(50),
  service varchar(50),
  description         VARCHAR(150),
  volumeSize       INTEGER,
  cost INTEGER
);
create table services(
Name  VARCHAR(50),
  id   VARCHAR(50),
  description         VARCHAR(150),
  maxdbpernode       INTEGER,
  bindable varchar(10)
);
create table mymap(
  instance  INTEGER,
  data  VARCHAR(2000)
);
create table instance(
  instance  INTEGER,
  planId  VARCHAR(50),
  userName  VARCHAR(50),
  password  VARCHAR(50),
  url  VARCHAR(500)  
);