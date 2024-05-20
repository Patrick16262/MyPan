package site.patrickshao.mypan.exception;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/19
 */
public class HttpBadRequestException extends RuntimeException{
    private final int status;
    private final String name;
    private final String description;
    public HttpBadRequestException(int status, String name, String description) {
        super("HTTP " + status + " " + name + ": " + description);
        this.status = status;
        this.name = name;
        this.description = description;
    }

}
