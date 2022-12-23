//
//  TabViewTemplete.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct TabViewTemplte: View {
    @Binding var isLogin: Bool
    @Binding var userId: String
    var body: some View {
        
        NavigationView{
            TabView {
                NavigationLink(destination: AlertListTabView(isLogin: $isLogin).navigationBarBackButtonHidden(true)
                               , label:  {
                    if(isLogin == false){Text("로그인이 필요합니다")}
                    else{AlertListTabView(isLogin: $isLogin)}
                })
                .tabItem {
                    Image(systemName: "clock.arrow.circlepath")
                    Text("알림 목록")
                }
                NavigationLink(destination: FavoriteListTabView(isLogin: $isLogin).navigationBarBackButtonHidden(true)
                               , label:  {
                    if(isLogin == false){Text("로그인이 필요합니다")}
                    else{FavoriteListTabView(isLogin: $isLogin)}
                })
                .tabItem {
                    Image(systemName: "heart.fill")
                    Text("관심 목록")
                }
                HomeTabView()
                    .tabItem {
                        Image(systemName: "house")
                        Text("홈 화면")
                    }
                MyNearTabView()
                    .tabItem {
                        Image(systemName: "location.circle.fill")
                        Text("내 주변")
                    }
                
                NavigationLink(destination: ChatListTabView(isLogin: $isLogin).navigationBarBackButtonHidden(true)
                               , label:  {
                    if(isLogin == false){Text("로그인이 필요합니다")}
                    else{ChatListTabView(isLogin: $isLogin)}
                })
                .tabItem {
                    Image(systemName: "message.fill")
                    Text("채팅 목록")
                }
            }
            .overlay(ButtonViewTemplete(userId: $userId, isLogin: $isLogin)
                .background(Color.clear)
                .frame(maxHeight: 50, alignment: .top)
                .offset(x: 0, y: -350)
            )
        }
    }
}
