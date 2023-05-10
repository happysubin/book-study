package com.code.code.chapter_5.section_1;

public class DbController {

    private final IDatabase iDatabase;

    public DbController(IDatabase iDatabase) {
        this.iDatabase = iDatabase;
    }

    public Report createReport() {
        int numberOfUsers = iDatabase.getNumberOfUsers();
        return new Report(numberOfUsers);
    }
}
