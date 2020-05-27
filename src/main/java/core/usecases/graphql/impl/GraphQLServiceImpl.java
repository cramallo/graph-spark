package core.usecases.graphql.impl;

import com.google.common.collect.Lists;
import core.usecases.graphql.GraphQLService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

@Singleton
public class GraphQLServiceImpl implements GraphQLService {
    private final Set<GraphQLResolver<?>> resolvers;
    private final GraphQLSchema graphQLSchema;

    @Inject
    public GraphQLServiceImpl(final Set<GraphQLResolver<?>> resolvers) {
        this.resolvers = resolvers;
        this.graphQLSchema = buildSchema();
    }

    @Override
    public ExecutionResult execute(final String query, final Map<String, Object> variables,
                                   final Map<String, Object> context) {
        final ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .context(context)
                .build();
        final ExecutionResult executionResult = getGraphQL().execute(executionInput);

        return executionResult;
    }

    private GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .files(getSchemas())
                .resolvers(Lists.newArrayList(resolvers))
                .build()
                .makeExecutableSchema();
    }

    private String[] getSchemas() {
        return new String[]{"student.graphqls", "schema.graphqls"};
    }

    private GraphQL getGraphQL() {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
