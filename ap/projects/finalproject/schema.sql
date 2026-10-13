-- Library database schema (also created automatically by Database.initDatabase())
CREATE TABLE IF NOT EXISTS employees (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    id        VARCHAR(50) PRIMARY KEY,
    title     VARCHAR(200) NOT NULL,
    author    VARCHAR(100),
    year      INT,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    added_by  VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS loans (
    id               VARCHAR(50) PRIMARY KEY,
    book_id          VARCHAR(50) NOT NULL REFERENCES books(id),
    student_username VARCHAR(50) NOT NULL REFERENCES students(username),
    start_date       DATE NOT NULL,
    end_date         DATE NOT NULL,
    approved         BOOLEAN NOT NULL DEFAULT FALSE,
    approved_by      VARCHAR(50),
    received_date    DATE,
    returned_date    DATE
);

select * from students;
