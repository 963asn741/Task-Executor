package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.bo.TaskHandlerBo;
import org.example.vo.TaskTRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskHandlerBo taskHandlerBo;

    @PostMapping("/process-tasks")
    public ResponseEntity<?> processTasks(@RequestBody List<TaskTRequestVo> bulkTasks, HttpServletRequest request){
        return new ResponseEntity<>(taskHandlerBo.processTasks(bulkTasks, request.getRequestId()), HttpStatus.OK);
    }

    @GetMapping("/running-tasks")
    public ResponseEntity<List<TaskTRequestVo>> getRunningTasks(){
        return new ResponseEntity<>(taskHandlerBo.getRunningTasks(), HttpStatus.OK);
    }
}
