package ua.kiev.prog;


import java.util.Random;

public class RandomData
{
    private final Random RND = new Random();

    private final String[] NAMES = {"Viktor", "Sergey", "Andrey", "Tamara", "Oksana"};
    private final String[] SURNAMES = {"Ivanenko", "Petrenko", "Vakulenko", "Sergienko", "Radchenko"};
    private final String[] CITIES = {"Kyiv", "Lviv", "Odessa", "Kharkiv"};
    private final String[] TEL_CODES = {"(050)", "(095)", "(066)", "(067)", "(068)", "(097)"};

    private final Course[] COURSES = {new Course("Java Start"),
                                      new Course("Java OOP"),
                                      new Course("Java Pro")};

    private final Group[] GROUPS = {new Group("Group-1", "First group"),
                                    new Group("Group-2", "Second group"),
                                    new Group("Group-3", "Third group")};

    private String name;
    private String surname;
    private String email;
    private String phone;

    private String randomPhone()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(TEL_CODES[RND.nextInt(TEL_CODES.length)]);

        for (int i = 0; i < 7; i++) {
            sb.append(RND.nextInt(10));
            if (i == 2 || i == 4)
                sb.append("-");
        }

        return sb.toString();
    }


    public RandomData() {}

    public Course[] getCOURSES() { return COURSES; }
    public Group[] getGROUPS() { return GROUPS; }

    public Student randomStudent()
    {
        name = NAMES[RND.nextInt(NAMES.length)];
        surname = SURNAMES[RND.nextInt(SURNAMES.length)];
        email = name.toLowerCase() + "-" + surname.toLowerCase() + "@mydomain.com";
        phone = randomPhone();

        return new Student(name, surname, email, phone);
    }

    public Group randomGroup() { return GROUPS[RND.nextInt(GROUPS.length)]; }
    public Course randomCourse() { return COURSES[RND.nextInt(COURSES.length)]; }

    public Address randomAddress()
    {
        return new Address("UA", CITIES[RND.nextInt(CITIES.length)], RND.nextInt(300));
    }
}
