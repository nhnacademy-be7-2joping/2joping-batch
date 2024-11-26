package com.nhnacademy.twojopingbatch.batch.partitioner;

import jakarta.annotation.Nonnull;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomerPartitioner
 * 스프링 배치의 파티셔너(Partitioner) 구현 클래스입니다.
 * 데이터 처리를 병렬로 수행하기 위해 주어진 범위를 여러 파티션으로 나누는 역할을 합니다.
 * 이 클래스는 특정 ID 범위를 기반으로 데이터를 여러 파티션으로 나누고,
 * 각 파티션의 시작 ID와 끝 ID를 ExecutionContext에 저장합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Component
public class CustomerPartitioner implements Partitioner {

    /**
     * 파티션 분할 메서드.
     * 주어진 gridSize(그리드 크기)를 기준으로 ID 범위를 나누고,
     * 각 파티션에 시작 ID와 끝 ID를 할당합니다.
     *
     * @param gridSize 나눌 파티션의 개수
     * @return 각 파티션의 이름과 ExecutionContext를 매핑한 Map
     */
    @Override
    @Nonnull
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();
        long minId = 1L; // 최소 ID
        long maxId = 10000L; // 최대 ID
        long range = (maxId - minId + 1) / gridSize;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            long start = minId + (i * range);
            long end = (i == gridSize - 1) ? maxId : (start + range - 1);
            context.putLong("startId", start);
            context.putLong("endId", end);
            partitions.put("partition" + i, context);
        }
        return partitions;
    }
}