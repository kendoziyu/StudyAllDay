package classes.b;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/21 0021 <br>
 */
public class Parent {
    static OhMyGod bug = new OhMyGod();

    private String name;

    private int age;

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
