package com.nerdygadgets.application.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * Utility class for retrieving values from annotations
 *
 * @author Kevin Zuman
 */
public final class AnnotationUtils {

    /**
     * Gets the value of the "value" method of the given {@link Annotation} on the given {@link AnnotatedElement}.
     *
     * @param annotatedElement the {@link AnnotatedElement}.
     * @param annoClass the {@link Annotation} class.
     *
     * @return the value of the annotation on the given {@link AnnotatedElement}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(AnnotatedElement annotatedElement, Class<? extends Annotation> annoClass) {
        try {
            Method method = annotatedElement.getAnnotation(annoClass).getClass().getMethod("value");
            method.setAccessible(true);
            return (T) method.invoke(annotatedElement.getAnnotation(annoClass));
        } catch (Exception ex) {
            Logger.error(ex, "Failed to retrieve value of annotation.");
            return null;
        }
    }

    /**
     * Returns whether the given {@link AnnotatedElement} has the given annotation.
     *
     * @param annotatedElement the annotated element.
     * @param annoClass the annotation.
     *
     * @return {@code true} if the {@link AnnotatedElement} has the given annotation, {@code false} otherwise.
     */
    public static boolean hasAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annoClass) {
        return annotatedElement.getAnnotation(annoClass) != null;
    }
}
