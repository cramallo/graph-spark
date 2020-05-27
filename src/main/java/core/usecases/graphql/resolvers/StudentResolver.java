package core.usecases.graphql.resolvers;

import core.usecases.graphql.student.StudentRetrieveProvider;
import entities.Student;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.CompletableFuture;

public class StudentResolver implements GraphQLResolver<Student> {

    @Inject
    private Provider<StudentRetrieveProvider> studentRetrieveProviderProvider;

    public CompletableFuture<Student> student (Long id, DataFetchingEnvironment dataFetchingEnvironment) {
        return CompletableFuture.supplyAsync(() -> {
            if (id == null) {
                return null;
            }
            Student student = studentRetrieveProviderProvider.get().get(id);
            return student;
        }).exceptionally(t -> {
            return null;
        });
    }

}
