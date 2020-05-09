package com.example.transvehobe.common.projection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomProjectionFactory {

    private final ProjectionFactory factory;

    public <T> List<T> createProjection(Class<T> projectionType, Collection<?> sources) {
        return sources.stream().map(source -> factory.createProjection(projectionType, source)).collect(Collectors.toList());
    }

    public <T> T createProjection(Class<T> projectionType, Object source) {
        return factory.createProjection(projectionType, source);
    }
}

