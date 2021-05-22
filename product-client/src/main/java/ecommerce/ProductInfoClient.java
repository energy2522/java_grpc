package ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProductInfoClient {

    public static void main(String[] args) {
        ManagedChannel mg = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub blockingStub = ProductInfoGrpc.newBlockingStub(mg);

        ProductID productID = blockingStub.addProduct(Product.newBuilder()
                .setName("Apple")
                .setDescription("Description")
                .setPrice(1000.0f)
                .build()
        );

        System.out.println(productID.getValue());

        Product product = blockingStub.getProduct(productID);
        System.out.println(product.toString());
        mg.shutdown();
    }
}
