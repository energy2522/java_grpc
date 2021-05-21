package ecommerce;

import java.io.IOException;

import io.grpc.ServerBuilder;

public class ProductInfoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        var server = ServerBuilder.forPort(port)
                .addService(new ProductInfoImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            if (server != null) {
                server.shutdown();
            }
            System.err.println("Server shut down");
        }));
        server.awaitTermination();
    }
}
