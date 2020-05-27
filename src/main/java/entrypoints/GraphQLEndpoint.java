package entrypoints;

import entrypoints.request.GraphQLEndpointRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public interface GraphQLEndpoint {
    Response post(final GraphQLEndpointRequest request);

    @Builder
    @Getter
    class Response {
        Map<String, Object> response;
    }
}
