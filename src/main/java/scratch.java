public class scratch {

    public void func(String params) {
        final String myparam = params;
        System.out.println(myparam);
    }

    public static void main(String[] args) {
        scratch s = new scratch();
        s.func("hi");
        s.func("hello");
    }
}