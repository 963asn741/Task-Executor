package org.example.bo;

import lombok.extern.slf4j.Slf4j;
import org.example.executor.AsyncTask;
import org.example.executor.TaskExecutor;
import org.example.vo.BulkTaskTRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class TaskHandlerBoImpl implements TaskHandlerBo{

    @Value("${app.max_concurrent_threads}")
    private Integer MAX_THREAD_COUNT;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public String processTasks(List<BulkTaskTRequestVo> bulkTasks) {
        log.info("EXEC -> TaskHandlerBoImpl -> processTasks");
        List<AsyncTask> tasks = bulkTasks.stream().map(taskVo -> new AsyncTask(taskExecutor, taskVo.getTaskId(), taskVo.getTaskAwaitCompletion())).toList();
        taskExecutor.executeTasks(tasks, new CountDownLatch(tasks.size()), MAX_THREAD_COUNT);
        return "OK";
    }
}
