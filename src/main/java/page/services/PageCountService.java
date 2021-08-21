package page.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import page.entity.PageCountsEntity;
import page.repository.PageCountsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PageCountService {
    private PageCountsRepository pageCountsRepository;

    public PageCountService(PageCountsRepository pageCountsRepository) {
        this.pageCountsRepository = pageCountsRepository;
    }

    public List<PageCountsEntity> getPageCounts() {
        return pageCountsRepository.findAll();

    }
}
