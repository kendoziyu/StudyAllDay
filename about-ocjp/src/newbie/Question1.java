package newbie;

public class Question1 {


    public static void main(String[] args) {
        String product = "PEN";
        product.toLowerCase();
        product.concat(" BOX".toLowerCase());
        System.out.println(product.substring(4, 6));

        // A. box
        // B. nbo
        // C. bo
        // D. nb
        // E. An exception is thrown at runtime

        // Answer：E
        // String 是 final 类型的，因此需要赋值才能得到新的字符串
        // 因此第8,9行代码并没有产生实际的效果
        // 最终结果是抛出 StringIndexOutOfBoundsException: String index out of range: 6
    }
}
