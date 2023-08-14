package org.example.vo;

import lombok.Data;

@Data
public class TaskRequestVo {
    private Long taskId;
    private Boolean taskAwaitCompletion;
    private Long processingTime;

    public TaskRequestVo(Long taskId, Long processingTime, Boolean taskAwaitCompletion){
        this.taskId = taskId;
        this.taskAwaitCompletion = taskAwaitCompletion;
        this.processingTime = processingTime;
    }

}
