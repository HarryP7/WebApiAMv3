CREATE TABLE Users(
Id_User      INT AUTO_INCREMENT,
Uid          NVARCHAR (255)  NOT NULL,
Full_Name    VARCHAR (255)   NULL,
Email        VARCHAR (50)    NULL,
Phone        VARCHAR (16)    NULL,
Login        NVARCHAR (50)   NOT NULL,
Password 	 VARCHAR (255) 	 NOT NULL,
image_Url	 varchar(255)	 NULL,
About_Me	 varchar(255)	 NULL,
rating		 int(11)		 NOT NULL,
Home 		 int(11)		 NULL,
Create_Date   datetime(2) 	 NULL,
Role		 int(2)			 NOT NULL,
token 		 varchar(255)	 NULL,
PRIMARY KEY (Id_User),
FOREIGN KEY (Home) REFERENCES Location(Id));

CREATE TABLE events(
  Id_Event    int(100) auto_increment   PRIMARY KEY,
  Uid         varchar(100) charset utf8 not null,
  Location    int(100)                  not null,
  Title       varchar(100)              not null,
  Date_Place  datetime(2)               not null,
  Image_Url   varchar(100) charset utf8 null,
  Cost        decimal(6, 2)             not null,
  Organizer   int                       not null,
  Description varchar(100)              null,
  Status      int default '1'           not null,
  Start_Time  datetime(2)               not null,
  End_Time    datetime(2)               null,
  Place       varchar(100)              null,
  foreign key (Organizer) references users (Id_User));

CREATE TABLE Participation(
    Id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    User int NOT NULL,
    Status int NOT NULL,
    Event int NOT NULL,
    Joined_At datetime(2) NULL,
    FOREIGN KEY (User) REFERENCES users (Id_User),
    FOREIGN KEY (Event) REFERENCES events (Id_Event));

CREATE TABLE location(
  Id       int auto_increment PRIMARY KEY,
  Lat      decimal(10, 6) not null,
  Lng      decimal(10, 6) not null,
  City     varchar(100)   null,
  Street   varchar(100)   null,
  Building varchar(10)    null);

CREATE TABLE EventReview(
  id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  Author int(11) NOT NULL,
  Event int(11) NOT NULL,
  Comment varchar(255) NOT NULL,
  Rating int(11) DEFAULT 0 NOT NULL,
  Date_Place datetime(2) NOT NULL,
  CONSTRAINT Author_fk FOREIGN KEY (Author) REFERENCES users (Id_User),
  CONSTRAINT Event_fk FOREIGN KEY (Event) REFERENCES events (Id_Event) ON DELETE CASCADE);

INSERT INTO Users(Uid,Full_Name,Login,Email,Phone,Password) VALUES
('999d9900-d99d-99d9-9999-0099999999990000','Tom Riddle Marvolo','Lord VolDeMort',
'user@gmail.com','9 999 999 9999','$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO Events(Uid, Location, Lng, Title,Date_Place,Ev_Date, Cost,Organizer,Description) VALUES
('73632f9f-f2cd-4313-a475-32c6d5d6c5e6',56.129042,40.40703, 'Hunt for unicorns', CURDATE(),
'2019-04-20',100,1,'comment');
