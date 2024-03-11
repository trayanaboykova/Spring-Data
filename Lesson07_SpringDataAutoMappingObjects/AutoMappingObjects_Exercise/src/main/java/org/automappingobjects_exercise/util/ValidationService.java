package org.automappingobjects_exercise.util;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationService {

    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> validate(E entity);
}
