package test;
import java.util.*;

public class Person
    implements Iterable<Person>
{
    public Person(String fn, String ln, int a, Person... kids)
    {
        this.firstName = fn; this.lastName = ln; this.age = a;
        for (Person kid : kids)
            children.add(kid);
    }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public int getAge() { return this.age; }
    public List<Person> getChildren() { return children; }
    
    public Iterator<Person> iterator() { return children.iterator(); }
    
    public void setFirstName(String value) { this.firstName = value; }
    public void setLastName(String value) { this.lastName = value; }
    public void setAge(int value) { this.age = value; }
    
    public String toString() { 
        return "[Person: " +
            "firstName=" + firstName + " " +
            "lastName=" + lastName + " " +
            "age=" + age + "]";
    }
    
    private String firstName;
    private String lastName;
    private int age;
    private List<Person> children = new ArrayList<Person>();
}
