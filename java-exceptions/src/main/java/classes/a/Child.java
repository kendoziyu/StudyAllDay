package classes.a;

import classes.b.Parent;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/21 0021 <br>
 */
public class Child {

    private Parent parent;

    private String name;

    public Child(String name, Parent parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Child{" +
                "parent=" + parent +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Parent parent = new Parent("laowang", 50);
        Child child = new Child("xiaoming", parent);
        System.out.println(child);
    }
}
