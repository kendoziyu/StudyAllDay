package newbie.question2;

public class RightPerson {
    String name;
    int age = 15;

    public RightPerson(String name) {
        setName(name);  // line 1
    }

    public RightPerson(String name, int age) {
        new RightPerson(name);   // line 2
        setAge(age);
    }


    // getter and setter here
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String show() {
        return name + " " + age;
    }
    public static void main(String[] args) {
        RightPerson test1 = new RightPerson("Jesse");
        RightPerson test2 = new RightPerson("Walter", 52);
        System.out.println(test1.show());
        System.out.println(test2.show());

        //> Jesse 15
        //null 52

    }
}
