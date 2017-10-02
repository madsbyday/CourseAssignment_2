package tcp;

import java.io.IOException;

public class ClientTest {
        public static void main(String[] args) throws IOException {
        new Client("127.0.0.1", 6666);
    }
}
