package page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import page.entity.PageCountsEntity;

import java.util.List;

@Repository
public interface PageCountsRepository extends JpaRepository<PageCountsEntity, Long> {
    List<PageCountsEntity> findAll();
}
