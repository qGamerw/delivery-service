import { useState, useEffect } from "react";
import { GeolocationControl, Map, YMaps } from "@pbe/react-yandex-maps";
import '../App.css';
import React from "react";
import courierService from "../services/courierService";

interface Coordinates {
    latitude:number;
    longitude:number;
}

const MainPage: React.FC = () => {
    const getLocation = () => {
        return new Promise<GeolocationPosition>((resolve, reject) => {
            navigator.geolocation.getCurrentPosition(resolve, reject);
        });
    }

    const [currentCoordinates, setCurrentCoordinates] = useState<Coordinates | null>(null);

    useEffect(() => {
        const updateCoordinates = async () => {
            try {
                const position = await getLocation();
                const { latitude, longitude } = position.coords;
                console.log(currentCoordinates?.latitude, currentCoordinates?.longitude)
                if(!currentCoordinates || currentCoordinates.latitude !== latitude
                                            || currentCoordinates.longitude !== longitude){
                    setCurrentCoordinates(position.coords);
                    await courierService.updateCoordinates(latitude, longitude);
                }

            } catch (error) {
                console.error('Failed to get current position:', error);
            }
        };

        const intervalId = setInterval(updateCoordinates, 10000);

        return () => {
            clearInterval(intervalId);
        };
    }, []);

    return (
        <YMaps query={{ apikey: '8ec18778-cb70-437f-87fc-7c17e8e0bb71'}}>
            <Map className="map" defaultState={{ center: [55.75, 37.57], zoom: 9 }} />
        </YMaps>
    );
};

export default MainPage;