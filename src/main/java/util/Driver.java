package util;

import java.io.IOException;

import com.hit.server.Server;

public class Driver {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Server server = new Server(34567);
		new Thread(server).start();

	}

}
