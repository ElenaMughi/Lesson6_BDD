package ru.netology.web.page;

import static com.codeborne.selenide.Selenide.$;

public class SendMoneyPage {

    public void sendMoney(int money, String id) {
        $("[data-test-id=amount] input").setValue(Integer.toString(money));
        $("[data-test-id=from] input").setValue(id);
        $("[data-test-id=action-transfer]").click();
    }

}
