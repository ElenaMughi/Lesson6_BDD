package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }

    public int getCardBalance(String id) {
        String shortId = id.substring(15, 19);
        String text = $$(".list__item").findBy(Condition.text(shortId)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    private String extractId(String text) {
        var finish = text.indexOf(balanceStart);
        String idText = text.substring(15, finish);
        return idText;
    }

    public void sendMoneyById(int money, String id,String writeOffCard) {
//        ElementsCollection cards = $$("[data-test-id=action-deposit]");
//        cards.first().click();
        String shortId = id.substring(15, 19);
        $$(".list__item").find(Condition.text(shortId)).$( "[data-test-id=action-deposit]").click();
        var sendMoney = new SendMoneyPage();
        sendMoney.sendMoney(money, writeOffCard);
    }

    ;
}
