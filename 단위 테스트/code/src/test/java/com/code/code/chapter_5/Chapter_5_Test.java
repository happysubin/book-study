package com.code.code.chapter_5;

import com.code.code.chapter_5.section_1.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

public class Chapter_5_Test {

    @Test
    @DisplayName("목 라이브러리에서 mock 메서드를 사용해 목을 생성")
    void send_a_greetings_email(){

        //given
        IEmailGateway mock = mock(IEmailGateway.class);
        String email = "user@email.com";
        given(mock.sendGreetingsEmail(email)).willReturn(email);
        EmailController sut = new EmailController(mock);

        //when
        sut.GreetUser(email);

        //then
        verify(mock, times(1)).sendGreetingsEmail(any(String.class));
    }

    @Test
    @DisplayName("mock 메서드를 사용해 스텁을 생성")
    void creating_a_report(){

        //given
        IDatabase stub = mock(IDatabase.class);
        given(stub.getNumberOfUsers()).willReturn(10);
        DbController sut = new DbController(stub);

        //when
        Report report = sut.createReport();

        //then
        Assertions.assertThat(10).isEqualTo(report.getNumberOfUsers());
    }


    @Test
    @DisplayName("스텁으로 상호 작용을 검증. 이는 테스트 취약성으로 이어질 수 있다.")
    void creating_a_report_v2(){

        //given
        IDatabase stub = mock(IDatabase.class);
        given(stub.getNumberOfUsers()).willReturn(10);
        DbController sut = new DbController(stub);

        //when
        Report report = sut.createReport();

        //then
        Assertions.assertThat(10).isEqualTo(report.getNumberOfUsers());
        verify(stub, times(1)).getNumberOfUsers();
    }
}
