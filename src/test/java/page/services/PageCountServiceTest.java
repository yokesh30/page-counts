package page.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import page.entity.PageCountsEntity;
import page.repository.PageCountsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageCountServiceTest {
    @Mock
    PageCountsRepository pageCountsRepository;

    @InjectMocks
    PageCountService pageCountService;

    @Test
    public void getgetPageCounts_returnsEmptyPageCountsEntity() {
        List<PageCountsEntity> pageCountsEntities = new ArrayList<>();

        when(pageCountsRepository.findAll())
                .thenReturn(pageCountsEntities);

        List<PageCountsEntity> pageCounts = pageCountService.getPageCounts();
        assertThat(pageCounts.size()).isEqualTo(0);
    }

    @Test
    public void getgetPageCounts_returnsPageCountsEntity() {
        List<PageCountsEntity> pageCountsEntities = new ArrayList<>();
        pageCountsEntities.add(new PageCountsEntity(1,"en","page",1,"start","end", LocalDate.now()));
        when(pageCountsRepository.findAll())
                .thenReturn(pageCountsEntities);

        List<PageCountsEntity> pageCounts = pageCountService.getPageCounts();
        assertThat(pageCounts.size()).isEqualTo(1);
        assertThat(pageCounts.get(0).getPage()).isEqualTo("page");
        assertThat(pageCounts.get(0).getLanguage()).isEqualTo("en");
    }
}