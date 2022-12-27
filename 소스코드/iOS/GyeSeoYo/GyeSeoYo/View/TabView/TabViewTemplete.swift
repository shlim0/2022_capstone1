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
    @State var showSecretOverlay = true
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        
        NavigationView{
            TabView(selection: $selection){
                //                NavigationLink(destination: AlertListTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive).navigationBarBackButtonHidden(true), isActive: $tabViewTempleteActive
                //                               , label:  {
                //                    if(isLogin == false){Text("로그인이 필요합니다")}
                //                    else{AlertListTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive)}
                //                })
//                SearchTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive)
                SearchTabView(sLVM: sLVM)
                    .tabItem {
                        Image(systemName: "magnifyingglass")
                        Text("검색")
                    }
                    
                //                NavigationLink(destination: FavoriteListTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive ).navigationBarBackButtonHidden(true), isActive: $tabViewTempleteActive
                //                               , label:  {
                //                    if(isLogin == false){Text("로그인이 필요합니다")}
                //                    else{FavoriteListTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive)}
                //                })
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
                
                //                NavigationLink(destination: SpaceUploadTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive).navigationBarBackButtonHidden(true), isActive: $tabViewTempleteActive
                //                               , label:  {
                //                    if(isLogin == false){Text("로그인이 필요합니다")}
                //                    else{SpaceUploadTabView(isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive)}
                //                })
                
                //                NavigationView(content: <#T##() -> View#>)
                
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
