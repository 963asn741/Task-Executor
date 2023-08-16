package org.example.bo;

import lombok.extern.slf4j.Slf4j;
import org.example.executor.AsyncTask;
import org.example.executor.TaskExecutor;
import org.example.vo.TaskRequestVo;
import org.example.vo.TaskResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class TaskHandlerBoImpl implements TaskHandlerBo{

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public List<TaskResponseVo> processTasks(List<TaskRequestVo> bulkTasks, String reqId) {
        log.info("EXEC -> TaskHandlerBoImpl -> processTasks");
        List<AsyncTask> tasks = bulkTasks.stream().map(taskVo ->
                new AsyncTask(taskExecutor, taskVo.getTaskId(), taskVo.getTaskAwaitCompletion(), reqId)
        ).toList();
        List<TaskResponseVo> response = taskExecutor.executeTasks(tasks, new CountDownLatch(tasks.size()));
        if(!response.isEmpty()){
            response = sortResponseElements(response, bulkTasks);
        }
        return response;
    }

    @Override
    public List<TaskRequestVo> getRunningTasks() {
        return taskExecutor.getRunningTasks();
    }

    private List<TaskResponseVo> sortResponseElements(List<TaskResponseVo> responseList, List<TaskRequestVo> bulkTasks){
        List<TaskResponseVo> newResponse = new LinkedList<>();
        for (int i = 0; i < bulkTasks.size(); i++) {
            int finalI = i;
            newResponse.add(responseList.stream().filter(x->x.getTaskId().equals(bulkTasks.get(finalI).getTaskId())).findFirst().get());
        }
        return newResponse;

    }
}
