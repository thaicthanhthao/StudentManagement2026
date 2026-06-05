package student.techzen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class PageResponse<T> {
    List<T> item;
    int page;
    int size;
    long totalItems;
    long totalPages;

    public PageResponse(Page<T> pageData) {
        this.item = pageData.getContent();
        this.totalPages = pageData.getTotalPages();
        this.totalItems = pageData.getTotalElements();
        this.size = pageData.getSize();
        this.page = pageData.getNumber();
    }
}
