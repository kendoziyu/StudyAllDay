package classes;

public class CustomClassLoaderTest {

    public static void main(String[] args) {
        CustomClassLoader loader1 = new CustomClassLoader();
        CustomClassLoader loader2 = new CustomClassLoader();

        try {
            Class c1 = loader1.loadClass("Child");
            Class c2 = loader2.loadClass("Child");

            System.out.println(c1.getClassLoader());
            System.out.println(c2.getClassLoader());
            System.out.println(c2.equals(c1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
