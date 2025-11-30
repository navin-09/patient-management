package org.example.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import billing.BillingServiceGrpc.BillingServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BilingServiceGrpcClient {
    private final BillingServiceBlockingStub blockingStub;
    public BilingServiceGrpcClient(@Value("${billing.service.address:localhost}") String address,
                                   @Value("${billing.service.grpc.port:9001}") int port ) {
        log.info("Connecting to billing service...at {}:{}", address, port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

    }

    public BillingResponse createBillingAccount(String patientId, String email, String name) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Create billing account response: {}", response);
        return response;

    }
}
