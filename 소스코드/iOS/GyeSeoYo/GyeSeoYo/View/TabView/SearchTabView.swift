//
//  SearchTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct SearchTabView: View {
    @ObservedObject var sLVM: SpaceListViewModel
    @State private var searchQuery: String = ""

    var body: some View {
        NavigationView {
            List {
                ForEach (searchResults, id:\.title){ space in
                    Text(space.title)
                }
            }
            .searchable(text: $searchQuery,
                        placement: .automatic)
            .navigationTitle("검색")
            
        }
    }
    
    var searchResults: [Space] {
//        let _ = print(sLVM.spaceList.count)
//        let _ = print(SpaceListViewModel().spaceList.count)
//        let _ = print(sLVM.spaceList)
//        sLVM.spaceList[0].title = "dasdas"
        if searchQuery.isEmpty{
            return sLVM.spaceList
        }
        else{
            return sLVM.spaceList.filter {$0.title.lowercased().contains(searchQuery.lowercased())}
        }
    }
}
