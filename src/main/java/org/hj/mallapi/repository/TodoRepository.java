package org.hj.mallapi.repository;

import org.hj.mallapi.domain.*;
import org.hj.mallapi.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo , Long> ,TodoSearch{}
