package org.example.bo;

import lombok.extern.slf4j.Slf4j;
import org.example.executor.AsyncTask;
import org.example.executor.TaskExecutor;
import org.example.vo.TaskRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class TaskHandlerBoImpl implements TaskHandlerBo{

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public String processTasks(List<TaskRequestVo> bulkTasks) {
        log.info("EXEC -> TaskHandlerBoImpl -> processTasks");
        List<AsyncTask> tasks = bulkTasks.stream().map(taskVo ->
                new AsyncTask(taskExecutor, taskVo.getTaskId(), taskVo.getTaskAwaitCompletion(), taskVo.getProcessingTime())
        ).toList();
        taskExecutor.executeTasks(tasks, new CountDownLatch(tasks.size()));
        return "OK";
    }

    @Override
    public List<TaskRequestVo> getRunningTasks() {
        return taskExecutor.getRunningTasks();
    }
}
