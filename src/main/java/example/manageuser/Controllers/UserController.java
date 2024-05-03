package example.manageuser.Controllers;

import example.manageuser.Entities.User;
import example.manageuser.Repositories.UserRepository;
import example.manageuser.Repositories.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private static final String[] NAMES = { "Tom", "Jerry", "Donald" };

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    @Autowired
    private UserRepository userrepository;


    @ResponseBody
    @RequestMapping("/")
    public String home() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/testInsert'>Test Insert</a></li>";
        html += " <li><a href='/showAllEmployee'>Show All Employee</a></li>";
        html += " <li><a href='/showFullName'>Show All </a></li>";
        html += " <li><a href='/findUserName'>Show User Name</a></li>";
        html += " <li><a href='/deleteAllEmployee'>Delete All Employee</a></li>";
        html += " <li><a href='/deleteByID'>Delete ID</a></li>";


        html += "</ul>";
        return html;
    }

    @ResponseBody
    @RequestMapping("/testInsert")
    public String testInsert() {
        User employee = new User();

        long id = this.userRepositoryCustom.getMaxUserId() + 1;
        int idx = (int) (id % NAMES.length);
        String fullName = NAMES[idx] + " " + id;

        employee.setId(id);
        employee.setUserNo("E" + id);
        employee.setFullName(fullName);
        employee.setHireDate(new Date());
        this.userrepository.insert(employee);

        return "Inserted: " + employee;
    }

    @ResponseBody
    @RequestMapping("/showAllEmployee")
    public String showAllEmployee() {

        List<User> users = this.userrepository.findAll();

        StringBuilder html = new StringBuilder();
        for (User user : users) {
            html.append(user).append("<br>");
        }

        return html.toString();
    }

    @RequestMapping("/findUserNo")
    public User findByUserNo(@RequestParam String userNo) {
        return userrepository.findByUserNo(userNo);
    }

    @GetMapping("/findHireDate")
    public String findHireDate(@RequestParam Date hireDate) {
        List<User> users = userrepository.findByHireDateGreaterThan(hireDate);

        StringBuilder html = new StringBuilder();
        for (User user : users) {
            html.append(user).append("<br>");
        }

        return html.toString();
    }
    @ResponseBody
    @RequestMapping("/showFullNameLikeTom")
    public String showFullNameLikeTom() {

        List<User> users = this.userrepository.findByFullNameLike("Tom");

        StringBuilder html = new StringBuilder();
        for (User usr : users) {
            html.append(usr).append("<br>");
        }

        return html.toString();
    }


    @ResponseBody
    @RequestMapping("/deleteAllEmployee")
    public String deleteAllEmployee() {

        this.userrepository.deleteAll();
        return "Deleted!";
    }

    @ResponseBody
    @GetMapping("/deleteByID")
    public String removeByIdAndReturnHtml() {
        Long id = 1L;
        this.userrepository.removeById(id);
        return id +"Deleted User!";
    }


}



