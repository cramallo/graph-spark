package entrypoints.request;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Builder
@Getter
public class GraphQLEndpointRequest {
    private String query;
    private Map<String, Object> variables;
    private Map<String, Object> context;
}
