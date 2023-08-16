package org.example.vo;

import lombok.Data;

@Data
public class TaskRequestVo {
    private Long taskId;
    private Boolean taskAwaitCompletion;

    public TaskRequestVo(Long taskId, Boolean taskAwaitCompletion){
        this.taskId = taskId;
        this.taskAwaitCompletion = taskAwaitCompletion;
    }

}
