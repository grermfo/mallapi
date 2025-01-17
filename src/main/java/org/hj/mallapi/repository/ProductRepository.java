package org.hj.mallapi.repository;

import java.util.Optional;

import org.hj.mallapi.domain.Product;
import org.hj.mallapi.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch{

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);


    @Query("select p , p1 from Product p left join p.imageList p1 where p1.ord=0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
