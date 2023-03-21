package com.project.groupware.board.controller;

import com.project.groupware.board.dto.BoardDTO;
import com.project.groupware.board.service.BoardService;
import com.project.groupware.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) { this.boardService = boardService; }

    /* 게시글 조회 */
    @Operation(summary = "게시글 조회 요청", description = "게시글 한 개 조회됩니다.", tags = {"BoardController"})
    @GetMapping("/board/{boardNo}")
    public ResponseEntity<ResponseDTO> selectBoardList(@PathVariable int boardNo){

        System.out.println("boardNo ==================== " + boardNo);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "게시글 조회 성공", boardService.selectBoardList(boardNo)));
    }

    /* 게시글 전체 조회 */
    @GetMapping("/board")
    public ResponseEntity<ResponseDTO> boardList() {

        List<BoardDTO> searchBoardList = boardService.searchBoardList();

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", searchBoardList));
    }

    /* 게시글 전체 조회 */
//    @Operation(summary = "게시글 리스트 조회 요청", description = "전체 게시글이 조회됩니다.", tags = {"BoardController"})
//    @GetMapping("/board")
//    public ResponseEntity<ResponseDTO> searchBoardList(){
//
//        System.out.println("전체 게시글 조회 컨트롤러 입니다.");
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 게시글 조회 성공", boardService.searchBoardList()));
//    }

    /* 게시글 등록 */
    @Operation(summary = " 신규 게시판글 등록 요청", description = " 신규 게시판글 등록이 진행됩니다.", tags = { "BoardController"})
    @PostMapping(value = "/board/write")
    public ResponseEntity<ResponseDTO> insertBoard(@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "신규 게시글 등록 성공", boardService.insertBoard(boardDTO)));
    }

    /* 게시글 수정 */
    @Operation(summary = "게시글 수정 요청", description = "게시글이 수정되었습니다.", tags = {"BoardController"})
    @PutMapping("/board/update/{boardNo}")
    public ResponseEntity<ResponseDTO> updateBoard(@RequestBody BoardDTO boardDTO, @PathVariable long boardNo){

        System.out.println("게시글 수정 컨트롤러 입니다.");
        System.out.println("memberDTO ========== " + boardDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "게시글 수정 성공", boardService.updateBoardList(boardDTO, boardNo)));
    }

    /* 게시글 삭제 */
    @Operation(summary = "게시글 삭제 요청", description = "게시글이 삭제됩니다.", tags = {"BoardController"})
    @DeleteMapping("/board/delete/{boardNo}")
    public ResponseEntity<ResponseDTO> deleteBoardList(@PathVariable long boardNo){

        System.out.println("게시글 삭제 컨트롤러 입니다.");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "게시글 삭제 성공", boardService.deleteBoardList(boardNo)));
    }
}
