package project.gradproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(400, "U001", "USER를 찾을 수 없습니다."),
    PASSWORD_UN_MATCH(400, "U002", "비밀번호가 일치하지 않습니다."),


    NOT_FOUND_STORE(400, "S001", "STORE를 찾을 수 없습니다."),

    UN_AUTHORIZED_MEMBER(400, "C001", "권한이 없는 접근입니다."),
    DUPLICATED_EMAIL(400, "C002", "이미 가입된 이메일입니다."),


    // 공통적인 에러코드
    ACCESS_DENIED(403, "C001", "접근할 수 있는 권한이 없습니다."),
    INVALID_TOKEN(401, "C002", "유효하지 않은 토큰입니다."),

    // visitor 에러코드
    NOT_FOUND_VISITOR(400, "V001", "visitor를 찾을 수 없습니다."),

    // author 에러코드
    NOT_FOUND_AUTHOR(400, "A001", "author를 찾을 수 없습니다."),
    DUPLICATED_AUTHOR_NAME(400, "A002", "중복되는 작가명입니다."),
    ALREADY_EXIST_AUTHOR(400, "A003", "이미 author가 존재합니다."),

    // work 에러코드
    NOT_FOUND_WORK(400, "W001", "work을 찾을 수가 없습니다."),

    // series 에러코드
    NOT_FOUND_SERIES(400, "S001", "series를 찾을 수가 없습니다."),

    // follow 에러코드
    NOT_FOUND_FOLLOW(400, "F001" , "follow를 찾을 수가 없습니다."),
    FOLLOW_SAME_FOLLOWING(400, "F002", "팔로워와 팔로잉이 같은사람입니다.");

    private final int status;
    private final String code;
    private final String message;
}
