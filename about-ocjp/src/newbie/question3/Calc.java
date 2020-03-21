package newbie.question3;

public class Calc {

    public static void main(String[] args) {
        int n1 = 22, n2 = 2;
        // insert code here

//        Calc c = new Calc();
//        int n3 = c.findMid(n1,n2);

//        int n3 = super.findMid(n1,n3);

//        Calc c = new Mid();
//        int n3 = c.findMid(n1, n2);

//        Mid m1 = new Calc();
//        int n3 = m1.findMid(n1, n2);

//        int n3 = Calc.findMid(n1, n2);
        System.out.print(n3);


        // Which two code fragments, when inserted at // insert code here, enable the code to
        // compile and print 12?

        // A. Calc c = new Calc();
        // int n3 = c.findMid(n1,n2);
        // B. int n3 = super.findMid(n1,n3);
        // C. Calc c = new Mid();
        // int n3 = c.findMid(n1, n2);
        // D. Mid m1 = new Calc();
        // int n3 = m1.findMid(n1, n2);
        // E. int n3 = Calc.findMid(n1, n2);

        // Answer: A,D

        // B: super 不能在静态的上下文中使用，n3 循环定义
        // C: Compilation error. line Calc c = new Mid();
        //  InCompatible Types：
        //      required: Calc
        //      found: Mid
        // E:  Compilation error. line int n3 = Calc.findMid(n1, n2);
        // non-static method findMid(int,int) cannot be referenced from a static context
    }
}
