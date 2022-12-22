package catharsis.announcement_server.Validation;

import static catharsis.announcement_server.Config.Config.*;

public class Validation {

    //systemMessage를 검증하여 유효하면 true, 그렇지 않으면 false 반환.
    public boolean message_validation(final String systemMessage) {
        //길이 제한을 만족하는 유의미한 문자열인지의 여부를 반환
        return !systemMessage.isBlank() && systemMessage.length() <= 255;
    }

}
