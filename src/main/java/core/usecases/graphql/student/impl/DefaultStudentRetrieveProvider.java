package core.usecases.graphql.student.impl;

import core.usecases.graphql.student.StudentRetrieveProvider;
import entities.Student;

public class DefaultStudentRetrieveProvider implements StudentRetrieveProvider {
    @Override
    public Student get(Long id) {
        return null;
    }
}
