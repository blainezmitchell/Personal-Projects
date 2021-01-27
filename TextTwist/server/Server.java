import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerStream.constructServerSocket();

        while (true) {
            MyRunnable myRunnable = new MyRunnable();
            Thread myThread = new Thread(myRunnable);
            myThread.start();
        }
    }
}