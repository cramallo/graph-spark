package entrypoints.impl;

import core.usecases.graphql.GraphQLService;
import entrypoints.GraphQLEndpoint;
import entrypoints.request.GraphQLEndpointRequest;
import graphql.ExecutionResult;

import javax.inject.Inject;

public class GraphQLEndpointImpl implements GraphQLEndpoint {

    private final GraphQLService graphQLService;

    @Inject
    public GraphQLEndpointImpl(final GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @Override
    public Response post(final GraphQLEndpointRequest request) {
        final ExecutionResult response = graphQLService.execute(request.getQuery(), request.getVariables(), request.getContext());
        return Response.builder().response(response.toSpecification()).build();
    }
}
