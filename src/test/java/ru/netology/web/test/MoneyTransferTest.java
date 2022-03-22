package ru.netology.web.test;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataInfo;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.SendMoneyPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;
    private ElementsCollection cards = $$("[data-test-id=action-deposit]");

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999");
    }

    @BeforeEach
    public void logIn() {
        var loginPage = new LoginPage();
        var authInfo = DataInfo.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataInfo.getVerificationCodeFor();
        dashboardPage = verificationPage.validCode(verificationCode);
//        return dashboardPage;
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        cards.first().click();
        var sendMoney = new SendMoneyPage();
        sendMoney.sendMoney(20, "0002");
        int expected = 10_020;
        int actual = dashboardPage.getCardBalance("0001");
        assertEquals(expected, actual);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        cards.last().click();
        var sendMoney = new SendMoneyPage();
        sendMoney.sendMoney(20, "0001");
        int expected = 10_020;
        int actual = dashboardPage.getCardBalance("0002");
        assertEquals(expected, actual);
    }

    @Test
    void shouldTransferMoneyOverLimit() {
        cards.last().click();
        var sendMoney = new SendMoneyPage();
        sendMoney.sendMoney(30_000, "0001");
        int expected = 20_000;
        int actual = dashboardPage.getCardBalance("0002");
        assertEquals(expected, actual);
    }
}
