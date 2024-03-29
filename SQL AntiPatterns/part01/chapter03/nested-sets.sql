CREATE TABLE Comments (
    comment_id SERIAL PRIMARY KEY ,
    nsleft INTEGER NOT NULL ,
    nsright INTEGER NOT NULL ,
    bug_id BIGINT UNSIGNED NOT NULL ,
    author BIGINT UNSIGNED NOT NULL ,
    comment_date DateTIME NOT NULL ,
    comment TEXT NOT NULL ,
    FOREIGN KEY (bug_id) REFERENCES Bugs(bug_id) ,
    FOREIGN KEY (author) REFERENCES Accounts(account_id)
);