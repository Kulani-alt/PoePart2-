package registrationandlogin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(true, user.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {

        login user = new login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(false, user.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexity() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(true, user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {

        login user = new login(
                "Kyl_1",
                "password",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(false, user.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(true, user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "Kyle",
                "Smith"
        );

        assertEquals(false, user.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulLogin() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertTrue(
                user.loginUser(
                        "Kyl_1",
                        "Ch&&sec@ke99!"
                )
        );
    }

    @Test
    public void testFailedLogin() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertFalse(
                user.loginUser(
                        "wrong",
                        "wrong"
                )
        );
    }

    @Test
    public void testRegistrationSuccessMessage() {

        login user = new login(
                "Kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Username successfully captured. Password successfully captured. Cell phone number successfully added.",
                user.registerUser()
        );
    }

    @Test
    public void testIncorrectUsernameMessage() {

        login user = new login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser()
        );
    }

    @Test
    public void testIncorrectPasswordMessage() {

        login user = new login(
                "Kyl_1",
                "password",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.registerUser()
        );
    }
}