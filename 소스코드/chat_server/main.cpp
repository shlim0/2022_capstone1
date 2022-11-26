#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/epoll.h>

#include "Controller"
#include "Config"

using namespace std;

char buffer[BUFFER_SIZE];

Controller controller;

int main (void) {
    int serv_sock = socket(PF_INET, SOCK_STREAM, 0);
    struct sockaddr_in serv_addr, clnt_addr;

    memset(&serv_addr, 0, sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(PORT);

    if (bind(serv_sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) == -1) {
        printf("bind error\n");
        exit(1);
    }

    if (listen(serv_sock, 5) == -1) {
        printf("listen error\n");
        exit(1);
    }

    struct epoll_event *ep_events;
    struct epoll_event event;
    int epfd, event_cnt;

    epfd = epoll_create(EPOLL_SIZE);
    ep_events = (struct epoll_event *)malloc(sizeof(struct epoll_event) * EPOLL_SIZE);

    event.events = EPOLLIN;
    event.data.fd = serv_sock;
    epoll_ctl(epfd, EPOLL_CTL_ADD, serv_sock, &event);

    while (true) {
        event_cnt = epoll_wait(epfd, ep_events, EPOLL_SIZE, -1);
        if (event_cnt == -1) {
            printf("epoll wait error\n");
            break;
        }

        for (int i = 0; i < event_cnt; ++i) {
            if (ep_events[i].data.fd == serv_sock) { // 새로운 클라이언트 연결
                socklen_t adr_sz = sizeof(clnt_addr);
                int clnt_sock = accept(serv_sock, (struct sockaddr*)&clnt_addr, &adr_sz);
                event.events = EPOLLIN;
                event.data.fd = clnt_sock;
                epoll_ctl(epfd, EPOLL_CTL_ADD, clnt_sock, &event);
            }
            else {
                int opcode;
                int read_len = read(ep_events[i].data.fd, &opcode, sizeof(opcode));
                if (read_len == 0) { // 연결 해제
                    epoll_ctl(epfd, EPOLL_CTL_DEL, ep_events[i].data.fd, nullptr);
                    controller.finish_session(ep_events[i].data.fd);
                    close(ep_events[i].data.fd);
                }
                else { // 메시지
                    if (opcode == 0) { // 새 세션 등록
                        int session_id;
                        read(ep_events[i].data.fd, &session_id, sizeof(session_id));
                        controller.establish_new_session(ep_events[i].data.fd, session_id);
                    }
                    else if (opcode == 1 || opcode == 2) { // 메시지, 이미지 수신
                        int chat_room_id;
                        int message_size;

                        read(ep_events[i].data.fd, &chat_room_id, sizeof(chat_room_id));
                        read(ep_events[i].data.fd, &message_size, sizeof(message_size));

                        int recv_sum = 0;
                        vector<char> message;
                        while (recv_sum < message_size) {
                            const int recv_count = read(ep_events[i].data.fd, buffer, BUFFER_SIZE);
                            recv_sum += recv_count;

                            for (int i = 0; i < recv_count; ++i) {
                                message.push_back(buffer[i]);
                            }
                        }

                        controller.receive_message(ep_events[i].data.fd, opcode, chat_room_id, message);
                    }
                    else {
                        // error
                    }
                }
            }
        }
    }

    close(serv_sock);
    close(epfd);
    return 0;
}