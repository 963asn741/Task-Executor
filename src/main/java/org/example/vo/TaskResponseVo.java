package org.example.vo;

import lombok.Data;
import org.example.constants.TaskStatus;

@Data
public class TaskResponseVo {
    private Long taskId;
    private Long proessingTimeTaken;
    private String taskStatus;

    public TaskResponseVo(Long taskId, Long proessingTimeTaken, String taskStatus) {
        this.taskId = taskId;
        this.proessingTimeTaken = proessingTimeTaken;
        if(taskStatus.equalsIgnoreCase(TaskStatus.INITIATED.getValue()))
            this.proessingTimeTaken=null;
        this.taskStatus = taskStatus;
    }
}
