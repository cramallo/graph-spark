package configuration.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import core.usecases.graphql.GraphQLService;
import core.usecases.graphql.impl.GraphQLServiceImpl;
import core.usecases.graphql.resolvers.StudentResolver;
import core.usecases.graphql.student.StudentRetrieveProvider;
import core.usecases.graphql.student.impl.DefaultStudentRetrieveProvider;
import graphql.kickstart.tools.GraphQLResolver;

public class EndpointConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        bind(GraphQLService.class).to(GraphQLServiceImpl.class);
        bind(StudentRetrieveProvider.class).to(DefaultStudentRetrieveProvider.class);
        Multibinder<GraphQLResolver<?>> graphResolvers =
                Multibinder.newSetBinder(binder(), new TypeLiteral<GraphQLResolver<?>>() {
                });
        graphResolvers.addBinding().to(StudentResolver.class);
    }
}
