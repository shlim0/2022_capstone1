//
//  ContentView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct MainView: View {
    @Binding var isLogin: Bool
    @Binding var userId: String
    var body: some View {
        TabViewTemplte(isLogin: $isLogin, userId: $userId)
        
    }
}

//struct MainView_Previews: PreviewProvider {
//    static var previews: some View {
//        MainView()
//    }
//}
