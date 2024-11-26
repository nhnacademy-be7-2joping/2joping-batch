package com.nhnacademy.twojopingbatch.batch;


import com.nhnacademy.twojopingbatch.batch.partitioner.CustomerPartitioner;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPartitionerTest {

    private final CustomerPartitioner partitioner = new CustomerPartitioner();

    @Test
    void testPartitionWithGridSize() {
        // Given
        int gridSize = 5; // 파티션 수 설정

        // When
        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        // Then
        assertEquals(gridSize, partitions.size()); // 생성된 파티션 수 확인

        long expectedRange = (10000L - 1L + 1) / gridSize; // 파티션 범위 계산
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = partitions.get("partition" + i);
            assertNotNull(context); // 각 파티션이 존재해야 함

            long startId = context.getLong("startId");
            long endId = context.getLong("endId");

            if (i == gridSize - 1) {
                // 마지막 파티션의 끝 ID는 maxId(10000)이어야 함
                assertEquals(10000L, endId);
            } else {
                // 각 파티션의 범위가 예상 범위와 일치해야 함
                assertEquals(startId + expectedRange - 1, endId);
            }
        }
    }

    @Test
    void testPartitionWithSingleGridSize() {
        // Given
        int gridSize = 1; // 파티션 수 1개

        // When
        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        // Then
        assertEquals(1, partitions.size()); // 하나의 파티션만 생성
        ExecutionContext context = partitions.get("partition0");
        assertNotNull(context);

        // 파티션 범위가 전체 데이터와 일치해야 함
        assertEquals(1L, context.getLong("startId"));
        assertEquals(10000L, context.getLong("endId"));
    }
}