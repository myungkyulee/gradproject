package project.gradproject;

import org.junit.jupiter.api.Test;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserWaitingTest {
    HttpServletRequest request;
    @Test
    void a(){
        List<User> users = new ArrayList<User>();
        System.out.println(users.size());
        users.add(new User());

        System.out.println(users.size());
    }
}
