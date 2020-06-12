package core.java.io.bytes.obj;

public class Programmer {

    private String name;

    private int age;

    private Programmer leader;

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

    public Programmer getLeader() {
        return leader;
    }

    public void setLeader(Programmer leader) {
        this.leader = leader;
    }
}
