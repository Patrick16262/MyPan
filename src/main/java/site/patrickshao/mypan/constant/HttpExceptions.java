package site.patrickshao.mypan.constant;

import site.patrickshao.mypan.exception.HttpBadRequestException;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/19
 */
public enum HttpExceptions {
    BAD_REQUEST_400(400, "Bad Request"),
    UNAUTHORIZED_401(401, "Unauthorized"),
    FORBIDDEN_403(403, "Forbidden"),
    NOT_FOUND_404(404, "Not Found"),
    METHOD_NOT_ALLOWED_405(405, "Method Not Allowed"),
    CONFLICT_409(409, "Conflict"),
    NOT_SATISFIABLE_RANGE_416(416, "Requested Range Not Satisfiable"),
    TOO_MANY_REQUESTS_429(429, "Too Many Requests"),
    REQUIRE_CAPTCHA_430(430, "Require Captcha"),
    INCORRECT_AUTHORIZATION_431(431, "Incorrect Authorization"),
    INCORRECT_VERIFICATION_432(432, "Incorrect Verification"),
    BAD_CAPTCHA_433(433, "Bad Captcha"),
    INCORRECT_CAPTCHA_434(434, "Incorrect Captcha"),
    REACH_LIMIT_440(440, "Reach Limit"),
    USER_OUT_OF_SPACE_444(444, "User Out Of Space"),
    SERVER_OUT_OF_SPACE_445(445, "Server Out Of Space"),
    REACH_CONCURRENCY_LIMIT_447(447, "Reach Concurrency Limit"),
    BAD_ARGUMENT_450(450, "Bad Argument"),
    RANGE_ERROR_460(460, "Range Error"),
    EXCEED_FILE_RANGE_464(464, "Exceed File Range"),
    EXCEED_CLIP_RANGE_465(465, "Exceed Clip Range"),
    TOO_LARGE_467(467, "Too Large"),
    ACTIVITY_ERROR_470(470, "Activity Error"),
    NO_SUCH_ACTIVITY_474(474, "No Such Activity"),
    ALREADY_IN_USE_479(479, "Already In Use"),
    CONFLICT_ERROR_490(490, "Conflict Error"),
    NAME_CONFLICT_491(491, "Name Conflict");

    private final int statusCode;
    private final String name;

    HttpExceptions(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getName() {
        return name;
    }

    public HttpBadRequestException getException(String description) {
        return new HttpBadRequestException(statusCode, name, description);
    }
}
