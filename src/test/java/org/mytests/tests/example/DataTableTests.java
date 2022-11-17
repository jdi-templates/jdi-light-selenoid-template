package org.mytests.tests.example;

import org.mytests.tests.TestsInit;
import org.mytests.uiobjects.example.entities.MarvelUserInfo;
import org.mytests.uiobjects.example.site.custom.MarvelUser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jdiai.tools.StringUtils.LINE_BREAK;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mytests.tests.preconditions.Preconditions.shouldBeLoggedIn;
import static org.mytests.uiobjects.example.TestData.SPIDER_MAN;
import static org.mytests.uiobjects.example.site.SiteJdi.usersPage;
import static org.mytests.uiobjects.example.site.pages.UsersPage.users;
import static org.mytests.uiobjects.example.site.pages.UsersPage.usersSetup;
import static org.testng.Assert.assertEquals;

public class DataTableTests extends TestsInit {
    @BeforeMethod
    public void before() {
        shouldBeLoggedIn();
        usersPage.open();
    }

    @Test
    public void dataTableTest() {
        assertEquals(users.size(), 4);
        assertEquals(users.count(), 6);
        assertEquals(users.header(), asList("Number", "Type", "User", "Description"));

        String value = users.preview();
        assertEquals(value,
                "Number Type User Description 1 Admin User Manager Roman Wolverine Vip 2 Admin User Manager Sergey Ivan Spider Man Vip 3 Admin User Manager Vladzimir Punisher Vip 4 Admin User Manager Helen Bennett Captain America some description Vip 5 Admin User Manager Yoshi Tannamuri Cyclope some description Vip 6 Admin User Manager Giovanni Rovelli Hulk some description Vip");
        value = users.getValue();
        assertEquals(value,
        "||X||Number|Type|User|Description||" + LINE_BREAK +
            "||1||1|Admin|Roman|Wolverine:VIP||" + LINE_BREAK +
            "||2||2|User|Sergey Ivan|Spider Man:Dude||" + LINE_BREAK +
            "||3||3|Manager|Vladzimir|Punisher:VIP||" + LINE_BREAK +
            "||4||4|User|Helen Bennett|Captain America\\nsome description:Dude||" + LINE_BREAK +
            "||5||5|User|Yoshi Tannamuri|Cyclope\\nsome description:Dude||" + LINE_BREAK +
            "||6||6|User|Giovanni Rovelli|Hulk\\nsome description:Dude||" + LINE_BREAK);
    }
    @Test
    public void filterDataTest() {
        assertEquals(users.dataRow(2), SPIDER_MAN);
        assertEquals(usersSetup.dataRow("Sergey Ivan"), SPIDER_MAN);
        assertEquals(users.dataRow(d -> d.user.contains("Ivan")), SPIDER_MAN);

        List<MarvelUserInfo> filteredData = users.dataRows(d -> d.user.contains("Ivan"));
        assertEquals(filteredData.size(), 1);
        assertEquals(filteredData.get(0), SPIDER_MAN);
    }
    @Test
    public void tableAssertsTest() {
        users.is().displayed();
        users.has().size(6);
        users.assertThat().size(greaterThan(3));
        users.is().size(lessThanOrEqualTo(6));
        users.is().notEmpty();
        users.has().row(d -> d.user.contains("Ivan"));
        users.assertThat().row(d -> d.user.length() > 4);
        users.assertThat().atLeast(3).rows(d -> d.type.contains("User"));
        users.assertThat().exact(2).rows(d -> d.description.contains(":VIP"));
        users.has().row(SPIDER_MAN);
        users.assertThat().exact(1).rows(SPIDER_MAN);
    }

    @Test
    public void filterLinesTest() {
        MarvelUser line = users.line(2);
        validateUserRow(line);
        line = usersSetup.line("Sergey Ivan");
        validateUserRow(line);
        line =  users.line(d -> d.user.contains("Ivan"));
        validateUserRow(line);

        List<MarvelUser> filteredData = users.lines(d -> d.user.contains("Ivan"));
        assertEquals(filteredData.size(), 1);
        validateUserRow(filteredData.get(0));
        usersPage.open();
    }

    private void validateUserRow(MarvelUser line) {
        line.type.select("Admin");
        assertEquals(line.type.getValue(), "Admin");
        line.type.select("User");
        line.number.assertThat().text(is("2"));
    }
}