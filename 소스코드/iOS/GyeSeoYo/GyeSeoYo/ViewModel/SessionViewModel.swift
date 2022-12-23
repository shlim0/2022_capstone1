//
//  SessionViewModel.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/23.
//

import Foundation
import CoreData
import CryptoKit
import SwiftUI

class SessionViewModel: ObservableObject {
    let container: NSPersistentContainer
    @Published var savedEntities = [SessionEntity]()
    @Published var exactUserEntities: [UserEntity] = []
    
    init () {
        container = NSPersistentContainer(name: "Model")
        container.loadPersistentStores { description, error in
            if let error = error {
                print("ERROR LOADING SESSION")
                print(error.localizedDescription)
            } else {
                print("SUCCESSFULLY LOAD SESSION")
            }
        }
        fetchSessions()
    }
    
    func fetchSessions() {
        let request = NSFetchRequest<SessionEntity>(entityName: "SessionEntity")
        do {
            savedEntities = try container.viewContext.fetch(request)
        } catch {
            print("ERROR FETCHING SESSION")
            print(error.localizedDescription)
        }
    }
    
    func addSession(id: String, isLogin: Bool = true, last_timestamp: Date = Date()) {
        let session = SessionEntity(context: container.viewContext)
        session.id = UUID().uuidString    //시스템에서 id를 자동 생성
        saveData()
    }
    
    // 회원 탈퇴해도 세션 정보는 바로 지우지 않기로 함
//    func deleteSession(indexSet: IndexSet) {
//        guard let index = indexSet.first else { return }
//        let entity = savedEntities[index]
//        container.viewContext.delete(entity)
//        saveData()
//    }
    
    func saveData() {
        do {
            try container.viewContext.save()
            fetchSessions()
        } catch {
            print("ERROR SAVING SESSION")
            print(error.localizedDescription)
        }
    }
    
    func updateSession(entity: SessionEntity, isLogin: Bool = true, last_timestamp: Date = Date()) {
        saveData()
    }
    
    func fetchUserAtSession(id: String) -> [UserEntity]{
        //User Entity 정보 가져옴
        let request = NSFetchRequest<UserEntity>(entityName: "UserEntity")
        
        //입력한 userId랑 exact match된 것만 가져옴
        request.predicate = NSPredicate(format: "id = '%@'", id)
        
        do {
             exactUserEntities = try container.viewContext.fetch(request)
        } catch {
             print("ERROR FETCHING USER")
             print(error.localizedDescription)
        }
        
        return exactUserEntities
    }
    
    func login(userId: String, password: String) -> Bool{
        exactUserEntities = fetchUserAtSession(id: userId)

        for exactUserEntity in exactUserEntities {
            if(exactUserEntity.id == userId && exactUserEntity.pwd == toSHA256(password)){
                addSession(id: userId)
                print("true")
                return true
            }
        }
        print("false")
        return false
    }
    
    func isLogin(userId: String) -> Bool{
        exactUserEntities = fetchUserAtSession(id: userId)
        
        //입력받은 userId에 대한 isLogin 상태를 반환한다.
        for exactSessionEntity in savedEntities {
            for exactUserEntity in exactUserEntities {
                //해당하는 userId의 isLogin이 true인 경우
                if(exactSessionEntity.isLogin && exactUserEntity.id == userId){
                    return true
                }
            }
        }
        return false
    }
    
    func toSHA256(_ password: String) -> String {
        let data = password.data(using: .utf8)
        let sha256 = SHA256.hash(data: data!)
        let shaData = sha256.compactMap{String(format: "%02x", $0)}.joined()
        return shaData
    }
    
}
