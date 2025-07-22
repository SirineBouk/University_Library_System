
CREATE TABLE Book (
    book_id INT PRIMARY KEY,
    title VARCHAR2(100),
    author VARCHAR2(100),
    category VARCHAR2(50),
    publication_year NUMBER(4),
    stock INT CHECK (stock >= 0)
);

CREATE TABLE Student (
    student_id     VARCHAR2(15) PRIMARY KEY,
    first_name     VARCHAR2(50),
    last_name      VARCHAR2(50),
    email          VARCHAR2(100),
    phone_number   VARCHAR2(20),
    CHECK (email LIKE '%@%.com' OR email IS NULL)
);

CREATE TABLE Loan (
    loan_id         INT PRIMARY KEY,
    student_id      VARCHAR2(15),
    book_id         INT,
    loan_date       DATE DEFAULT SYSDATE,
    due_date        DATE DEFAULT (SYSDATE + 15),
    return_date     DATE,
    status          VARCHAR2(20) DEFAULT 'In Progress',
    CONSTRAINT chk_status CHECK (status IN ('In Progress', 'Returned', 'Overdue')),
    CONSTRAINT chk_dates CHECK (
        loan_date < due_date
        AND (return_date IS NULL OR loan_date < return_date)),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

