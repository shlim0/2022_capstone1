//
//  SpaceUploadTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation
import SwiftUI
import PhotosUI

struct SpaceUploadTabView: View{
    @State var title: String = "park"
    @State var location: String = "seoul"
    @State var price: String = "10000"
    @State var priceUnit: String = "day"
    @State var description: String = "sibal"
    @State var isButtonPushed: Bool = false
    @State var space: Space? = nil
    @State var tags: String = "kimbap"
    @State var peopleCount: String = "2"
    @Binding var isLogin: Bool
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImageData: Data? = nil
    
    
    var body: some View{
        if(isLogin == true){
            ScrollView{
                VStack{
                    Text("장소 등록")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .padding(.bottom, 40)
                        
                    //갤러리에서 사진 업로드
                    PhotosPicker(
                        selection: $selectedItem,
                        matching: .images,
                        photoLibrary: .shared()) {
                            Text("Select a photo")
                        }
                        .onChange(of: selectedItem) { newItem in
                            Task {
                                // Retrieve selected asset in the form of Data
                                if let data = try? await newItem?.loadTransferable(type: Data.self) {
                                    selectedImageData = data
                                }
                            }
                        }
                    
                    //사진 미리보기
                    if let selectedImageData,
                       let uiImage = UIImage(data: selectedImageData) {
                        Image(uiImage: uiImage)
                            .resizable()
                            .scaledToFit()
                            .frame(width: 250, height: 250)
                    }
                    
                    TextField("제목", text: $title)
                        .modifier(FieldStyle())
                        .autocapitalization(.none)
                    TextField("위치 등록", text: $location)
                        .modifier(FieldStyle())
                    TextField("가격", text: $price)
                        .modifier(FieldStyle())
                    TextField("가격 단위", text: $priceUnit)
                        .modifier(FieldStyle())
                        .autocapitalization(.none)
//                    TextField("해시 태그", text: $tags)
//                        .modifier(FieldStyle())
//                        .autocapitalization(.none)
                    TextField("최대 인원", text: $peopleCount)
                        .modifier(FieldStyle())
                        .autocapitalization(.none)
                    TextField("장소 설명 (선택)", text: $description)
                        .modifier(FieldStyle())
                        .autocapitalization(.none)
                    
                    
                    AsyncButton {
                        if let selectedImageData,
                            let uiImage = UIImage(data: selectedImageData) {
                            let imageURL: String = makeImageBase64Encoding(uiImage)
                            space = Space(id: 4, session_id: SessionManager.session_id, title: title, distance: 0.0, location: location, description: description, status: 0, price: Int(price)!, priceUnit: priceUnit, rank: "New", seller: registrationManager.nickname, peopleCnt: peopleCount, image_count: 1, images: [imageURL], tag_count: 0, tags: [tags])
                            SpaceImageList.append(uiImage)
                        }
                        sLVM.spaceList.append(space!)
                        
                        print("session id in SpaceUploadTabView: \(SessionManager.session_id)")
                        await upload(space!)
                        
                        title = ""
                        location = ""
                        price = ""
                        priceUnit = ""
                        peopleCount = ""
                        description = ""
                        
                    }label: {
                        Text("장소 등록")
                            .font(.headline)
                            .foregroundColor(.white)
                            .padding()
                            .frame(width: 280, height: 45)
                            .background(Color.blue)
                            .cornerRadius(10.0)
                    }
                }.padding()
                    .ignoresSafeArea()
            }
        }
        else {
            LoginView(tabViewTempleteActive: $tabViewTempleteActive, isLogin: $isLogin, sLVM: sLVM)
        }
    }
    
    func makeImageBase64Encoding(_ spaceImage: UIImage) -> String{
        if let data = spaceImage.pngData() {
            let base64 = data.base64EncodedString()
            return base64
        }
        return "ERROR"
    }
    func upload(_ space: Space) async {
        let uploadInfo: Space = space
        
        let data = uploadInfo.toDictionary
//        print(data!)
        await Network().postSpaceUploadData(input_url: "http://119.192.31.33:10006/space", input_params: data!)
    }
    
}

//struct SpaceUploadTabView_Previews: PreviewProvider {
//    static var previews: some View {
//        SpaceUploadTabView(space:)
//    }
//}
