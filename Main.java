package ua.kiev.prog;

import javax.persistence.*;
import java.util.List;


// 1.Выбрать всех студентов, которые учатся на курсе Х и проживают в городе У.
// 2.Вывести на экран список групп с указанием количества студентов в каждой группе.
// 3.Создать новый курс на котором занимается 3 группы по 5 студентов.
public class Main
{
    public static void main(String[] args)
    {
        RandomData randomData = new RandomData();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPATest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Student student = null;
        Address address = null;
        Query query = null;

        try {
            entityManager.getTransaction().begin();

            for (Group group : randomData.getGROUPS())
                entityManager.persist(group);

            for (Course course : randomData.getCOURSES())
                entityManager.persist(course);


            // Создаем случайным образом 20 студентов
            for (int i = 0; i < 20; i++) {
                address = randomData.randomAddress();
                entityManager.persist(address);

                student = randomData.randomStudent();
                student.addCourse(randomData.randomCourse());
                student.setGroup(randomData.randomGroup());
                student.setAddress(address);

                entityManager.persist(student);
            }


            // Выводим данные о всех студентах
            System.out.println("\nСПИСОК ВСЕХ СТУДЕНТОВ:");

            query = entityManager.createQuery("SELECT st FROM Student st", Student.class);
            List<Student> studentList = (List<Student>) query.getResultList();

            for (Student currentStudent : studentList)
                System.out.println(currentStudent.toString());


            // 1.Выбрать всех студентов, которые учатся на курсе Х и проживают в городе У.
            final String courseName = "Java Pro";
            final String cityName = "Kyiv";
            final String queryString1 = "SELECT st FROM Student st JOIN st.courses cs " +
                                        "WHERE cs.name = :coursename AND st.address.city = :cityname";

            System.out.println("\n1. Студенты, которые учатся на курсе " + courseName +
                               " и проживают в городе " + cityName + ":");

            query = entityManager.createQuery(queryString1, Student.class);
            query.setParameter("cityname", cityName);
            query.setParameter("coursename", courseName);
            List<Student> studentList1 = (List<Student>) query.getResultList();

            for (Student currentStudent : studentList1)
                System.out.println(currentStudent.toString());


            // 2.Вывести на экран список групп с указанием количества студентов в каждой группе.
            System.out.println("\n2. Список групп и число студентов в каждой группе:");
            query = entityManager.createQuery("SELECT g.name, g.students.size FROM Group g");
            List<Object[]> studentList2 = query.getResultList();

            for (Object[] objects : studentList2)
                System.out.println((String) objects[0] + " - " + (int) objects[1] + " студентов;");


            // 3.Создать новый курс на котором занимается 3 группы по 5 студентов.
            Course newCourse = new Course("Java QA");
            entityManager.persist(newCourse);

            for (Group group : randomData.getGROUPS()) {
                for (int i = 0; i < 5; i++) {
                    address = randomData.randomAddress();
                    entityManager.persist(address);

                    student = randomData.randomStudent();
                    student.addCourse(newCourse);
                    student.setGroup(group);
                    student.setAddress(address);

                    entityManager.persist(student);
                }
            }

            final String queryString3 = "SELECT c FROM Student c JOIN c.courses cs " +
                                        "WHERE cs.name = :coursename";
            query = entityManager.createQuery(queryString3, Student.class);
            query.setParameter("coursename", "Java QA");
            List<Student> studentList3 = (List<Student>) query.getResultList();

            System.out.println("\n3. Новый курс на котором занимается 3 группы по 5 студентов:");

            for (Student currentStudent : studentList3)
                System.out.println(currentStudent.toString());

            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
