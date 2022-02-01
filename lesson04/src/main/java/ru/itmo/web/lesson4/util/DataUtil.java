package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Color;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.Reference;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.RED),
            new User(6, "pashka", "Pavel Mavrin", Color.BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    private static final List<Reference> LINKS = Arrays.asList(
            new Reference("/index", "Home"),
            new Reference( "/users", "Users"),
            new Reference( "/contests", "Contests"),
            new Reference( "/misc/help", "Help")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces round #747 (Div. 2)",  1, "Dremix10 and I are glad to invite you to the first Cypriot round Codeforces Round #747 (Div. 2) which will take place on Friday, October 8, 2021 at 18:05. Note the unusual start time of the round. This round will be rated for participants with rating lower than 2100.\n" +
                    "\n" +
                    "Problems were created and prepared by Dremix10 and me. We tried to make them interesting, with short and clear statements. We hope that you will enjoy them!\n" +
                    "\n" +
                    "I would like to thank:\n" +
                    "\n" +
                    "adedalic for amazing coordination of this round.\n" +
                    "\n" +
                    "Dremix10 for giving the first feedback about every problem I created.\n" +
                    "\n" +
                    "Markadiusz, cfalas, m371, ijxjdjd, YouKn0wWho, PurpleCrayon, flamestorm, Shinchan01, rgnerdplayer, awoo, Hyperbolic, Nots0fast for testing the problems and providing useful feedback.\n" +
                    "\n" +
                    "MikeMirzayanov for Codeforces and Polygon platforms.\n" +
                    "\n" +
                    "You will be given 6 problems (and one subtask) and 2 hours and 15 minutes to solve them. Scoring distribution will be announced later.\n" +
                    "\n" +
                    "Good luck and have fun!"),
            new Post(2, "Kotlin Heroes 8 Announcement",  9, "Hello, Codeforces!\n" +
                    "\n" +
                    "First and foremost, we would like to say a massive thank you to everyone who entered and submitted their answers to the seven Kotlin Heroes competitions which were held previously: Episode 1, Episode 2, Episode 3, Episode 4, Episode 5: ICPC Round, Episode 6, and Episode 7.\n" +
                    "\n" +
                    "Ready to challenge yourself to do better? The Kotlin Heroes: Episode 8 competition will be hosted on the Codeforces platform on четверг, 7 октября 2021 г. в 17:35. The contest will last 2 hours 30 minutes and will feature a set of problems from simple ones, designed to be solvable by anyone, to hard ones, to make it interesting for seasoned competitive programmers.\n" +
                    "\n" +
                    "Prizes:\n" +
                    "\n" +
                    "Top three winners will get prizes of $512, $256, and $128 respectively, top 50 will win a Kotlin Heroes t-shirt and an exclusive Kotlin sticker, competitors solving at least one problem will enter into a draw for one of 50 Kotlin Heroes t-shirts.\n" +
                    "\n" +
                    "Registration is already open and available via the link. It will be available until the end of the round.\n" +
                    "\n"),
            new Post(3, "ICPC World Finals Moscow",  11, "ICPC World Finals Moscow will begin on October 5, 2021 at 8:30 (UTC+3). We are thrilled to invite you to join the live broadcast of the main event of the year in the world of sports programming!\n" +
                    "\n" +
                    "For the very first time in October 2021, Moscow will host the world’s most prestigious competition for young IT talents, the ICPC World Finals Championship. The last International Collegiate Programming Contest has hosted over 60000 students from 3,514 universities in 115 countries that span the globe. October 5 more than 100 teams will compete in logic, mental speed, and strategic thinking at Russia’s main Manege Central Conference Hall.\n" +
                    "\n"),
            new Post(4, "Codeforces round #746 (Div. 2)",  1, "صباحو (Good Morning), Codeforces!\n" +
                    "\n" +
                    "I'm glad to invite you to Codeforces Round #746 (Div. 2), which will be held on воскресенье, 3 октября 2021 г. в 17:35.\n" +
                    "\n" +
                    "This round is rated for the participants with rating lower than 2100.\n" +
                    "\n" +
                    "You will be given 6 problems and 2 hours to solve them. All problems were prepared by me and Bakry_.\n" +
                    "\n" +
                    "One of the problems will be interactive. So, it is recommended to read the guide on interactive problems before the round.\n" +
                    "\n" +
                    "I would like to thank:\n" +
                    "\n" +
                    "antontrygubO_o, for a lot of things (awesome coordination, suggestion for one of the tasks, helping in preparation, rejecting only not interesting tasks, a lot of useful discussions).\n" +
                    "\n" +
                    "KostasKostil for one of the tasks and antontrygubO_o for preparing it.\n" +
                    "\n" +
                    "YahiaSherif, ZeyadKhattab, mahmoudbadawy, Uzumaki_Narutoo, _zaher_ for discussing and testing the problems.\n" +
                    "\n" +
                    "Our army of testers kefaa2, DeadPillow, Naseem17, Mohammad_Yasser, ZerooCool, compiler_101, TheSawan, IsaacMoris, Mohamed.Sobhy, AhmedEzzatG, Ahmed_AIansary, Evro, Sisyy, G.A.T.C, mennafadali, Khater, It_Wasnt_Me, DAleksa, Samez, soba, O_E, AhmedZ, AshrafEzz, Urvuk3, Mo2men, Ahmad.houmani.\n" +
                    "\n" +
                    "MikeMirzayanov, for the amazing Codeforces and Polygon platforms.\n" +
                    "\n" +
                    "The statements are short and we have tried to make the pretests strong. I encourage you to read all the problems.\n" +
                    "\n" +
                    "For people who don't like stories, you will find all the stories written in italic you can skip them safely.\n" +
                    "\n" +
                    "This is our first official round on Codeforces. We are sincerely looking forward to your participation. We hope everyone will enjoy it.\n" +
                    "\n" +
                    "Good luck and see you in the standings!")
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("posts", POSTS);
        data.put("users", USERS);
        data.put("links", LINKS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
