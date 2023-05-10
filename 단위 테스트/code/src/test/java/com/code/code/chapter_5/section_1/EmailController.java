package com.code.code.chapter_5.section_1;

public class EmailController {
    
    private final IEmailGateway iEmailGateway;
    public EmailController(IEmailGateway iEmailGateway) {
        this.iEmailGateway = iEmailGateway;
    }

    public void GreetUser(String email) {
        iEmailGateway.sendGreetingsEmail(email);
    }
}
