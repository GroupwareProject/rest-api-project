package com.project.groupware.todo.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.todo.dto.TodoDTO;
import com.project.groupware.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public ResponseEntity<ResponseDTO> todoList(){

        List<TodoDTO> selectTodoList = todoService.selectList();

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공",selectTodoList));
    }

    @GetMapping("/todo/{todoNo}")
    public ResponseEntity<ResponseDTO> todoSelect(@PathVariable Long todoNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회성공",todoService.selectTodo(todoNo)));
    }

    @PostMapping(value = "/todo/write")
    public ResponseEntity<ResponseDTO> insertTodo(@RequestBody TodoDTO todoDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "신규 todo 등록성공", todoService.insertTodo(todoDTO)));
    }

    @PutMapping(value = "/todo/detail")
    public ResponseEntity<ResponseDTO> updateTodo(@RequestBody TodoDTO todoDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "todo 수정 성공", todoService.updateTodo(todoDTO)));
    }

    @DeleteMapping(value = "/todo/detail/{todoNo}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoNo) {

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTodoNo(todoNo);

        return todoService.deleteTodo(todoDTO);
    }

}
