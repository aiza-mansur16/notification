package com.example.notification.utils.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record Page(
        @Schema(description = "page number")
        long pageNumber,
        @Schema(description = "page size")
        long pageSize,
        @Schema(description = "total number of records")
        long totalRecords,
        @Schema(description = "total number of pages")
        long totalPages
) {
}
