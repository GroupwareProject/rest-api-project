package com.project.groupware.todo.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.notice.entity.Notice;
import com.project.groupware.todo.dto.TodoDTO;
import com.project.groupware.todo.entity.Todo;
import com.project.groupware.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "todo 전체 조회 요청", description = "todo 전체 조회를 요청합니다.")
    @GetMapping("/todo")
    public ResponseEntity<ResponseDTO> todoList(){

        List<TodoDTO> selectTodoList = todoService.selectList();

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공",selectTodoList));
    }
    @Operation(summary = "todo 상세조회 요청", description = "todo 상세페이지 조회를 요청합니다.")
    @GetMapping("/todo/{todoNo}")
    public ResponseEntity<ResponseDTO> todoSelect(@PathVariable Long todoNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회성공",todoService.selectTodo(todoNo)));
    }

    @Operation(summary = " 신규 todo 등록 요청", description = "신규 todo 등록 진행됩니다.", tags = { "todoController"})
    @PostMapping(value = "/todo/write")
    public ResponseEntity<ResponseDTO> insertTodo(@RequestBody TodoDTO todoDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "신규 todo 등록성공", todoService.insertTodo(todoDTO)));
    }

    @Operation(summary = " todo 수정 요청", description = "todo 수정이 진행 됩니다.")
    @PatchMapping(value = "/todo/detail/{todoNo}")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDTO todoDTO, @PathVariable Long todoNo) {

        return ResponseEntity.ok(todoService.updateTodo(todoNo, todoDTO));
    }
    @Operation(summary = " todo 삭제 요청", description = "todo 삭제가 진행됩니다.")
    @DeleteMapping(value = "/todo/detail/{todoNo}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoNo) {

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTodoNo(todoNo);

        return todoService.deleteTodo(todoDTO);
    }

}
