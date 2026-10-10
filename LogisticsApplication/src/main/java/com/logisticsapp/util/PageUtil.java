package com.logisticsapp.util;

import com.logisticsapp.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// helper - builds a Pageable (page, size, sorting) for pagination
public class PageUtil {

    // checks the values, then builds page number, page size and ascending / descending sort
    public static Pageable of(int page, int size, String sortBy, String direction) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be negative");
        }
        if (size < 1 || size > 100) {
            throw new BadRequestException("Page size must be between 1 and 100");
        }
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}