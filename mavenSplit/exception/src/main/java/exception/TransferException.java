package exception;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/16 10:31
 * @Version: 1.0
 */
public class TransferException extends RuntimeException{

    public TransferException(String message) {
        super(message);
    }
}
