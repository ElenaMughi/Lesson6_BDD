package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataInfo;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferErrorTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    public void logIn() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataInfo.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataInfo.getVerificationCodeFor();
        dashboardPage = verificationPage.validCode(verificationCode);
    }

    @Test
    void shouldTransferMoneyOverLimit() {
        int expected = dashboardPage.getCardBalance("5559 0000 0000 0001");
        int expected2 = dashboardPage.getCardBalance("5559 0000 0000 0002");

        dashboardPage.sendMoneyById(30_000, "5559 0000 0000 0001", "5559 0000 0000 0002");

        int actual = dashboardPage.getCardBalance("5559 0000 0000 0001");
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance("5559 0000 0000 0002");
        assertEquals(expected2, actual2);

    }

}
