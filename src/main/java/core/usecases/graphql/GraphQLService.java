package core.usecases.graphql;

import graphql.ExecutionResult;

import java.util.Map;

public interface GraphQLService {
    //String REQUEST_CONTEXT_ARGUMENT_KEY = "request_context";
    ExecutionResult execute(final String query, final Map<String, Object> variables, final Map<String, Object> context);
}
