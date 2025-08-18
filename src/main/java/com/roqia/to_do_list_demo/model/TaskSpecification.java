package com.roqia.to_do_list_demo.model;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<Task> hasFieldEqualTo(String field,Object value){
        return ((root, query, criteriaBuilder) ->criteriaBuilder.equal(root.get(field),value) );
    }
    public static Specification<Task> hasFieldLike(String field,Object value){
        return ((root, query, criteriaBuilder) ->
        {
        if (value instanceof String) {
            return criteriaBuilder.like(root.get(field), "%" + value + "%");
        } else {
            return criteriaBuilder.equal(root.get(field), value);
        }});

    }
}
