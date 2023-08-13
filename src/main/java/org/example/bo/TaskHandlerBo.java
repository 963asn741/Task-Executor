package org.example.bo;

import org.example.vo.BulkTaskTRequestVo;

import java.util.List;

public interface TaskHandlerBo {
    String processTasks(List<BulkTaskTRequestVo> bulkTasks);
}
