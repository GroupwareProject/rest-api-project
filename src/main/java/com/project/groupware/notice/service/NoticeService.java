package com.project.groupware.notice.service;

import com.project.groupware.notice.dto.NoticeDTO;
import com.project.groupware.notice.entity.Notice;
import com.project.groupware.notice.repository.NoticeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Path;
import java.util.List;
import java.util.Optional;
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

    public Object selectNotice(Long noticeNo) {

        log.info("[noticeService] 개인조회 start");

        Notice notice = noticeRepository.findById(noticeNo).get();

        log.info("[noticeService] 개인조회 end");
        return modelMapper.map(notice, Notice.class);
    }


    @Transactional
    public NoticeDTO insertNotice(NoticeDTO noticeDTO) {
        log.info("[noticeService] insertNotice start");
        log.info("[noticeService] noticeDTO: {}", noticeDTO);

        Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);

        Notice savedNotice = noticeRepository.save(insertNotice);

        return modelMapper.map(savedNotice, NoticeDTO.class);
    }


    @Transactional
    public Notice updateNotice(Long noticeNo, NoticeDTO noticeDTO) {

        Optional<Notice> optionalNotice = noticeRepository.findById(noticeNo);

        if (!optionalNotice.isPresent()) {
            throw new EntityNotFoundException( "notice not present");

        }

        Notice notice = optionalNotice.get();
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeContent(noticeDTO.getNoticeContent());

        return noticeRepository.save(notice);

    }

    public ResponseEntity<?> deleteNotice(NoticeDTO noticeDTO) {
        log.info("[noticeService] deleteNotice Start");

        Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo())
                .orElseThrow(() -> new NotFoundException("notice not found"));
        noticeRepository.delete(notice);

        log.info("[noticeService] deleteNotice end");

        return ResponseEntity.ok().build();
    }







}
