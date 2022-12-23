//
//  LoginView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/18.
//

import SwiftUI
import CryptoKit

struct LoginView: View {
    @ObservedObject var userViewModel = UserViewModel()
    @ObservedObject var sessionViewModel = SessionViewModel()
    @State private var userId: String = ""
    @State private var password: String = ""
    @State private var isLogin: Bool = false
    @State var showingAlert: Bool = false
    
    private var correctUserId: String = "kim"
    private var correctPassword: String = "b1556dea32e9d0cdbfed038fd7787275775ea40939c146a64e205bcb349ad02f"
    
    var body: some View {
        
        NavigationStack{
            
            VStack{
                
                Text("게세요")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.bottom, 40)
                TextField("Userid", text: $userId)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                SecureField("Password", text: $password)
                    .modifier(FieldStyle())
                
                
                Button {
                    isLogin = sessionViewModel.login(userId: userId, password: password)
                    if isLogin == false{
                        showingAlert = true
                    }
                    else if isLogin == true{
                        MainView(isLogin: $isLogin, userId: $userId)
                    }
                }label: {
                    Text("로그인")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.blue)
                        .cornerRadius(10.0)
                }.padding()
                    .padding(.top, 120)
                    .ignoresSafeArea()
                    .alert(isPresented: $showingAlert) {
                        Alert(title: Text("불일치"), message: Text("아이디 또는 패스워드가 잘못되었습니다."), dismissButton: .default(Text("닫기")))
                    }
                
                
                NavigationLink {
                    RegistrationView(id: "", pwd: "", pwdCheck: "", nickname: "", comment: "", isLogin: false, isPwdValid: false)
                } label: {
                    Text("회원 가입")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.red)
                        .cornerRadius(10.0)
                }
                
                Spacer()
                NavigationLink {
                    if isLogin == false{
                        MainView(isLogin: $isLogin, userId: $userId).navigationBarBackButtonHidden(true)
                    }
                } label: {
                    Text("홈 화면")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.black)
                        .cornerRadius(10.0)
                }
                Spacer()
            }.padding()
                .padding(.top, 120)
                .ignoresSafeArea()
            
            Spacer()
        }
    }
}

struct FieldStyle: ViewModifier {
    let lightGreyColor = Color(red: 240.0/255.0, green: 240.0/255.0, blue: 240.0/255.0, opacity: 1.0)
    func body(content: Content) -> some View {
        return content
            .padding()
            .background(lightGreyColor)
            .cornerRadius(5.0)
            .padding(.bottom, 5)
            .disableAutocorrection(true)
    }
}
