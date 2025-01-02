package com.example.demo.src.user;



import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    //POST
    public PostUserRes createUser(PostUserReq postUserReq) {
        // 이메일로 사용자 중복 체크
        Optional<User> checkUser = userRepository.findByEmailAndState(postUserReq.getEmail(), ACTIVE);
        if(checkUser.isPresent() == true){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        // 가입 수단에 따라 처리 분기
        if (postUserReq.getJoinType() == User.JoinType.LOCAL) {
            // LOCAL 회원가입인 경우, 비밀번호 암호화
            String encryptPwd;
            try {
                encryptPwd = new SHA256().encrypt(postUserReq.getPassword());
                postUserReq.setPassword(encryptPwd); // 비밀번호 암호화
            } catch (Exception exception) {
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }

            // 소셜 ID는 null로 설정
            postUserReq.setSocialId(null);

        } else if (postUserReq.getJoinType() == User.JoinType.GOOGLE ||
                postUserReq.getJoinType() == User.JoinType.KAKAO) {
            // 소셜 로그인인 경우, 비밀번호를 null로 설정
            postUserReq.setPassword(null);

            // 소셜 ID가 없는 경우 예외 처리
            if (postUserReq.getSocialId() == null) {
                throw new BaseException(INVALID_SOCIAL_ID);
            }
        } else {
            throw new BaseException(INVALID_JOIN_TYPE);
        }

        User saveUser = userRepository.save(postUserReq.toEntity());
        return new PostUserRes(saveUser.getId());

    }

    public PostUserRes createOAuthUser(User user) {
        User saveUser = userRepository.save(user);

        // JWT 발급
        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserRes(saveUser.getId(), jwtToken);

    }

    public void modifyUserName(Long userId, PatchUserReq patchUserReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateName(patchUserReq.getName());
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.deleteUser();
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsers() {
        List<GetUserRes> getUserResList = userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsersByEmail(String email) {
        List<GetUserRes> getUserResList = userRepository.findAllByEmailAndState(email, ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }


    @Transactional(readOnly = true)
    public GetUserRes getUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmailAndState(email, ACTIVE);
        if (result.isPresent()) return true;
        return false;
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        // 이메일로 사용자 조회 후, ACTIVE 상태인지 체크
        User user = userRepository.findByEmailAndState(postLoginReq.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        // 비밀번호 암호화 후, 체크
        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        // JWT 생성
        if(user.getPassword().equals(encryptPwd)){
            Long userId = user.getId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginRes(userId,jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public GetUserRes getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        return new GetUserRes(user);
    }

    @Transactional
    public void updateConsent(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateConsent(LocalDate.now());
    }
}
