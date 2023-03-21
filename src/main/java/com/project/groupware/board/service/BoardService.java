package com.project.groupware.board.service;

import com.project.groupware.board.dto.BoardDTO;
import com.project.groupware.board.entity.Board;
import com.project.groupware.board.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardService(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    /* 게시글 조회 */
    public BoardDTO selectBoardList(int boardNo) {

        log.info("[BoardService] selectBoardList Start");

        Board board = boardRepository.findByBoardNo(boardNo);
        log.info("[BoardService] {}", board);
        log.info("[BoardService] selectBoardList End");

        return modelMapper.map(board, BoardDTO.class);
    }

    /* 게시글 전체 조회 */
    public List<BoardDTO> searchBoardList() {

        List<Board> selectBoardList = boardRepository.findAll();

        return selectBoardList.stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());
    }

    /* 게시글 전체 조회 */
//    public BoardDTO searchBoardList() {
//        log.info("[BoardService] searchBoardList Start");
//
//        List<Board> selectAllList = boardRepository.findAll();
//
//        log.info("[BoardService] boardList {}", selectAllList);
//
//        log.info("[BoardService] searchBoardList End");
//
//        return selectAllList.stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList())
//    }

    /* 게시글 등록 */
    @Transactional
    public BoardDTO insertBoard(BoardDTO boardDTO) {
        log.info("[boardService] insertBoard Start");
        log.info("[boardService] boardDTO: {}", boardDTO);

        Board insertBoard = modelMapper.map(boardDTO, Board.class);

        Board savedBoard = boardRepository.save(insertBoard);

        return modelMapper.map(savedBoard, BoardDTO.class);
    }

    @Transactional
    public Object updateBoardList(BoardDTO boardDTO, long boardNo) {
        log.info("[BoardService] updateBoard Start");
        System.out.println("boardDTO ==================== " + boardDTO);

        int result = 0;

        try {
            Board board = boardRepository.findByBoardNo(boardNo);

            board.setBoardNo(boardDTO.getBoardNo());
            board.setMemberCode(boardDTO.getMemberCode());
            board.setBoardTitle(boardDTO.getBoardTitle());
            board.setBoardContent(boardDTO.getBoardContent());
            board.setBoardViews(boardDTO.getBoardViews());
            board.setBoardDate(boardDTO.getBoardDate());

            boardRepository.save(board);

            result = 1;
        } catch (Exception e) {
            log.info("[board update] Exception!!!!!");
        }
            log.info("[BoardService] updateBoard End");

            return (result > 0) ? "개시글 수정 성공 :)" : "게시글 수정 실패 :(";
    }

    @Transactional
    public String deleteBoardList(long boardNo) {

        log.info("[BoardService] deleteBoardList Start");

        System.out.println("boardNo = " + boardNo);

        Optional<Board> board = boardRepository.findById(boardNo);

        System.out.println("board ========== " + board);

        int result = 0;

        try {
            boardRepository.deleteById(board.get().getBoardNo());
            result = 1;
        } catch (Exception e) {
            log.error("[BoardService] deleteBoard error", e);
        }
        return (result > 0) ? "게시글 삭제 성공 :)" : "게시글 삭제 실패 :(";
    }
}
