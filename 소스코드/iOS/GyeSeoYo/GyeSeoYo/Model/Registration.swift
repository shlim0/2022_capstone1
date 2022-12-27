//
//  Registration.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation

struct Registration: Codable{
    var user_id: String
    var user_pwd: String
    var nickname: String
    var comment: String
}

var registrationManager: Registration = Registration(user_id: "", user_pwd: "", nickname: "", comment: "")
