//
//  MyNearTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI
import UIKit
import GoogleMaps
import GooglePlaces
import CoreLocation


struct MyNearTabView: UIViewRepresentable
{
    let marker : GMSMarker = GMSMarker()
    @ObservedObject var locationManager = LocationManager()

    
    /// Creates a `UIView` instance to be presented.
    func makeUIView(context: Self.Context) -> GMSMapView {

        // Create a GMSCameraPosition that tells the map to display the
        // coordinate -33.86,151.20 at zoom level 6.
        let camera = GMSCameraPosition.camera(withLatitude: 37.494576975240875, longitude: 126.9597316987289, zoom: 6.0)
        let mapView =  GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        mapView.settings.compassButton = true
        mapView.isMyLocationEnabled = true
        mapView.settings.myLocationButton = true
        mapView.settings.scrollGestures = true
        mapView.settings.zoomGestures = true
        mapView.settings.rotateGestures = true
        mapView.settings.tiltGestures = true
        mapView.isIndoorEnabled = false
        return mapView
    }
    
    /// Updates the presented `UIView` (and coordinator) to the latest
    /// configuration.
    func updateUIView(_ mapView: GMSMapView, context: Self.Context) {
        if let myLocation = locationManager.lastKnownLocation {
            mapView.animate(toLocation: myLocation.coordinate)
            print("User's location: \(myLocation)")
        }
        
        // Creates a marker in the center of the map.
        marker.position = CLLocationCoordinate2D(latitude: 37.494576975240875, longitude: 126.9597316987289)
        marker.title = "Seoul"
        marker.snippet = "Korea"
        marker.map = mapView
        
    }
}

class LocationManager: NSObject, CLLocationManagerDelegate, ObservableObject {
    
    // Publish the user's location so subscribers can react to updates
    @Published var lastKnownLocation: CLLocation? = nil
    private let manager = CLLocationManager()
//    var locationManager:CLLocationManager!

    override init() {
        super.init()
        self.manager.delegate = self
        self.manager.startUpdatingLocation()
//        locationManager = CLLocationManager()
        self.manager.requestWhenInUseAuthorization() // when in use auth로 요청 등록

    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status == .authorizedWhenInUse {
            self.manager.startUpdatingLocation()
        }
    }
}
