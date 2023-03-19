package com.project.groupware.notice.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.notice.entity.Notice;
import com.project.groupware.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @GetMapping("/notice")
    public ResponseEntity<ResponseDTO> noticeList() {

        List<NoticeDTO> selectNoticeList = noticeService.selectList();

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", selectNoticeList));

    }

//    @PostMapping("/notice/write")
//    public ResponseEntity insertNotice(@RequestBody @Valid NoticeDTO noticeDTO){
//        noticeService.insertNotice(noticeDTO);
//
//        return ResponseEntity.ok(null);
//    }

    /* 공지 등록 */
//    @PostMapping(value = "/notice/write")
//    public ResponseEntity<ResponseDTO> insertNotice(@RequestBody @Validated NoticeDTO noticeDTO){
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 등록 성공", noticeService.insertNotice(noticeDTO)));
//    }
//    @PostMapping("notice/write") // 사기 등록
//    public ResponseEntity<Notice> createNotice() throws ParseException{
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = format.parse("2023-03-18");
//        Notice notice = Notice.builder()
//                .noticeNo(0)
//                .memberCode(2000)
//                .noticeTitle("hello")
//                .noticeContent("hello2")
//                .noticeViews(5)
//                .noticeDate(date)
//                .build();
//
//        Notice saveNotice = noticeService.createNotice(notice);
//        return new ResponseEntity<>(saveNotice, HttpStatus.OK);



    @Operation(summary = " 신규 공지사항 등록 요청", description = "신구 공지사항 등록 진행됩니다.", tags = { "NoticeController"})
    @PostMapping(value = "/notice/write")
    public ResponseEntity<ResponseDTO> insertNotice(@RequestBody NoticeDTO noticeDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "신규 공지사항 등록성공", noticeService.insertNotice(noticeDTO)));


    }

    @PutMapping(value = "/notice/detail")
    public ResponseEntity<ResponseDTO> updateNotice(@ModelAttribute NoticeDTO noticeDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 수정 성공", noticeService.updateNotice(noticeDTO)));
    }

    // @@  1차 시도 실패
//    @DeleteMapping(value = "/notice/detail/{noticeNo}")
//    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeNo) {
//
//        return noticeService.deleteNoticeById(noticeNo);
//    }

//    @DeleteMapping(value = "notice/detail/{noticeNo}")
//    public ResponseEntity<ResponseDTO> deleteNotice(@PathVariable NoticeDTO noticeDTO){
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 수정 성공", noticeService.deleteNotice(noticeDTO)));
//    }


    // delete 성공본
//    @DeleteMapping(value = "/notice/detail/{noticeNo}")
//    public String deleteNotice(@PathVariable Long noticeNo) {
//        NoticeDTO noticeDTO = new NoticeDTO();
//        noticeDTO.setNoticeNo(noticeNo);
//        noticeService.deleteNotice(noticeDTO);
//        return "redirect:/detail/";
//    }

    @DeleteMapping(value = "/notice/detail/{noticeNo}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeNo){
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeNo(noticeNo);
        return noticeService.deleteNotice(noticeDTO);
    }


}
