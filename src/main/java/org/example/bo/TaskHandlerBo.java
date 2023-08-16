package org.example.bo;

import org.example.vo.TaskRequestVo;

import java.util.List;

public interface TaskHandlerBo {
    List<?> processTasks(List<TaskRequestVo> bulkTasks, String reqId);

    List<TaskRequestVo> getRunningTasks();
}
