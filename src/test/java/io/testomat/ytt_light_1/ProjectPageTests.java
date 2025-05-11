package io.testomat.ytt_light_1;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPageTests {

    @Test
    void firstTest() {
        open("https://app.testomat.io/");

        //user login
        $("#content-desktop #user_email").setValue("zalanis7@gmail.com");
        $("#content-desktop #user_password").setValue("Mattinazoria2409");
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=\"commit\"]").click();
        $(".common-flash-success").shouldBe(Condition.visible);

        //project search
        $("#search").setValue("manufacture light");

        //project select
        $(byText("manufacture light")).click();

        //waiting for project to load
        $("h2").shouldHave(Condition.text("manufacture light"));
    }
}
