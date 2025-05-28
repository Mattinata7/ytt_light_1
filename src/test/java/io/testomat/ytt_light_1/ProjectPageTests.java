package io.testomat.ytt_light_1;

import com.codeborne.selenide.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPageTests extends BaseTest{

    static String baseUrl = env.get("BASE_URL");
    static String email = env.get("EMAIL");
    static String password = env.get("PASSWORD");
    String targetProjectName = "manufacture light";

    @BeforeAll
    static void openTestomatAndLogin(){
        open(baseUrl);

        loginUser(email, password);

        Configuration.headless = false;
    }

    @BeforeEach
    void openHomePage(){
        open(baseUrl);
    }


    @Test
    void firstTest() {

        searchForProject(targetProjectName);

        selectProject(targetProjectName);

        waitingForProjectToLoad(targetProjectName);
    }

    @Test
    public void anotherTest() {

        searchForProject(targetProjectName);

        SelenideElement targetProject = countOfProjectsShouldBeEqualTo(2).first();

        countOfTestCasesShouldBeEqualTo(targetProject, 0);
    }


    @Test
    public void findsManufactureTestomatioProjectWithCountOfTests() {

        searchForProjectManufactureTestomatio();
        
        $$("#grid ul li").filter(visible).shouldHave(CollectionCondition.size(1));

        $("[title=\"Manufacture Testomatio\"] p").shouldHave(text("2 tests"));
        
        waitingForProjectMTToLoad();
    }

    @Test

    public void findSmokyTestCountInManufactureTestomatioProject(){

        searchForProjectManufactureTestomatio();

        selectProjectManufactureTestomatio();

        waitingForProjectMTToLoad();

        searchForSmokyTesting();

        SelenideElement smokyTest = countOfSmokyTestsShouldBeEquakTo(2);

        countOfTestsShouldBeEqualTo(smokyTest, "small.text-gray-500", "[^0-9]", 2);

    }

    private static void searchForSmokyTesting() {
        $("#search").setValue("smoky");
    }

    private static void countOfTestsShouldBeEqualTo(SelenideElement smokyTest, String s, String regex, int expected) {
        String testCountText = smokyTest.$(s).getText();
        String numberFromSmoky = testCountText.replaceAll(regex, "");
        Integer actualTestCount = Integer.parseInt(numberFromSmoky);
        Assertions.assertEquals(expected, actualTestCount);
    }

    @NotNull
    private static SelenideElement countOfSmokyTestsShouldBeEquakTo(int expectedCount) {
        return $$(".nestedItem-title").findBy(Condition.text("smoky")).shouldBe(visible);
    }

    private static void waitingForProjectMTToLoad() {
        $(Selectors.byText("Manufacture Testomatio")).shouldBe(visible);
    }

    private static void selectProjectManufactureTestomatio() {
        $(Selectors.byText("Manufacture Testomatio")).click();
    }

    private static void searchForProjectManufactureTestomatio() {
        $("#search").setValue("Manufacture Testomatio");
    }

    private static void waitingForProjectToLoad(String targetProjectName) {
        $(".first h2").shouldHave(Condition.text(targetProjectName));
        $(".first [href*='/readme']").shouldHave(Condition.text("Readme"));
    }

    private static void selectProject(String targetProjectName) {
        $(byText(targetProjectName)).click();
    }

    private static void searchForProject(String targetProjectName) {
        $("#search").setValue(targetProjectName);
    }

    private static void countOfTestCasesShouldBeEqualTo(SelenideElement targetProject, int expectedCount) {
        String countOfTests = targetProject.$("p").getText();
        String digitText = countOfTests.replaceAll("\\D+","" );
        Integer actualCountOfTests = Integer.parseInt(digitText);
        Assertions.assertEquals(0, actualCountOfTests);
    }

    @NotNull
    private static ElementsCollection countOfProjectsShouldBeEqualTo(int expectedSize) {
        return $$("#grid ul li").filter(visible).shouldHave(CollectionCondition.size(expectedSize));
    }

    public static void loginUser(String email, String password) {
        $("#content-desktop #user_email").setValue(email);
        $("#content-desktop #user_password").setValue(password);
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=\"commit\"]").click();
        $(".common-flash-success").shouldBe(visible);
    }
}
