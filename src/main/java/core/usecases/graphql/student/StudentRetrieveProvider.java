package core.usecases.graphql.student;

import com.google.inject.ImplementedBy;
import core.usecases.graphql.student.impl.DefaultStudentRetrieveProvider;
import entities.Student;

@FunctionalInterface
@ImplementedBy(DefaultStudentRetrieveProvider.class)
public interface StudentRetrieveProvider {
    Student get(final Long id);
}
