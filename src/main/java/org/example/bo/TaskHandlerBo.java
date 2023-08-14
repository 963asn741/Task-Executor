package org.example.bo;

import org.example.vo.TaskRequestVo;

import java.util.List;

public interface TaskHandlerBo {
    String processTasks(List<TaskRequestVo> bulkTasks);

    List<TaskRequestVo> getRunningTasks();
}
