package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataInfo;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;
    private DataInfo.CardInfo cardInfo;
    private DataInfo.CardInfo cardAnotherInfo;

    @BeforeEach
    public void logIn() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataInfo.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataInfo.getVerificationCodeFor();
        dashboardPage = verificationPage.validCode(verificationCode);
        cardInfo = DataInfo.getCardInfo();
        cardAnotherInfo = DataInfo.getAnotherCardInfo();
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {

        int expected = dashboardPage.getCardBalance(cardInfo.getNumber()) + 20;
        int expected2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber()) - 20;

        var sendMoney = dashboardPage.sendMoneyById(cardInfo.getNumber());
        sendMoney.sendMoney(20, cardAnotherInfo.getNumber());

        int actual = dashboardPage.getCardBalance(cardInfo.getNumber());
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        int expected = dashboardPage.getCardBalance(cardInfo.getNumber()) - 20;
        int expected2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber()) + 20;

        var sendMoney = dashboardPage.sendMoneyById(cardAnotherInfo.getNumber());
        sendMoney.sendMoney(20, cardInfo.getNumber());

        int actual = dashboardPage.getCardBalance(cardInfo.getNumber());
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyFirstCard() {
        int expected = dashboardPage.getCardBalance(cardInfo.getNumber());
        int expected2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());

        var sendMoney = dashboardPage.sendMoneyById(cardInfo.getNumber());
        sendMoney.sendMoney(20, cardInfo.getNumber());

        int actual = dashboardPage.getCardBalance(cardInfo.getNumber());
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneySecondCard() {
        int expected = dashboardPage.getCardBalance(cardInfo.getNumber());
        int expected2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());

        var sendMoney = dashboardPage.sendMoneyById(cardAnotherInfo.getNumber());
        sendMoney.sendMoney(20, cardAnotherInfo.getNumber());

        int actual = dashboardPage.getCardBalance(cardInfo.getNumber());
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyOverLimit() {
        int expected = dashboardPage.getCardBalance(cardInfo.getNumber());
        int expected2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());

        var sendMoney = dashboardPage.sendMoneyById(cardInfo.getNumber());
        sendMoney.sendMoney(30_000, cardAnotherInfo.getNumber());

        int actual = dashboardPage.getCardBalance(cardInfo.getNumber());
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(cardAnotherInfo.getNumber());
        assertEquals(expected2, actual2);

    }
}
