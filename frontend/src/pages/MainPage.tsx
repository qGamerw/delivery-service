import { useState, useEffect } from "react";
import { GeolocationControl, Map, Placemark, YMaps } from "@pbe/react-yandex-maps";
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
    const [mapCenter, setMapCenter] = useState<[number, number]>([55.75, 37.57]);

    const updateCoordinates = async () => {
        try {
            const position = await getLocation();
            const { latitude, longitude } = position.coords;

            if (!currentCoordinates || (currentCoordinates.latitude !== latitude && currentCoordinates.longitude !== longitude)) {
                console.log([currentCoordinates?.latitude, currentCoordinates?.longitude]);
                setCurrentCoordinates(oldVal => {return position.coords});
                console.log([currentCoordinates?.latitude, currentCoordinates?.longitude]);
                await courierService.updateCoordinates(latitude, longitude);

                console.log([latitude, longitude]);
                console.log([mapCenter[0], mapCenter[1]]);
                // setMapCenter(oldVal => {return [latitude, longitude]});
                const brand_new_var = [latitude, longitude];
                setMapCenter([brand_new_var[0], brand_new_var[1]]);
                // setMapCenter([latitude, longitude]);
                console.log([mapCenter[0], mapCenter[1]]);
            }

        } catch (error) {
            console.error('Failed to get current position:', error);
        }
    };

    useEffect(() => {

        const intervalId = setInterval(updateCoordinates, 10000);

        return () => {
            clearInterval(intervalId);
        };
    }, []);

    return (
        <YMaps query={{ apikey: '8ec18778-cb70-437f-87fc-7c17e8e0bb71'}}>
            <Map
                className="map"
                defaultState={{ center: [55.75, 37.57], zoom: 9 }}
                state={{center: mapCenter, zoom: 9}}
            >
                {currentCoordinates && (
                    <Placemark geometry={[currentCoordinates.latitude, currentCoordinates.longitude]} />
                )}
            </Map>
        </YMaps>
    );
};


export default MainPage;