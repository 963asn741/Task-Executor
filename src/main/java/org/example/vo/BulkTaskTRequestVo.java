package org.example.vo;

import lombok.Data;

@Data
public class BulkTaskTRequestVo {
    private Long taskId;
    private Boolean taskAwaitCompletion;
    private Long processingTime;

}
