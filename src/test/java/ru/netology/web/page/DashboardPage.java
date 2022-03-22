package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }

    public int getCardBalance(String id) {
        cards = $$(".list__item");
        var text = "";
        int size = cards.size();
        for (int i = 0; i < size; i++) {
            var txt = cards.get(i).text();
            String txtId = extractId(txt);
            if (txtId.equals(id)) {
                text = txt;
            }
        }
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
}
