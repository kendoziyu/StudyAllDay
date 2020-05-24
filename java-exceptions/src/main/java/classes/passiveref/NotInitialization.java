package classes.passiveref;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/22 0022 <br>
 */
public class NotInitialization {

    public static void main(String[] args) {
        // 常量在编译阶段会存入常量池，本质上并没有直接应用到定义变量的类
        // 因此不会触发类的初始化
        System.out.println(SubClass.VALUE);
        // 通过数组定义来引用类，不会触发类的初始化
        SuperClass[] array = new SuperClass[10];
    }
}
