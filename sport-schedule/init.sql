-- init.sql
CREATE
DATABASE schedule_db;
GO
USE schedule_db;
GO

CREATE TABLE teams
(
    id   BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE games
(
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    home_team_id  BIGINT   NOT NULL,
    away_team_id  BIGINT   NOT NULL,
    game_datetime DATETIME NOT NULL,
    home_score    INT DEFAULT 0,
    away_score    INT DEFAULT 0,
    is_completed  BIT DEFAULT 0,
    FOREIGN KEY (home_team_id) REFERENCES teams (id),
    FOREIGN KEY (away_team_id) REFERENCES teams (id)
);

INSERT INTO teams (name)
VALUES ('Dynamo Kyiv'),
       ('Shakhtar Donetsk'),
       ('Real Madrid'),
       ('Barcelona'),
       ('Liverpool'),
       ('Manchester City'),
       ('AC Milan'),
       ('Inter Milan');

INSERT INTO games (home_team_id, away_team_id, game_datetime, home_score, away_score, is_completed)
VALUES (1, 2, DATEADD(day, 1, GETDATE()), 0, 0, 0),
       (3, 4, DATEADD(day, -2, GETDATE()), 3, 2, 1),
       (5, 6, DATEADD(hour, 3, GETDATE()), 0, 0, 0),
       (7, 8, DATEADD(day, -4, GETDATE()), 1, 1, 1);