package com.myomi.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
//@Service
public class S3UploaderBoard {
	static { System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); }
	private final AmazonS3Client amazonS3Client;
	private final BoardRepository boardRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
	public String upload(MultipartFile multipartFile, String dirName, Authentication user
			, BoardReadResponseDto boardDto
			) throws IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
		return upload(uploadFile, dirName, user
				, boardDto
				);
	}

	private String upload(File uploadFile, String dirName, Authentication user
			, BoardReadResponseDto boardDto
			) {
//		Optional<Board> optB = boardRepository.findById(boardDto.getBoardNum());
		String fileName = dirName + "/" + user.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);

		removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

		return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile)
				.withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
				);
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if(targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		}else {
			log.info("파일이 삭제되지 못했습니다.");
		}
	}

	private Optional<File> convert(MultipartFile file) throws  IOException {
		log.info("파일이름은: " + file.getName() + "파일 사이즈는: "+ file.getSize());
		File convertFile = new File(file.getOriginalFilename());
		removeNewFile(convertFile);
		if(convertFile.createNewFile()) { 
			//AWS폴더의 파일존재여부 
			log.info("아무");
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
				fos.close();
			}
			return Optional.of(convertFile);
		}
		return Optional.empty();
	}

}