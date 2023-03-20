package com.project.groupware.todo.service;

import com.project.groupware.todo.dto.TodoDTO;
import com.project.groupware.todo.entity.Todo;
import com.project.groupware.todo.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    public List<TodoDTO> selectList() {

        List<Todo> selectTodoList = todoRepository.findAll();

        return selectTodoList.stream().map(todo -> modelMapper.map(todo, TodoDTO.class )).collect(Collectors.toList());

    }
    public Object selectTodo(Long todoNo) {

        Todo todo = todoRepository.findById(todoNo).get();

        return modelMapper.map(todo, Todo.class);
    }

    @Transactional
    public TodoDTO insertTodo(TodoDTO todoDTO) {

        log.info("[todoService] insertTodo start");
        log.info("[todoService] todoDTO: {}", todoDTO);

        Todo insertTodo = modelMapper.map(todoDTO, Todo.class);

        Todo savedTodo = todoRepository.save(insertTodo);

        return modelMapper.map(savedTodo, TodoDTO.class);
    }

    @Transactional
    public Object updateTodo(TodoDTO todoDTO) {

        log.info("[todoService] updateTodo start");
        Optional<Todo> optionalNotice = todoRepository.findById(todoDTO.getTodoNo());
        if (!optionalNotice.isPresent()) {
            throw new EntityNotFoundException(
                    "todo not "
            );

        }

        Todo todo = optionalNotice.get();

        todo.setTodoTitle(todo.getTodoTitle());
        todo.setTodoContent(todo.getTodoContent());

        log.info("[noticeService] UpdateNotice End");
        return todoRepository.save(todo);
    }

    @Transactional
    public ResponseEntity<?> deleteTodo(TodoDTO todoDTO) {
        log.info("[todoService] deleteTodo start ");

        Todo todo = todoRepository.findById(todoDTO.getTodoNo())
                .orElseThrow(() -> new NotFoundException("todo not found"));
        todoRepository.delete(todo);

        log.info("[todoService] deleteTodo end");

        return ResponseEntity.ok().build();

    }

}
