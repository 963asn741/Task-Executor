package org.example.bo;

import org.example.vo.TaskTRequestVo;

import java.util.List;

public interface TaskHandlerBo {
    String processTasks(List<TaskTRequestVo> bulkTasks, String requestId);

    List<TaskTRequestVo> getRunningTasks();
}
