//
//  HomeTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct HomeTabView: View{
    @State private var searchText = ""
    
    var body: some View {
        VStack {
            //홈 화면 배너
            
            Image("설악산")
                .resizable()
                .frame(width: 400, height: 150)
                .clipShape(Rectangle())
            //검색 창
            SearchBar(text: $searchText)
                .padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0))
            
            ScrollView{
                SpaceView()
            }
            .frame(height: 480)
        }
    }
}


struct HomeTabView_Previews: PreviewProvider {
    static var previews: some View {
        HomeTabView()
    }
}
