package page.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "page_count")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageCountsEntity {
    @Id
    public int id;

    public PageCountsEntity(int id, String language, String page, int count, String start, String end, LocalDate createdAt) {
        this.id = id;
        this.language = language;
        this.page = page;
        this.count = count;
        this.start = start;
        this.end = end;
        this.createdAt = createdAt;
    }

    public String language;
    public String page;
    public int count;
    public String start;
    public String end;
    public LocalDate createdAt;

    public String getLanguage() {
        return language;
    }

    public int getCount() {
        return count;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public String getPage() {
        return page;
    }
}
