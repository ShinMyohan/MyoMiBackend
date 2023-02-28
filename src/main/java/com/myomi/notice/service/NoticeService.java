package com.myomi.notice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myomi.admin.entity.Admin;
import com.myomi.admin.repository.AdminRepository;
import com.myomi.notice.dto.NoticeDto;
import com.myomi.notice.dto.NoticeRequestDto;
import com.myomi.notice.dto.NoticeUpdateDto;
import com.myomi.notice.entity.Notice;
import com.myomi.notice.repository.NoticeRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	private final AdminRepository adminRepository;
	
	
	@Transactional
	public List<NoticeDto> getAllNotice(Pageable pageable){
		List<Notice>notices = noticeRepository.findAll(pageable);
		List<NoticeDto>list = new ArrayList<>();
		if(notices.size()==0) {
			log.info("공지사항이 없습니다.");
		}else {
			for(Notice notice:notices) {
				NoticeDto dto = NoticeDto.builder()
								.adminId(notice.getAdmin().getAdminId())
								.noticeNum(notice.getNoticeNum())
								.title(notice.getTitle())
								.content(notice.getContent())
								.createdDate(notice.getCreatedDate())
								.build();
								list.add(dto);
			}
		}
		return list;
	}

	@Transactional
	public void addNotice(NoticeRequestDto noticeDto,Authentication admin) {
		String username=admin.getName();
		Optional<Admin> a=adminRepository.findById(username);
		Notice n = noticeDto.toEntity(noticeDto,a.get());
		noticeRepository.save(n);
	}
	
	@Transactional
	public NoticeDto getOneNotice(Long noticeNum) {
		Notice notice = noticeRepository.findById(noticeNum).get();
		return new NoticeDto(notice);
	}
	
	@Transactional
	public void modifyNotice(NoticeUpdateDto noticeDto, Long noticeNum,Authentication admin) {
		Notice notice = noticeRepository.findById(noticeNum).get();
		String username=admin.getName();
		Optional<Admin> a=adminRepository.findById(username);
		if(notice.getNoticeNum() != noticeNum) {
			log.error("해당 공지사항이 없습니다.");
		} else {
			Notice n = noticeDto.toEntity(noticeDto,a.get(),noticeNum);
			noticeRepository.save(n);
		}
	}
	
	@Transactional
	public void RemoveNotice(Long nNum) {
		Notice notice = noticeRepository.findById(nNum).get();
		noticeRepository.delete(notice);
	}
	
	//제목으로 검색
	@Transactional
	public List<NoticeDto> getAllNoticeByTitle(String keyword,Pageable pageable) {
		List<Notice>notices = noticeRepository.findByTitleContaining(keyword,pageable);
		List<NoticeDto> list =new ArrayList<>();
		for(Notice notice:notices) {
			NoticeDto dto = NoticeDto.builder()
					.adminId(notice.getAdmin().getAdminId())
					.noticeNum(notice.getNoticeNum())
					.title(notice.getTitle())
					.content(notice.getContent())
					.createdDate(notice.getCreatedDate())
					.build();
					list.add(dto);
		}
		return list;
}
}
