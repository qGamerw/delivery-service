import {Map, YMaps} from "@pbe/react-yandex-maps";

const MainPage: React.FC = () => {
    return (
        <YMaps query={{ apikey: '8ec18778-cb70-437f-87fc-7c17e8e0bb71'}}>
            <div className="App">
                <h1>Мои карты</h1>
                <Map defaultState={{ center: [55.75, 37.57], zoom: 9 }} />
            </div>
        </YMaps>
    );
};

export default MainPage;