//
//  ButtonViewTemplete.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct ButtonViewTemplete: View {
    @Binding var userId: String
    @Binding var isLogin: Bool
    var body: some View {
        HStack{
            Spacer()
            MyPageButtonView(userId: $userId, isLogin: $isLogin)
            SettingButtonView()
        }
    }
}
