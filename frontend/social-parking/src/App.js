import { map } from 'leaflet';
import React, { useEffect, useRef, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMapEvents, useMap, MapConsumer } from 'react-leaflet'

import { fetchParkingLots } from './api/fetchParkingLots';

import "./App.css";

const App = () => {
    const [query, setQuery] = useState('');
    const [parkingLots, setParkingLots] = useState({}); 

    const search = async (e) => {
        if (e.key === 'Enter') {
            const data = await fetchParkingLots(query);

            setParkingLots(data);
            setQuery('');
        }
    }

    return (
        <div className="leaflet-container">
            <MapContainer center={[44.45, 26.1]} zoom={15}>
                <TileLayer
                    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <MapConsumer>
                    {(map) => {
                        map.locate({setView: true});
                        return null;
                    }}
                </MapConsumer>
            </MapContainer>
        </div>
    );
}

export default App;