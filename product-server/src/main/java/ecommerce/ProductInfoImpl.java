package ecommerce;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {
    private Map<String, Product> productMap = new HashMap<>();

    @Override
    public void addProduct(Product request, StreamObserver<ProductID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        var randomUUIDString = uuid.toString();
        productMap.put(randomUUIDString, request);
        var productId = ProductID.newBuilder()
                .setValue(randomUUIDString).build();

        responseObserver.onNext(productId);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductID request, StreamObserver<Product> responseObserver) {
        var id = request.getValue();
        Optional.ofNullable(productMap.get(id)).ifPresentOrElse(product -> {
                    responseObserver.onNext(product);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(new StatusException(Status.NOT_FOUND)));
    }
}
