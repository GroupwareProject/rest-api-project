package com.project.groupware.board.repository;

import com.project.groupware.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardNo(long boardNo);
}
