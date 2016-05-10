package com.kevinkotowski.server;

/**
 * Created by kevinkotowski on 5/6/16.
 */
public class MockNetwork implements IONetwork {
    private int requestCount = 0;

    public MockNetwork() {
        this.writeln("Waiting...");
    }

    public int getPort() {
        return 3210;
    }

    public boolean hasNext() {
        boolean response = false;
        if (this.requestCount < 1) {
            this.requestCount++;
            response = true;
        }
        return response;
    }

    public IORequest next() {
        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        request.setPath( "/src/test/java/com/kevinkotowski/server/test.htm" );
        return request;
    }

    public void write(byte b) {
        String s = new String(new byte[] { b });
        System.out.print( s );
    }

    public void writeln(String message) {
        System.out.println("MockNetowrk: " + message);
    }

    public void close() {}
    public void closeOut() {}
}
