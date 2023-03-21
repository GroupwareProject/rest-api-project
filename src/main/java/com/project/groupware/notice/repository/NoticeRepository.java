package com.project.groupware.notice.repository;

import com.project.groupware.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long> {


//    Notice findByNoticeNo(long noticeNo);
}
