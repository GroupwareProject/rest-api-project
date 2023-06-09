package com.project.groupware.notice.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.notice.entity.Notice;
import com.project.groupware.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }



    /* 공지 조회 */
    @Operation(summary = "공지사항 전체 조회 요청", description = "게시판 전체 조회를 요청합니다.")
    @GetMapping("/notice")
    public ResponseEntity<ResponseDTO> noticeList() {

        List<NoticeDTO> selectNoticeList = noticeService.selectList();

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", selectNoticeList));

    }

    @Operation(summary = "공지사항 상세조회 요청", description = "게시판 상세페이지 조회를 요청합니다.")
    @GetMapping("/notice/detail/{noticeNo}")
    public ResponseEntity<ResponseDTO> noticeSelectList(@PathVariable Long noticeNo) {


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", noticeService.selectNotice(noticeNo)));
    }




    /* 공지사항 Create */
    @Operation(summary = " 신규 공지사항 등록 요청", description = "신구 공지사항 등록 진행됩니다.", tags = { "NoticeController"})
    @PostMapping(value = "/notice/write")
    public ResponseEntity<ResponseDTO> insertNotice(@RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "신규 공지사항 등록성공", noticeService.insertNotice(noticeDTO)));


    }


    /* 공지사항 Update */
    @Operation(summary = " 공지사항 수정 요청", description = "공지사항 수정이 진행 됩니다.")
    @PatchMapping("/notice/detail/{noticeNo}")
    public ResponseEntity<Notice> updateNotice(@RequestBody NoticeDTO noticeDTO, @PathVariable Long noticeNo) {

        return ResponseEntity.ok(noticeService.updateNotice(noticeNo, noticeDTO));
    }

    /* 공지사항 Delete */
    @Operation(summary = " 공지사항 삭제 요청", description = "공지사항 삭제가 진행됩니다.")
    @DeleteMapping(value = "/notice/detail/{noticeNo}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeNo){
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeNo(noticeNo);
        return noticeService.deleteNotice(noticeDTO);
    }


}
