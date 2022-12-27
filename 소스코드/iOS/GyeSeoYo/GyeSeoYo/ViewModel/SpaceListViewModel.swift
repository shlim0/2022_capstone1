//
//  SpaceListViewModel.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/27.
//


import Foundation

class SpaceListViewModel: ObservableObject{
    @Published var spaceList : [Space] = []
    
    init(){
        for index in 0..<SpaceList.count {
            spaceList.append(SpaceList[index])
        }
    }
}
