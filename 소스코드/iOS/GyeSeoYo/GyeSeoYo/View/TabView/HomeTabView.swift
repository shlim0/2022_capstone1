//
//  HomeTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct HomeTabView: View{
    @Binding var isLogin: Bool
    @Binding var userId: String
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    var body: some View {
        VStack {
            //홈 화면 배너
            
            Image("설악산")
                .resizable()
                .frame(width: 400, height: 150)
                .clipShape(Rectangle())
        
            ScrollView{
                SpaceView(sLVM: sLVM)
            }
            .frame(height: 480)
        }
        .overlay(ButtonViewTemplete(userId: $userId, isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive, sLVM: sLVM)
            .background(Color.clear)
            .frame(maxHeight: 50, alignment: .top)
            .offset(x: 0, y: -350)
        )
    }
}


//struct HomeTabView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeTabView()
//    }
//}
