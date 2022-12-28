//
//  network.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation
import Alamofire
import SwiftUI

struct Network{
    func getData(input_url: String) {
        let url = input_url
        //        let url = "https://jsonplaceholder.typicode.com/todos/1"
        AF.request(url,
                   method: .get,
                   parameters: nil,
                   encoding: URLEncoding.default,
                   headers: ["Content-Type":"application/json", "Accept":"application/json"])
        .validate(statusCode: 200..<300)
        .responseJSON { (json) in
            //여기서 가져온 데이터를 자유롭게 활용하세요.
            print(json)
        }
    }
    
    func postLoginData(input_url: String, input_params: Dictionary<String, Any>)async{
        let url = input_url
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 10
        print(type(of: request))
        // POST 로 보낼 정보
        //        let params = ["id":"아이디", "pw":"패스워드"] as Dictionary
        
        // httpBody 에 parameters 추가
        do {
            try request.httpBody = JSONSerialization.data(withJSONObject: input_params, options: [])
        } catch {
            print("http Body Error")
        }
        await postDataResponse(request: request)
    }
    
    func postDataResponse(request: URLRequest) async{
        AF.request(request).responseString { (response) in
            if let statusCode = response.response?.statusCode {
                //                print("-------------------------statusCode : \(statusCode)")
            }
            
            var decoded = try? JSONDecoder().decode(Session.self, from: response.data!)
            
            if let statusCode = response.response?.statusCode {
                
                SessionManager.statusCode! = statusCode
            }
            
            //             print(decoded?.session_id)
            if let session_id = decoded?.session_id {
                //                    print("session_id : \(session_id)")
                SessionManager.session_id = session_id
                //                    print("sessionManager session_id : \(SessionManager.session_id)")
            }
            
            print("-------------------------------------------------------")
            print("SessionManager.session_id : \(SessionManager.session_id)")
            print("SessionManager.statusCode! : \(SessionManager.statusCode!)")
            
            /////////////////// END
            switch response.result {
            case .success:
                if response.response?.statusCode == 200{
                    print("POST 성공")
                    
                    //////////////////////////START
                    //                    print("session id in Network.swift : \(response.result)")
                    //                    print("session id in Network.swift : \(response.value!)")
                    //                    print(type(of: response.value!))
                    
                    //////////////////////// END
                }
                else{
                    print("연결은 성공했으나 error")
                    //                    print(response.result)
                }
            case .failure(let error):
                print("🚫 Alamofire Request Error\nCode:\(error._code), Message: \(error.errorDescription!)")
            }
        }
    }
    
    func postRegistrationData(input_url: String, input_params: Dictionary<String, Any>)async{
        let url = input_url
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 10
        print(type(of: request))
        // POST 로 보낼 정보
        //        let params = ["id":"아이디", "pw":"패스워드"] as Dictionary
        
        // httpBody 에 parameters 추가
        do {
            try request.httpBody = JSONSerialization.data(withJSONObject: input_params, options: [])
        } catch {
            print("http Body Error")
        }
        
        AF.request(request).responseString {  (response) in
            if let statusCode = response.response?.statusCode {
                print("-------------------------statusCode : \(statusCode)")
                if statusCode == 200{
                    print("SIbal")
                }
                
            }
            
            //            switch response.result {
            //            case .success:
            //                if response.response?.statusCode == 200{
            //                    print("POST 성공")
            //                }
            //                else{
            //                    print("연결은 성공했으나 error")
            //                    //                    print(response.result)
            //                }
            //            case .failure(let error):
            //                print("🚫 Alamofire Request Error\nCode:\(error._code), Message: \(error.errorDescription!)")
            //            }
        }
    }
    
    func postSpaceUploadData(input_url: String, input_params: Dictionary<String, Any>){
        let url = input_url
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 10

        do {
            try request.httpBody = JSONSerialization.data(withJSONObject: input_params, options: [])
        } catch {
            print("http Body Error")
        }
        
        AF.request(request).responseString { (response) in
            print("status code in postSpaceUploadData : \(response.response?.statusCode)")
            switch response.result {
            case .success:
                if response.response?.statusCode == 200{
                    print("POST 성공")
                    
                }
                else{
                    print("연결은 성공했으나 error")
                    //                    print(response.result)
                }
            case .failure(let error):
                print("🚫 Alamofire Request Error\nCode:\(error._code), Message: \(error.errorDescription!)")
            }
        }
    }
    
    func postSearchData(input_url: String, input_params: String){
        let url = input_url
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 10

        do {
            try request.httpBody = JSONSerialization.data(withJSONObject: input_params, options: [])
        } catch {
            print("http Body Error")
        }
        
        AF.request(request).responseString { (response) in
            print("status code in postSpaceUploadData : \(response.response?.statusCode)")
            switch response.result {
            case .success:
                if response.response?.statusCode == 200{
                    print(response.response?.statusCode)
                    print("SEARCH POST 성공")
                    
                }
                else{
                    print("연결은 성공했으나 error")
                    //                    print(response.result)
                }
            case .failure(let error):
                print("🚫 Alamofire Request Error\nCode:\(error._code), Message: \(error.errorDescription!)")
            }
        }
    }
}
