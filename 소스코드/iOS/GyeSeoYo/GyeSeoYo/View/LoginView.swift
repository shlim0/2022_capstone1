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
    @State private var userId: String = "kim"
    @State private var password: String = "112"
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
                
                Spacer()
                //로그인 버튼
                NavigationLink(destination: MainView(isLogin: self.$isLogin, userId: self.$userId).navigationBarBackButtonHidden(true), isActive: $isLogin){
                    Button(action: {
                        login(userId, password)
                    }
                    ){
                        Text("LOGIN")
                            .font(.headline)
                            .foregroundColor(.white)
                            .padding()
                            .frame(width: 280, height: 45)
                            .background(Color.blue)
                            .cornerRadius(10.0)
                    }.alert(isPresented: $showingAlert) {
                        Alert(title: Text("불일치"), message: Text("아이디 또는 패스워드가 잘못되었습니다."), dismissButton: .default(Text("닫기")))
                    }
                }
                
                //                Button {
                ////                    isLogin = sessionViewModel.login(userId: userId, password: password)
                //                    if userId == correctUserId && password == correctPassword{
                //                        isLogin = true
                //                    }
                //
                //                    if isLogin == false{
                //                        showingAlert = true
                //                    }
                //                    else if isLogin == true{
                //                        MainView(isLogin: $isLogin, userId: $userId)
                //                    }
                //                }label: {
                //                    Text("로그인")
                //                        .font(.headline)
                //                        .foregroundColor(.white)
                //                        .padding()
                //                        .frame(width: 280, height: 45)
                //                        .background(Color.blue)
                //                        .cornerRadius(10.0)
                //                }.padding()
                //                    .padding(.top, 120)
                //                    .ignoresSafeArea()
                //                    .alert(isPresented: $showingAlert) {
                //                        Alert(title: Text("불일치"), message: Text("아이디 또는 패스워드가 잘못되었습니다."), dismissButton: .default(Text("닫기")))
                //                    }
                
                
                //회원 가입 버튼
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
                
                //홈 화면 버튼
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
    func login(_ userId: String, _ password: String){
        if(correctUserId == userId && correctPassword == toSHA256(password)){
            self.password = ""
            isLogin = true
        } else {
            showingAlert = true
        }
    }
    
    func toSHA256(_ password: String) -> String {
        let data = password.data(using: .utf8)
        let sha256 = SHA256.hash(data: data!)
        let shaData = sha256.compactMap{String(format: "%02x", $0)}.joined()
        return shaData
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
