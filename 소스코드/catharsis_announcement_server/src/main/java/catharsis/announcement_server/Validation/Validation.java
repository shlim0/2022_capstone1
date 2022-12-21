package catharsis.announcement_server.Validation;

import catharsis.announcement_server.Config.Config;
import catharsis.announcement_server.VO.SystemMessage;

public class Validation {

    //VO에 해당하는 SystemMessage를 검증하여 유효하면 true, 그렇지 않으면 false 반환.
    public boolean message_validation(final SystemMessage systemMessage) {
        final String message = systemMessage.getSystem_message();
        //빈 문자열, 공백 문자로 이루어진 문자열, 길이가 DB에 정의된 값을 초과하는 메시지
        if(message.isBlank() || message.length() > Config.MAX_MESSAGE_LENGTH) {
            return false;
        }
        //유효한 메시지
        return true;
    }

}
