#ifndef __CHATROOM_H__
#define __CHATROOM_H__

#include <string>

class ChatRoom {
public:
    int room_id;
    string user_id_1;
    string user_id_2;
    bool user_1_status;
    bool user_2_status;
};

#endif