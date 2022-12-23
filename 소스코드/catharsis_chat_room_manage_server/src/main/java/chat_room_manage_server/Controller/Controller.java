package chat_room_manage_server.Controller;

import chat_room_manage_server.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Repository repository;

    @Autowired
    public Controller (final Repository repository) {
        this.repository = repository;
    }

}

