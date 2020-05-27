package configuration.injectors;

import com.google.inject.AbstractModule;
import entrypoints.GraphQLEndpoint;
import entrypoints.impl.GraphQLEndpointImpl;

public class RouterConfiguration extends AbstractModule {
    @Override
    protected void configure() {
        bind(GraphQLEndpoint.class).to(GraphQLEndpointImpl.class);
    }
}
