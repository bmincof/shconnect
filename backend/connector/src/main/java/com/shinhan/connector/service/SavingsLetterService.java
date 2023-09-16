package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.SavingsLetterAddRequest;
import com.shinhan.connector.dto.request.SavingsLetterModifyRequest;
import com.shinhan.connector.dto.response.SavingsLetterResponse;
import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.entity.SavingsLetter;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import com.shinhan.connector.repository.SavingsLetterRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.Jar;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsLetterService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final SavingsLetterRepository savingsLetterRepository;

    @Transactional
    public SavingsLetterResponse regist(SavingsLetterAddRequest savingsLetterAddRequest, UserDetailsImpl user) {
        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰을 확인해주세요.")
                );

        Friend friend = null;

        if (savingsLetterAddRequest.getFriendNo() != null) {
            friend = friendRepository.findById(savingsLetterAddRequest.getFriendNo())
                    .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 번호를 확인해주세요.")
                    );

            if (!friend.getMember().equals(member)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
            }
        }

        SavingsLetter savingsLetter = savingsLetterRepository.save(savingsLetterAddRequest.toEntity(member, friend));

        return SavingsLetterResponse.entityToDto(savingsLetter);
    }

    public SavingsLetterResponse getDetails(Integer savingsLetterNo, UserDetailsImpl user) {
        SavingsLetter savingsLetter = savingsLetterRepository.findById(savingsLetterNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "적금편지를 찾을 수 없습니다."));

        if (!savingsLetter.getMember().getNo().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 적금편지만 조회가 가능합니다.");
        }

        return SavingsLetterResponse.entityToDto(savingsLetter);
    }

    @Transactional
    public SavingsLetterResponse modifyLetter(Integer savingsLetterNo, SavingsLetterModifyRequest savingsLetterModifyRequest, UserDetailsImpl user) {
        SavingsLetter savingsLetter = savingsLetterRepository.findById(savingsLetterNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "적금편지 번호가 잘못되었습니다."));

        if (!savingsLetter.getMember().getNo().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 편지만 수정이 가능합니다.");
        }

        savingsLetter.update(savingsLetterModifyRequest);
        savingsLetterRepository.save(savingsLetter);

        return SavingsLetterResponse.entityToDto(savingsLetter);
    }

    public ResponseMessage deleteLetter(Integer savingsLetterNo, UserDetailsImpl user) {
        SavingsLetter savingsLetter = savingsLetterRepository.findById(savingsLetterNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "적금편지를 찾을 수 없습니다."));

        if (!savingsLetter.getMember().getNo().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 적금편지만 수정이 가능합니다.");
        }
        savingsLetterRepository.delete(savingsLetter);

        return new ResponseMessage("적금편지 삭제 완료");
    }

    public List<SavingsLetterResponse> getAll(UserDetailsImpl user) {
        return savingsLetterRepository.findSavingsLettersByMemberNo(user.getId()).stream()
                .map(savingsLetter -> SavingsLetterResponse.entityToDto(savingsLetter))
                .collect(Collectors.toList());
    }
}
