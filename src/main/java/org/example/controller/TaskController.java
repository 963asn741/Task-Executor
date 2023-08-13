package org.example.controller;

import org.example.bo.TaskHandlerBo;
import org.example.vo.BulkTaskTRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskHandlerBo taskHandlerBo;

    @PostMapping("/process-tasks")
    public ResponseEntity<?> processTasks(@RequestBody List<BulkTaskTRequestVo> bulkTasks){
        return new ResponseEntity<>(taskHandlerBo.processTasks(bulkTasks), HttpStatus.OK);
    }
}
