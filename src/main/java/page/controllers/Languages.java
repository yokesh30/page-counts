package page.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.entity.PageCountsEntity;
import page.services.PageCountService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@RestController
public class Languages {
    private PageCountService pageCountService;

    public Languages(PageCountService pageCountService) {
        this.pageCountService = pageCountService;
    }

    @GetMapping("/languages/top10")
    public List<PageCountsEntity> getTopLanguagesByLanguage() {
        List<PageCountsEntity> pageCounts = pageCountService.getPageCounts();
        Comparator<PageCountsEntity> compareByLanguage = Comparator.comparing(PageCountsEntity::getLanguage);
        Comparator<PageCountsEntity> compareByCount = Comparator.comparing(PageCountsEntity::getCount).reversed();
        Comparator<PageCountsEntity> compareByLanguageAndCount = compareByLanguage.thenComparing(compareByCount);
        List<PageCountsEntity> sorted = pageCounts.stream().sorted(compareByLanguageAndCount).collect(Collectors.toList());

        List<String> languages = sorted.stream().map(x -> x.getLanguage()).distinct().collect(Collectors.toList());
        List<PageCountsEntity> result = new ArrayList<>();

        for(String lng: languages){
            result.addAll(sorted.stream().filter(x -> x.getLanguage().equals(lng)).limit(10).collect(Collectors.toList()));
        }
        return result;
    }

    @GetMapping("/languages/top10/csv")
    public List<PageCountsEntity> getTopLanguagesByLanguageAsCSV(HttpServletResponse response) throws IOException {
        List<PageCountsEntity> pageCounts = pageCountService.getPageCounts();
        Comparator<PageCountsEntity> compareByLanguage = Comparator.comparing(PageCountsEntity::getLanguage);
        Comparator<PageCountsEntity> compareByCount = Comparator.comparing(PageCountsEntity::getCount).reversed();
        Comparator<PageCountsEntity> compareByLanguageAndCount = compareByLanguage.thenComparing(compareByCount);
        List<PageCountsEntity> sorted = pageCounts.stream().sorted(compareByLanguageAndCount).collect(Collectors.toList());

        List<String> languages = sorted.stream().map(x -> x.getLanguage()).distinct().collect(Collectors.toList());
        List<PageCountsEntity> result = new ArrayList<>();

        for(String lng: languages){
            result.addAll(sorted.stream().filter(x -> x.getLanguage().equals(lng)).limit(10).collect(Collectors.toList()));
        }

        String csvFileName = "Top10ByLanguages.csv";
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] header = { "Language", "Page", "Count", "Start", "End"};
        csvWriter.writeHeader(header);

        for (PageCountsEntity aBook : result) {
            csvWriter.write(aBook, header);
        }
        csvWriter.close();
        return result;
    }
}
