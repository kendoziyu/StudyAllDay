package core.java.lambda;

public class LambdaTest {

    public static void main(String[] args) {
        print((String name, int age)-> System.out.println(String.format("name:%s age:%d", name, age)), "ziyu", 18);
        print((name, age)-> System.out.println(String.format("name:%s age:%d", name, age)), "ziyu", 18);
    }

    public static void print(Formatter formatter, String name, int age) {
        formatter.format(name, age);
    }
}
