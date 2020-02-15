package silver;

/**
 * <p>
 * 自增自减运算符
 * </p>
 *
 * @author zhangzeyu
 * @date 2020/2/12 20:40
 */
public class Question1 {

    public static void main(String[] args) {
        boolean aBol = true;
        int aVar = 9;
        System.out.println(~aVar);
        if (aVar++ < 10) {
            System.out.println(aVar + " Hello World!");
        } else {
            System.out.println(aVar + " Hello Universe!");
        }
        // What is the result ?
        // A. 10 Hello World!
        // B. Hello Universe!
        // C. Hello World!
        // D. Compilation fails.

        // Answer:A

        // 知识点：自增自减运算符的前缀后缀写法
        // 前缀自增自减法(++a,--b): 先进行自增或者自减运算，再进行表达式运算。
        // 后缀自增自减法(a++,b--): 先进行表达式运算，再进行自增或者自减运算。

        // 疑问：单目运算符运算优先级不是高于关系运算符吗？
        // 没错，首先得承认单目运算符优先级确实高于关系运算符，
        // 但是后缀自增自减法有它的一套规则(即先进行表达式运算，后自增自减运算)，
        // 编译器也是按照既定的规则来安排指令的
    }
}
