package com.augustogabriel.taskmanager.specification;

import com.augustogabriel.taskmanager.domain.Task;
import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> taskSpecification(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status"), status);
   }

   public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                priority == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("priority"), priority);
   }

   public static Specification<Task> matchesQuery(String query) {
       return (root, criteriaQuery, criteriaBuilder) -> {
           if (query == null || query.isBlank())
               return criteriaBuilder.conjunction();
           String likeQuery = "%" + query.toLowerCase() + "%";
           return criteriaBuilder.or(
                   criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeQuery),
                   criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likeQuery)
           );
       };
   }
}
