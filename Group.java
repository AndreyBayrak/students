package ua.kiev.prog;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="Groups")
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="comment")
    private String comment;

    @Column(name="date")
    private Date date;

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<Student>();

    public Group() {}

    public Group(String name, String comment)
    {
        this.name = name;
        this.comment = comment;
    }

    public void addClient(Student student)
    {
        students.add(student);
        if (student.getGroup() != this)
            student.setGroup(this);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
