package com.project.groupware.notice.service;

import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.notice.entity.Notice;
import com.project.groupware.notice.repository.NoticeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private static final Logger log = LoggerFactory.getLogger(NoticeService.class);

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }


    public List<NoticeDTO> selectList() {

        List<Notice> selectNoticeList = noticeRepository.findAll();

        return selectNoticeList.stream().map(notice -> modelMapper.map(notice, NoticeDTO.class)).collect(Collectors.toList());
    }


//    @Transactional @@ content cannot be null
//    public Notice createNotice(NoticeDTO noticeDTO) {
//
//        Notice createNotice = modelMapper.map(noticeDTO, Notice.class);
//        noticeRepository.save(createNotice);
//
//        return createNotice;
//    }

//    @Transactional  date
//    public Object insertNotice(NoticeDTO noticeDTO) {
//
//        log.info("[insertservice] insertnotice start=====");
//        log.info("[noticeservice] noticeDTO: " + noticeDTO );
//
//        int result = 0;
//
//        try {
//            Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);
//
//            noticeRepository.save(insertNotice);
//
//            result = 1;
//        } catch (Exception e) {
//
//            throw new RuntimeException(e);
//        }
//
//        return (result > 0) ? "공지사항 등록 성공" : "공지사항 등록 실패";
//    }

//    @Transactional
//    public Notice createNotice(Notice notice) {
//        Notice saveNotice = noticeRepository.save(notice);
//        return saveNotice;
//    }

    @Transactional
    public NoticeDTO insertNotice(NoticeDTO noticeDTO) {
        log.info("[noticeService] insertNotice start");
        log.info("[noticeService] noticeDTO: {}", noticeDTO);

        Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);

        Notice savedNotice = noticeRepository.save(insertNotice);

        return modelMapper.map(savedNotice, NoticeDTO.class);
    }

    @Transactional
    public Object updateNotice(NoticeDTO noticeDTO) {

        log.info("[noticeService] updateNotice Start");
        log.info("[noticeService] noticeDTO : " + noticeDTO);

        int result = 0;
        try {
            Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo()).get();

            notice.setNoticeTitle(noticeDTO.getNoticeTitle());
            notice.setNoticeContent(noticeDTO.getNoticeContent());

            noticeRepository.save(notice);
            result = 1;
        } catch (Exception e) {
            log.info("[updateNotice] exception");

            throw new RuntimeException(e);
        }

        log.info("[noticeService] updateNotice end");
        return (result > 0) ? "공지사항 업데이트 성공" : " 공지사항 업데이트 실패";
    }

    public ResponseEntity<?> deleteNotice(NoticeDTO noticeDTO) {
        log.info("[noticeService] deleteNotice Start");

        Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo())
                .orElseThrow(() -> new NotFoundException("notice not found"));
        noticeRepository.delete(notice);

        log.info("[noticeService] deleteNotice end");

        return ResponseEntity.ok().build();
    }

//    delete 성공본
//    public void deleteNotice(NoticeDTO noticeDTO) {
//
//        log.info("[noticeService] deleteNotice Start");
//        Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo())
//                .orElseThrow(() -> new NotFoundException("notice not found"));
//        noticeRepository.delete(notice);
//    }

//    @Transactional
//    public Object deleteNotice(NoticeDTO noticeDTO) {
//        Notice notice = noticeRepository.deleteById(notice.getNoticeNo());
//    }


//    @Transactional
//    public ResponseEntity<?> deleteNoticeById(Long noticeNo) {
//        log.info("[deleteNoticeService] start");
//
//        try {
//            noticeRepository.deleteById(noticeNo);
//            return ResponseEntity.ok().build();
//        } catch (EmptyResultDataAccessException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}