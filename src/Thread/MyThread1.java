package Thread;

public class MyThread1 extends Thread {
    private String name;

    public MyThread1 (String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++){
            System.out.println(name + i);

        }
    }

    public static void main(String[] args) {
        MyThread1 myThread1 = new MyThread1("A");
        myThread1.start();
        MyThread1 myThread12 = new MyThread1("b");
        myThread12.start();
    }

}
