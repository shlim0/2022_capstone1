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
    @State private var selection = 0
    @State var tabViewTempleteActive: Bool = true
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        
        NavigationView{
            TabView(selection: $selection){
                SearchTabView(sLVM: sLVM)
                    .tabItem {
                        Image(systemName: "magnifyingglass")
                        Text("검색")
                    }
                FavoriteListTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive, sLVM: sLVM)
                    .tabItem {
                        Image(systemName: "heart.fill")
                        Text("관심 목록")
                    }
                HomeTabView(isLogin: $isLogin, userId: $userId, tabViewTempleteActive: $tabViewTempleteActive, sLVM: sLVM)
                    .tabItem {
                        Image(systemName: "house")
                        Text("홈 화면")
                    }.tag(0)
                MyNearTabView()
                    .tabItem {
                        Image(systemName: "location.circle.fill")
                        Text("내 주변")
                    }
                SpaceUploadTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive, sLVM: sLVM)
                    .tabItem {
                        Image(systemName: "plus.square.fill")
                        Text("장소 등록")
                    }
                    
                
            }
//            .overlay(ButtonViewTemplete(userId: $userId, isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive)
//                .background(Color.clear)
//                .frame(maxHeight: 50, alignment: .top)
//                .offset(x: 0, y: -350)
//            )
            
        }
    }
}
