DROP TABLE IF EXISTS movie CASCADE;
DROP TABLE IF EXISTS available_movie CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS usr CASCADE;

CREATE TABLE IF NOT EXISTS movie (
    movie_id SERIAL NOT NULL,
    movie_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (movie_id)
);

CREATE TABLE IF NOT EXISTS available_movie (
    available_date DATE NOT NULL,
    movie_id INT4 NOT NULL,
    seats INT4 NOT NULL,
    PRIMARY KEY (available_date, movie_id)
);

CREATE TABLE IF NOT EXISTS reservation (
    reservation_id SERIAL NOT NULL,
    end_time TIME NOT NULL,
    start_time TIME NOT NULL,
    available_date DATE NOT NULL,
    movie_id INT4 NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (reservation_id)
);

CREATE TABLE IF NOT EXISTS usr (
    user_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE available_movie DROP CONSTRAINT IF EXISTS FK_MOVIE_ID;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_AVAILABLE_DATE_MOVIE_ID;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS FK_USER_ID;

ALTER TABLE available_movie ADD CONSTRAINT FK_MOVIE_ID FOREIGN KEY (movie_id) REFERENCES movie;
ALTER TABLE reservation ADD CONSTRAINT FK_AVAILABLE_DATE_MOVIE_ID FOREIGN KEY (available_date, movie_id) REFERENCES available_movie;
ALTER TABLE reservation ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES usr;
