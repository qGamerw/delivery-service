import { useState, useEffect, useRef } from "react";
import { Map, Placemark, YMaps } from "@pbe/react-yandex-maps";
import '../App.css';
import React from "react";
import courierService from "../services/courierService";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import orderService from '../services/orderService';

interface Coordinates {
    latitude:number;
    longitude:number;
}


const MainPage: React.FC = () => {
  const dispatch = useDispatch();
  const allOrders = useSelector((store: RootState) => store.order.allOrders);
  const isAuth = useSelector((state: any) => state.auth.isAuth);
    
    const getLocation = () => {
        return new Promise<GeolocationPosition>((resolve, reject) => {
            navigator.geolocation.getCurrentPosition(resolve, reject,{timeout:10000});
        });
    }

    const [currentCoordinates, setCurrentCoordinates] = useState<Coordinates | null>(null);
    const [mapCenter, setMapCenter] = useState<[number, number]>([55.75, 37.57]);

    const updateCoordinates = async () => {
        try {
            const position = await getLocation();
            const { latitude, longitude } = position.coords;

            if (isAuth && (!currentCoordinates || (currentCoordinates.latitude !== latitude && currentCoordinates.longitude !== longitude))) {
                setCurrentCoordinates(position.coords);
                await courierService.updateCoordinates(latitude, longitude);

                setMapCenter([latitude, longitude]);
            }

        } catch (error) {
            console.error('Failed to get current position:', error);
        }
    };

    useEffect(() => {
      if(isAuth){
        orderService.getActiveDeliveryOrders(dispatch);
      }
      updateCoordinates();
      const intervalId = setInterval(updateCoordinates, 10000);

      return () => {
          clearInterval(intervalId);
      };
    }, []);

    const [ymaps, setYmaps] = useState<any>(null);
  const routes = useRef<any>(null);

  const getRoute = (ref: any) => {
    console.log("allOrders.length: " + allOrders.length);
    if (ymaps && currentCoordinates && ref.geoObjects.getLength() === 0 && allOrders.length > 0) {
      const destinationAddress = allOrders[0].status === "COOKING" || allOrders[0].status === "COOKED"
      ? allOrders[0].branchAddress
        : allOrders[0].status === "DELIVERY"
        ? allOrders[0].address
        : null;
      if (destinationAddress === null){
        return;
      }
      const multiRoute = new ymaps.multiRouter.MultiRoute(
        {
          referencePoints: [[currentCoordinates.latitude, currentCoordinates.longitude], destinationAddress],
          params: {
            results: 2,
            routingMode: 'pedestrian',
          }
        },
        {
        //   boundsAutoApply: true,
          routeActiveStrokeWidth: 6,
          routeActiveStrokeColor: "#fa6600",
        }
      );

      routes.current = multiRoute;
      ref.geoObjects.add(multiRoute);
    }
  };

  const getRoutes = () => {
    console.log(routes.current.getWayPoints());
  };

    return (
        <YMaps query={{ apikey: '8ec18778-cb70-437f-87fc-7c17e8e0bb71'}}>
            <Map
                className="map"
                state={{center: mapCenter, zoom: 12}}
                modules={["multiRouter.MultiRoute"]}
                onLoad={(ymaps) => setYmaps(ymaps)}
                instanceRef={(ref) => ref && getRoute(ref)}
            >
                {/* {currentCoordinates && (
                    <Placemark geometry={[currentCoordinates.latitude, currentCoordinates.longitude]} />
                )} */}
            </Map>
        </YMaps>
    );
};


export default MainPage;