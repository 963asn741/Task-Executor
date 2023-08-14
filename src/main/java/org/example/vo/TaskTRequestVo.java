package org.example.vo;

import lombok.Data;

@Data
public class TaskTRequestVo {
    private Long taskId;
    private Boolean taskAwaitCompletion;
    private Long processingTime;

    public TaskTRequestVo(Long taskId, Long processingTime, Boolean taskAwaitCompletion){
        this.taskId = taskId;
        this.taskAwaitCompletion = taskAwaitCompletion;
        this.processingTime = processingTime;
    }

}
