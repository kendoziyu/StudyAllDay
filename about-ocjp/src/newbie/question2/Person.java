package newbie.question2;

public class Person {

    String name;
    int age = 15;

    public Person(String name) {
        this();  // line 1
    }

    public Person(String name, int age) {
        Person(name);   // line 2
        setAge(age);
    }

    public String show() {
        return name + " " + age + " " + number;
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

    public static void main(String[] args) {
        Person test1 = new Person("Jesse");
        Person test2 = new Person("Walter", 52);
        System.out.println(test1.show());
        System.out.println(test2.show());

        // What is the result?

        // A. Jesse 25
        // Walter 52
        // B. Compilation fails only at line n1
        // C. Compilation fails only at line n2
        // D. Compilation fails at both line n1 and line n2

        // Answer: D
        // Java 文件中有错误代码就会 编译失败（Compilation fails）
        // 由于定义了构造器，因此编译器就不会帮我们自动生成无参构造函数了，因此 line 1 处 this() 找不到合适的构造器
        // line 2 处使用 Person(name) 是找不到方法的，正确是使用构造器的方法是 new Person(name)
        // 其实 number 也是找不到符号的，也会编译错误
    }
}
